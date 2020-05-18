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
        boolean isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(query.toLowerCase()));;
        
        // "MyBundle:Article:new_article.html.twig" => src/MyBundle/Resources/views/Article/new_article.html.twig
        String realPath = query;
        String[] exploded = query.split(":");
        if (exploded.length == 3) {
            if (exploded[1].length() > 0) {
                realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[1] + "/" + exploded[2];
            } else {
                realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[2];
            }
            isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
        }
        
        // "UserBundle:BodySizeData"
        if (exploded.length == 2) {
            realPath = "/src/" + exploded[0] + "/Entity/" + exploded[1] + ".php";
            isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
            if (!isMatched) {
                realPath = "/src/" + exploded[0] + "/Repository/" + exploded[1] + "Repository.php";
                isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
            }
        }
        
        return isMatched;
    }

    @Override
    public boolean isValidQuery(String query) {
        return (query != null) && query.toLowerCase().contains(".twig");
    }
}
