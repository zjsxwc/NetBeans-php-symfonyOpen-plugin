package org.jojo.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jojo.search.pattern.*;

public class SearchService {

    private static SearchService instance = null;

    private SearchService() {

    }

    public static SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }
        return instance;
    }

    public ArrayList<FileEntry> search(ArrayList<FileEntry> fileList, String query) {
        if (fileList == null) {
            return new ArrayList<FileEntry>();
        }
        return search(fileList, query, fileList.size());
    }
    
    public HashMap<String, ArrayList<FileEntry>> searchResultCache = new HashMap<String, ArrayList<FileEntry>>();

    public ArrayList<FileEntry> search(ArrayList<FileEntry> fileList, String query, int limit) {
        
        if (searchResultCache.containsKey(query)) {
            ArrayList<FileEntry> scr = searchResultCache.get(query);
            if (scr == null) {
                return new ArrayList<FileEntry>();
            }
            return scr;
        }

        ArrayList<SearchPattern> searchPatternList = new ArrayList<SearchPattern>();
        boolean onlySymfony = false;
        if (query.contains(":")) {
            onlySymfony = true;
        }
        if (query.contains(".twig")) {
            onlySymfony = true;
        }
        if (!onlySymfony) {
            searchPatternList.add(new DirectorySearchPattern());
            searchPatternList.add(new SimpleSearchPattern());
            searchPatternList.add(new SymfonyTwigSearchPattern());
            searchPatternList.add(new RegexSearchPattern());
        } else {
            searchPatternList.add(new DirectorySearchPattern());
            searchPatternList.add(new SymfonyTwigSearchPattern());
        }

        ArrayList<FileEntry> result = new ArrayList<FileEntry>();
        if (query.length() <= 0) {
            return result;
        }
        int actualLimit = limit;
        for (Iterator<SearchPattern> it = searchPatternList.iterator(); it.hasNext();) {
            SearchPattern searchPattern = it.next();
            if (!searchPattern.queryStringApply(query)) {
                continue;
            }
            actualLimit = limit - result.size();
            if (actualLimit > 0 && searchPattern.isValidQuery(query)) {
                ArrayList<FileEntry> found = searchPattern.search(fileList, query, actualLimit);
                result = FileEntry.concatWithoutDuplications(result, found);
            }
        }
        
        searchResultCache.put(query, result);
        return result;
    }
}
