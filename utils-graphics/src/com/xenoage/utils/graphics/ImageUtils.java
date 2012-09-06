package com.xenoage.utils.graphics;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.xenoage.utils.io.IO;


/**
 * Useful methods to work with images.
 * 
 * @author Andreas Wenger
 */
public class ImageUtils
{
	
	/**
	 * Returns an {@link ImageIcon} with the image behind the
	 * given path, or null if it can't be loaded.
	 */
	public static ImageIcon iconOrNull(String filepath)
	{
		try
		{
			return new ImageIcon(ImageIO.read(IO.openInputStream(filepath)));
		}
		catch (IOException ex)
		{
			return null;
		}
	}
	
	
	/**
	 * Returns an {@link Image} with the image behind the
	 * given path, or null if it can't be loaded.
	 */
	public static Image imageOrNull(String filepath)
	{
		try
		{
			return ImageIO.read(IO.openInputStream(filepath));
		}
		catch (IOException ex)
		{
			return null;
		}
	}

}
