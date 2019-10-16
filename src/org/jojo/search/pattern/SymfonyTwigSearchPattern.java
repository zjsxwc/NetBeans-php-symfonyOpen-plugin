package org.jojo.search.pattern;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import org.jojo.search.FileEntry;

public class SymfonyTwigSearchPattern extends SearchPattern {
    
    @Override
    public ArrayList<FileEntry> search(ArrayList<FileEntry> fileList, String query, int limit) {
        ArrayList<FileEntry> result = new ArrayList<FileEntry>();
        if (fileList == null || !isValidQuery(query)) {
            return result;
        }
        for (Iterator<FileEntry> it = fileList.iterator(); it.hasNext();) {
            FileEntry fileEntry = it.next();
            if (isMatch(fileEntry, query)) {
                result.add(fileEntry);
                return result;
            }
            if (limit == result.size()) {
                return result;
            }
        }
        return result;
    }
    
    
    @Override
    public boolean isMatch(FileEntry fileEntry, String query) {
        if (fileEntry == null || !isValidQuery(query)) {
            return false;
        }
        // "MyBundle:Article:new_article.html.twig" => src/MyBundle/Resources/views/Article/new_article.html.twig
        String realPath = query;
        String[] exploded = query.split(":");
        if (exploded.length == 3) {
            if (exploded[1].length() > 0) {
                realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[1] + "/" + exploded[2];
            } else {
                realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[2];
            }
        }
        
//        Console console = System.console();
//        System.out.println(query);
//        System.out.println(realPath);
        
        return (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
    }

    @Override
    public boolean isValidQuery(String query) {
        return (query != null) && query.toLowerCase().contains(".twig");
    }
}
