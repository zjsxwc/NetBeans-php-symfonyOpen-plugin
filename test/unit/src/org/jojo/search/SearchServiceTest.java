package org.jojo.search;

import java.io.File;
import java.util.ArrayList;
import org.jojo.search.pattern.DirectorySearchPattern;
import org.jojo.search.pattern.RegexSearchPattern;
import org.jojo.search.pattern.SearchPattern;
import org.jojo.search.pattern.SimpleSearchPattern;
import org.junit.Test;
import static org.junit.Assert.*;

public class SearchServiceTest {

    @Test
    public void testByDefaultUsesThreeSearchPatterns() {
        SearchService searchService = SearchService.getInstance();
    }

    @Test
    public void testSearchPerformsASearchUsingSearchQueryAndPassedFileList() {
        ArrayList<FileEntry> fileList = new ArrayList<FileEntry>();
        FileEntry testFileEntry = new FileEntry(new File("/somefile"));
        fileList.add(testFileEntry);
        SearchService searchService = SearchService.getInstance();

        assertTrue(searchService.search(null, null).isEmpty());
        assertTrue(searchService.search(fileList, null).isEmpty());
        assertTrue(searchService.search(null, "me").isEmpty());

        ArrayList<FileEntry> result = searchService.search(fileList, "me");
        assertEquals(1, result.size());
        assertEquals(testFileEntry, result.get(0));
    }

    @Test
    public void testSearchLimitsNumberOfResultsByParam() {
        ArrayList<FileEntry> fileList = new ArrayList<FileEntry>();
        for (int i = 0; i < 100; i++) {
            fileList.add(new FileEntry(new File("/somefile" + i)));
        }
        SearchService searchService = SearchService.getInstance();

        assertTrue(searchService.search(null, null).isEmpty());
        assertTrue(searchService.search(fileList, null).isEmpty());
        assertTrue(searchService.search(null, "me").isEmpty());

        ArrayList<FileEntry> result = searchService.search(fileList, "me", 40);
        assertEquals(40, result.size());
    }

    @Test
    public void testFirstUseExactSearchThenRegexSearch() {
        ArrayList<FileEntry> fileList = new ArrayList<FileEntry>();
        fileList.add(new FileEntry(new File("some_other.file")));
        fileList.add(new FileEntry(new File("some.file")));

        SearchService searchService = SearchService.getInstance();

        ArrayList<FileEntry> results = searchService.search(fileList, "some.file");
        assertEquals("some.file", results.get(0).getName());
    }

}
