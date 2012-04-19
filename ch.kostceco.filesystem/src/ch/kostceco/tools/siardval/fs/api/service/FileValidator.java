package ch.kostceco.tools.siardval.fs.api.service;

import java.io.File;

import org.eclipse.core.runtime.IStatus;

public interface FileValidator 
{
	/**
	 * Checks if file exists and is readable
	 * 
	 * @param file the file to check
	 * @return status
	 */
	IStatus checkReadable(File file);

	/**
	 * Checks if the file is a directory (must not be a directory
	 * 
	 * @param file the file to check
	 * @return status
	 */
	IStatus checkDirectory(File file);

	/**
	 * Validates by calling all relevant methods in a reasonable order (checkFile, checkDirectory, checkExtension)
	 * @param file
	 * @return
	 */
	IStatus validate(File file);
}
