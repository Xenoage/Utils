package com.xenoage.utils.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link FilesystemIndex}.
 * 
 * @author Andreas Wenger
 */
public class FilesystemIndexTest {
	
	private FilesystemIndex index = new FilesystemIndex(
		"rootfile1\n" +
		"rootfile2\n" +
		"/folder/\n" +
		"/folder/file\n" +
		"folder/subfolder/" +
		"folder/subfolder/file1\n" +
		"\\folder\\subfolder//file2\n" +
		"/folder/secondsubfolder\\");
	
	@Test public void existsFileTest() {
		//test with and without leading "/"
		assertEquals(true, index.existsFile("/folder/file"));
		assertEquals(true, index.existsFile("folder/file"));
		//test other files
		assertEquals(true, index.existsFile("rootfile1"));
		assertEquals(true, index.existsFile("rootfile2"));
		assertEquals(true, index.existsFile("folder/subfolder/file1"));
		assertEquals(true, index.existsFile("folder/subfolder/file2"));
		//test some folders and non-existing files
		assertEquals(false, index.existsFile("folder"));
		assertEquals(false, index.existsFile("folder/subfolder/"));
		assertEquals(false, index.existsFile("foo"));
		assertEquals(false, index.existsFile("folder/foo"));
	}

	@Test public void existsDirectoryTest() {
		//test with and without trailing "/"
		assertEquals(true, index.existsDirectory("folder"));
		assertEquals(true, index.existsDirectory("/folder"));
		//test other directories
		assertEquals(true, index.existsDirectory("folder/subfolder"));
		assertEquals(true, index.existsDirectory("folder/secondsubfolder"));
		//test files and non-existing directories
		assertEquals(false, index.existsDirectory("folder/file"));
		assertEquals(false, index.existsDirectory("folder/file/"));
		assertEquals(false, index.existsDirectory("foofolder/"));
		assertEquals(false, index.existsDirectory("folder/foofolder/"));
	}

	@Test public void listFilesTest() {
		//folder "/"
		List<String> files = index.listFiles("/");
		assertEquals(2, files.size());
		assertTrue(files.contains("rootfile1"));
		assertTrue(files.contains("rootfile2"));
		//folder "folder/"
		files = index.listFiles("folder/");
		assertEquals(1, files.size());
		assertTrue(files.contains("file"));
		//folder "folder/subfolder"
		files = index.listFiles("folder/subfolder/");
		assertEquals(2, files.size());
		assertTrue(files.contains("file1"));
		assertTrue(files.contains("file2"));
		//folder "folder/secondsubfolder/": empty
		assertEquals(0, index.listFiles("folder/secondsubfolder/"));
		//non existing folder: empty
		assertEquals(0, index.listFiles("foo/"));
	}
	
	@Test public void listDirectoriesTest() {
		//folder "/"
		List<String> dirs = index.listDirectories("/");
		assertEquals(1, dirs.size());
		assertTrue(dirs.contains("folder"));
		//folder "folder/"
		dirs = index.listDirectories("folder/");
		assertEquals(2, dirs.size());
		assertTrue(dirs.contains("subfolder"));
		assertTrue(dirs.contains("secondsubfolder"));
		//folder "folder/subfolder": empty
		assertEquals(0, index.listDirectories("folder/subfolder/"));
		//folder "folder/secondsubfolder/": empty
		assertEquals(0, index.listDirectories("folder/secondsubfolder/"));
		//non existing folder: empty
		assertEquals(0, index.listDirectories("foo/"));
	}

}
