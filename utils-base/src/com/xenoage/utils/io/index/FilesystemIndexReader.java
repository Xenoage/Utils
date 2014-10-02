package com.xenoage.utils.io.index;

import static com.xenoage.utils.PlatformUtils.platformUtils;

import com.xenoage.utils.async.AsyncProducer;
import com.xenoage.utils.async.AsyncResult;
import com.xenoage.utils.io.InputStream;
import com.xenoage.utils.xml.XmlReader;

/**
 * This class reads a {@link FilesystemIndex} from a XML file.
 * 
 * The format of the UTF-8 encoded XML index file is as follows:
 * <pre> {@code
 * <index>
 * 	<file name="myfile.txt" size="1000"/> <!-- size in bytes -->
 * 	<directory name="mydir">
 * 		<directory name="myemptychilddir"/>
 * 		<file name="mychildfile.pdf" size="14123"/>
 * 	</directory>
 * </index>
 * } </pre>
 * 
 * @author Andreas Wenger
 */
public class FilesystemIndexReader
	implements AsyncProducer<FilesystemIndex> {
	
	private final String indexFilePath;
	
	private XmlReader xmlReader;
	
	
	public FilesystemIndexReader(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}
	

	@Override public void produce(final AsyncResult<FilesystemIndex> result) {
		platformUtils().openFileAsync(indexFilePath, new AsyncResult<InputStream>() {

			@Override public void onSuccess(InputStream xmlStream) {
				try {
					FilesystemIndex index = parseFilesystemIndex(xmlStream);
					result.onSuccess(index);
				} catch (Exception ex) {
					result.onFailure(new Exception("Could not read index file", ex));
				}
			}

			@Override public void onFailure(Exception ex) {
				result.onFailure(new Exception("Could not open index file", ex));
			}
			
		});
	}
	
	FilesystemIndex parseFilesystemIndex(InputStream stream)
		throws Exception {
		try {
			xmlReader = platformUtils().createXmlReader(stream);
			checkFileFormat();
			IndexedDirectory root = new IndexedDirectory("");
			readDirectoryChildren(root);
			xmlReader.close();
			return new FilesystemIndex(root.getChildren());
		}
		catch (Exception ex) {
			xmlReader.close();
			throw ex;
		}
	}
	
	private void checkFileFormat() {
		if (xmlReader.openNextChildElement()) {
			if (false == xmlReader.getElementName().equals("index"))
				throw new IllegalStateException("No file index");
		}
		else {
			throw new IllegalStateException("No content");
		}
	}
	
	private void readFile(IndexedDirectory parentDir) {
		Long size = null;
		String s = xmlReader.getAttribute("size");
		if (s != null)
			size = Long.parseLong(s);
		String name = checkFilename(xmlReader.getAttribute("name"));
		parentDir.addChild(new IndexedFile(name, size));			
	}
	
	private void readDirectory(IndexedDirectory parentDir) {
		String name = checkFilename(xmlReader.getAttribute("name"));
		IndexedDirectory dir = new IndexedDirectory(name);
		parentDir.addChild(dir);
		readDirectoryChildren(dir);
	}
	
	private void readDirectoryChildren(IndexedDirectory parentDir) {
		while (xmlReader.openNextChildElement()) {
			String n = xmlReader.getElementName();
			if (n.equals("file"))
				readFile(parentDir);
			else if (n.equals("directory"))
				readDirectory(parentDir);
			else
				throw new IllegalStateException("Unknown element: " + n);
			xmlReader.closeElement();
		}
	}
	
	private String checkFilename(String filename) {
		if (filename == null)
			throw new IllegalStateException("Missing file name");
		//slash and backslash are not allowed
		if (filename.contains("/") || filename.contains("\\"))
			throw new IllegalStateException("Illegal filename: " + filename);
		return filename;
	}

}
