package com.xenoage.utils.io;

/**
 * A filter for file paths.
 *
 * @author Andreas Wenger
 */
public interface FileFilter {

    /**
     * Checks if the specified file should be accepted.
     */
    boolean accept(String filePath);

}