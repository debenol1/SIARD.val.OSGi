package ch.kostceco.zip.api.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ch.kostceco.zip.api.IStatus;

public interface ZipService 
{
	/**
	 * Checks if file is a valid zip file. 
	 * 
	 * @param file
	 * @return
	 */
	IStatus validateZip(File file);

	/**
	 * Checks if file is a valid zip file
	 * 
	 * @param file
	 * @param method Method.HEADER_CHECK or Method.FULL_CHECK either. While Method.HEADER_CHECK checks only, if the file opens, Method.FULL_CHECK opens each entry and reads the stream until its end (saver method).
	 * @return
	 */
	IStatus validateZip(File file, Method method);

	/**
	 * Checks if file is encrypted. Each entry is checked.
	 * @param file
	 * @return
	 */
	IStatus checkEncryption(File file);

	/**
	 * Checks if file is compressed. Each entry is checked.
	 * @param file
	 * @return
	 */
	IStatus checkCompression(File file);

	IStatus checkDirectoryStructure(File file, String[] validEntries);

	InputStream getInputStream(File file, String element) throws IOException;

	/**
	 * collects the directory entries of the zip file and returns them as an array of strings
	 * 
	 * @param file
	 * @return
	 */
	String[] getDirectories(File file) throws IOException;
	
	public enum Method
	{
		HEADER_CHECK, FULL_CHECK;
	}
}
