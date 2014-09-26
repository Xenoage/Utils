package com.xenoage.utils.gwt.io;

import java.util.ArrayList;
import java.util.List;

import com.xenoage.utils.async.AsyncResult;
import com.xenoage.utils.gwt.GwtPlatformUtils;
import com.xenoage.utils.io.FileFilter;
import com.xenoage.utils.io.FileUtils;
import com.xenoage.utils.io.FilesystemInput;
import com.xenoage.utils.io.InputStream;

/**
 * Some useful input methods for a GWT client application.
 * 
 * Use {@link GwtPlatformUtils#gwtIO()} to get an instance of this class.
 * 
 * Since HTTP provides no method to list files and directories, helper files are used.
 * A directory can contain a file {@value #dirIndex} and / or a file
 * {@value #fileIndex}, which are UTF8 text files containing the subdirectories
 * and file names, one per line. These can either be static files, or on a webserver,
 * these lists could for example be created on the fly by PHP scripts.
 *
 * @author Andreas Wenger
 */
public class GwtIO
	implements FilesystemInput {
	
	public static final String dirIndex = ".dirindex";
	public static final String fileIndex = ".fileindex";
	
	
	@Override public void openFileAsync(String filePath, AsyncResult<InputStream> callback) {
		GwtInputStream.open(filePath, callback);
	}

	/**
	 * {@inheritDoc}
	 * A file is only reported to exist, iff it is listed in the {@value #fileIndex} index.
	 */
	@Override public void existsFileAsync(final String filepath, final AsyncResult<Boolean> exists) {
		String indexPath = FileUtils.getDirectoryName(filepath) + "/" + fileIndex;
		indexAsync(indexPath, new AsyncResult<List<String>>() {

			@Override public void onSuccess(List<String> index) {
				String filename = FileUtils.getFileName(filepath);
				exists.onSuccess(index.contains(filename));
			}

			@Override public void onFailure(Exception ex) {
				exists.onSuccess(false);
			}
		});
	}
	
	private void indexAsync(String indexPath, final AsyncResult<List<String>> index) {
		openFileAsync(indexPath, new AsyncResult<InputStream>() {

			@Override public void onSuccess(InputStream data) {
				String indexString = ((GwtInputStream) data).getData();
				String[] indexArray = indexString.split("\\n");
				List<String> indexList = new ArrayList<String>();
				for (String item : indexArray)
					indexList.add(item.trim());
				index.onSuccess(indexList);
			}

			@Override public void onFailure(Exception ex) {
				index.onFailure(ex);
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 * A directory is only reported to exist, iff it is listed in the {@value #dirIndex} index.
	 */
	@Override public void existsDirectoryAsync(final String directory, final AsyncResult<Boolean> exists) {
		String indexPath = FileUtils.getDirectoryName(directory) + "/" + dirIndex;
		indexAsync(indexPath, new AsyncResult<List<String>>() {

			@Override public void onSuccess(List<String> index) {
				String dirname = FileUtils.getFileName(directory);
				exists.onSuccess(index.contains(dirname));
			}

			@Override public void onFailure(Exception ex) {
				exists.onSuccess(false);
			}
		});
	}

	@Override public void listFilesAsync(String directory, AsyncResult<List<String>> files) {
		String indexPath = FileUtils.cleanPath(directory) + "/" + fileIndex;
		indexAsync(indexPath, files);
	}

	@Override public void listFilesAsync(final String directory, final FileFilter filter,
		final AsyncResult<List<String>> files) {
		listFilesAsync(directory, new AsyncResult<List<String>>() {
			
			@Override public void onSuccess(List<String> index) {
				String dir = FileUtils.cleanPath(directory);
				List<String> result = new ArrayList<String>();
				for (String item : index) {
					String filename = dir + "/" + item;
					if (filter.accept(new GwtFile(filename, false)))
						result.add(item);
				}
				files.onSuccess(result);
			}
			
			@Override public void onFailure(Exception ex) {
				files.onFailure(ex);
			}
		});
	}

	@Override public void listDirectoriesAsync(String directory,
		AsyncResult<List<String>> directories) {
		String indexPath = FileUtils.cleanPath(directory) + "/" + dirIndex;
		indexAsync(indexPath, directories);
	}

}
