package com.xenoage.utils.io;

/**
 * A filter for {@link File}s.
 *
 * @author Andreas Wenger
 */
public interface FileFilter {

    /**
     * Checks if the specified file should be accepted.
     */
    boolean accept(File file);

}