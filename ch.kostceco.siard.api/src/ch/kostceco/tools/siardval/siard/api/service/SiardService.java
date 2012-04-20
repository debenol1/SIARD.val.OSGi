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
	
	/**
	 * Validates the metadata.xsd in the target siard file
	 * 
	 * @param file
	 * @return
	 */
	IStatus validateMetadataXsd(File file);

	/**
	 * Validates metadata.xml in header section of target siard file against metadata.xsd residing in header section of target siard file
	 * 
	 * @param file
	 * @return
	 */
	IStatus validateMetadataXmlAgainstMetadataXsd(File file);
	
	/**
	 * Validates metadata.xml in header section of target siard file against local metadata.xsd
	 * 
	 * @param file
	 * @return
	 */
	IStatus validateMetadataXmlAgainstInternalMetadataXsd(File file);

	/**
	 * Validates metadata.xsd in header section of target siard file against local metadata.xsd. Note that the validation is carried out as a checksum compare. So the metadata.xsd of the siard file must be identical to original metadata.xsd.
	 * 
	 * @param file
	 * @return
	 */
	IStatus validatesMetadataXsdAgainstInternalMetadataXsd(File file);
	
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
	String getVersion(File file) throws IOException;

	/**
	 * Checks the siard version. Extracts the siard version from the siard file and compares it with the
	 * siard version provided in the service respective. If same version returns IStatus.OK_STATUS, if 
	 * wrong version returns IStatus.CANCEL_STATUS, if an error occures returns IStatus.ERROR_STATUS with appropriate message;
	 * 
	 * @param file
	 * @return
	 */
	IStatus checkVersion(File file);
}
