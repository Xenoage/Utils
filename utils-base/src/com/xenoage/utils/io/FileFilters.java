package com.xenoage.utils.io;


/**
 * Some useful file filters.
 *
 * @author Andreas Wenger
 */
public class FileFilters {

	/**
	 * A filter for directories.
	 * Directories beginning with a "." are ignored
	 * (e.g. ".svn").
	 */
	public static FileFilter directoriesFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.isDirectory() && !file.getName().startsWith(".");
		}
	};

	/**
	 * A filter for files with ".java" ending.
	 */
	public static FileFilter javaFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".java");
		}
	};

	/**
	 * A filter for files with ".po" ending.
	 */
	public static FileFilter poFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".po");
		}
	};

	/**
	 * A filter for files with ".xml" ending.
	 */
	public static FileFilter xmlFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".xml");
		}
	};

	/**
	 * A filter for files with ".svg" ending.
	 */
	public static FileFilter svgFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".svg");
		}
	};

	/**
	 * A filter for files with ".ttf" ending.
	 */
	public static FileFilter ttfFilter = new FileFilter() {

		@Override public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".ttf");
		}
	};


	/**
	 * Returns a filter which accepts files which have one of the
	 * given extensions (if needed, include a "." before the extension, like ".xml").
	 */
	public static FileFilter extFilter(final String... extensions) {
		FileFilter ret = new FileFilter() {

			@Override public boolean accept(File file) {
				String filename = file.getName().toLowerCase();
				for (String extension : extensions) {
					if (filename.endsWith(extension))
						return true;
				}
				return false;
			}
		};
		return ret;
	}

	/**
	 * Returns a filter which accepts all of the given filters.
	 */
	public static FileFilter orFilter(final FileFilter... filters) {
		FileFilter ret = new FileFilter() {

			@Override public boolean accept(File file) {
				for (FileFilter filter : filters) {
					if (filter.accept(file))
						return true;
				}
				return false;
			}
		};
		return ret;
	}

}
