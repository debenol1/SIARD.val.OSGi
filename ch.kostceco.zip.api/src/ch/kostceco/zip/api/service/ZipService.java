package ch.kostceco.zip.api.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import org.eclipse.core.runtime.IStatus;

public interface ZipService 
{
	/**
	 * Checks if file is a valid zip file. 
	 * 
	 * @param file
	 * @return
	 */
	IStatus checkIntegrity(File file);

	/**
	 * Checks if file is a valid zip file
	 * 
	 * @param file
	 * @param method Method.HEADER_CHECK or Method.FULL_CHECK either. While Method.HEADER_CHECK checks only, if the file opens, Method.FULL_CHECK opens each entry and reads the stream until its end (saver method).
	 * @return
	 */
	IStatus checkIntegrity(File file, Method method);

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

//	IStatus checkDirectoryStructure(File file, String[] validEntries);

	InputStream getEntry(File file, String path) throws IOException;

//	/**
//	 * collects the directory entries of the zip file and returns them as an array of strings
//	 * 
//	 * @param file
//	 * @return
//	 */
//	String[] getDirectories(File file) throws IOException;
//	
//	/**
//	 * returns a list of immediate children of parent
//	 * 
//	 * @param parent
//	 * @return
//	 */
//	List<String> getChildren(File file, String parent) throws IOException;
//	
//	/**
//	 * returns a list of all descendants of parent
//	 * 
//	 * @param parent
//	 * @return
//	 */
//	List<String> getDescendants(File file, String parent) throws IOException;
//	
//	/**
//	 * return the entryname if found else null
//	 * 
//	 * @param file
//	 * @param entry
//	 * @return
//	 */
//	String getEntry(File file, String path) throws IOException;
	
	public Enumeration<? extends ZipEntry> listEntries(File file) throws IOException;

	public enum Method
	{
		HEADER_CHECK, FULL_CHECK;
	}
}
