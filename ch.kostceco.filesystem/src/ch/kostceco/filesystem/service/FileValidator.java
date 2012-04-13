package ch.kostceco.filesystem.service;

import java.io.File;

import ch.kostceco.filesystem.IStatus;

public interface FileValidator 
{
	/**
	 * Checks if file exists
	 * 
	 * @param file the file to check
	 * @return status
	 */
	IStatus checkFile(File file);

	/**
	 * Checks the filename against a list of valid extensions
	 *
	 * @param file the file to check
	 * @param extensions an array of valid extensions to check against
	 * @return status
	 */
	IStatus checkExtension(File file, String[] extensions);
	
	/**
	 * Checks if the file is a directory (must not be a directory
	 * 
	 * @param file the file to check
	 * @return status
	 */
	IStatus checkDirectory(File file);
}
