package org.jojo.search.pattern;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import org.jojo.search.FileEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymfonyTwigSearchPattern extends SearchPattern {
    @Override
    public boolean queryStringApply(String query) {
        if (query.contains(":")) {
            return true;
        }
        if (query.contains("twig")) {
            return true;
        }
        if (query.contains(".") && query.contains("_")) {
            return true;
        }
        return false;
    }
    
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
            }
            if (limit <= result.size()) {
                return result;
            }
        }
        return result;
    }
    
    
    public static String snake2Camel(String line, boolean... smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    
    public static String camel2Snake(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
    
    @Override
    public boolean isMatch(FileEntry fileEntry, String query) {
        if (fileEntry == null || !isValidQuery(query)) {
            return false;
        }
        boolean isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(query.toLowerCase()));;
        if (isMatched) {
            return true;
        }
        // "MyBundle:Article:new_article.html.twig" => src/MyBundle/Resources/views/Article/new_article.html.twig
        String realPath = query;
        
        if (query.contains(":")) {
            String[] exploded = query.split(":");
            if (query.contains("twig")) {
                if (exploded.length == 3) {
                    if (exploded[1].length() > 0) {
                        realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[1] + "/" + exploded[2];
                    } else {
                        realPath = "/src/" + exploded[0] + "/Resources/views/" + exploded[2];
                    }
                    isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
                    if (isMatched) {
                        return true;
                    }
                }
            }

            // "UserBundle:BodySizeData"
            if (exploded.length == 2) {
                realPath = "/src/" + exploded[0] + "/Entity/" + exploded[1] + ".php";
                isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
                if (isMatched) {
                    return true;
                }
            }
            if (exploded.length == 2) {
                realPath = "/src/" + exploded[0] + "/Repository/" + exploded[1] + "Repository.php";
                isMatched = (fileEntry.getRelativePath().toLowerCase().startsWith(realPath.toLowerCase()));
                if (isMatched) {
                    return true;
                }
            }
        } else {
            //todo goto service by string
            String[] exploded = query.split("\\.");
            String camelPath = "";
            for (int i = 0; i < exploded.length; i++) {
                String suffix = "/";
                if (i == (exploded.length-1)) {
                    suffix = "";
                }
                camelPath += snake2Camel(exploded[i]) + suffix;
            }
            
            System.out.println("length: " + exploded.length);
            System.out.println("camelPath: " + camelPath);
            System.out.println("query: " + query);
            isMatched = (fileEntry.getRelativePath().toLowerCase().contains(camelPath.toLowerCase()));
            if (isMatched) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean isValidQuery(String query) {
        return (query != null);
    }
}
