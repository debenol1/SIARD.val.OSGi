package ch.kostceco.tools.siardval.siard.api.service;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import org.eclipse.core.runtime.IStatus;

public interface SiardService
{
	/**
	 * Checks the filename against a list of valid extensions. The valid extensions are 
	 * stored as component properties in the siard service implementation bundles.
	 *
	 * @param file the file to check
	 * @param extensions an array of valid extensions to check against
	 * @return status
	 */
	IStatus checkExtension(File file);
	
	IStatus validateMetadataXsd(File file);

	//	IStatus checkVersion(File file);
	
	/**
	 * returns an enumeration of zip file entries
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	Enumeration<? extends ZipEntry> listEntries(File file) throws IOException;

	/**
	 * returns the version of siard for the given file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String getVersion(File file) throws Exception;
}
