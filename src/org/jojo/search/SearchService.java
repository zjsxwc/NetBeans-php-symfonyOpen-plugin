package org.jojo.search;

import java.util.ArrayList;
import java.util.Iterator;
import org.jojo.search.pattern.*;

public class SearchService {

    private static SearchService instance = null;
    private ArrayList<SearchPattern> searchPatternList = new ArrayList<SearchPattern>();

    private SearchService(boolean onlySymfony) {
        if (!onlySymfony) {
            searchPatternList.add(new DirectorySearchPattern());
            searchPatternList.add(new SimpleSearchPattern());
            searchPatternList.add(new SymfonyTwigSearchPattern());
            searchPatternList.add(new RegexSearchPattern());
        } else {
            searchPatternList.add(new DirectorySearchPattern());
            searchPatternList.add(new SymfonyTwigSearchPattern());
        }

    }

    public static SearchService getInstance(boolean onlySymfony) {
        if (instance == null) {
            instance = new SearchService(onlySymfony);
        }
        return instance;
    }

    public void clearSearchPatternList() {
        searchPatternList.clear();
    }

    public void addSearchPattern(SearchPattern searchPattern) {
        searchPatternList.add(searchPattern);
    }

    public ArrayList<SearchPattern> getSearchPatternList() {
        return searchPatternList;
    }

    public ArrayList<FileEntry> search(ArrayList<FileEntry> fileList, String query) {
        if (fileList == null) {
            return new ArrayList<FileEntry>();
        }
        return search(fileList, query, fileList.size());
    }

    public ArrayList<FileEntry> search(ArrayList<FileEntry> fileList, String query, int limit) {
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
        return result;
    }
}
