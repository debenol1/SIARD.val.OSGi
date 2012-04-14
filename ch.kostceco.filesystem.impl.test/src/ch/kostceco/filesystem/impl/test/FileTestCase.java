package ch.kostceco.filesystem.impl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.filesystem.api.service.FileSystem;
import ch.kostceco.filesystem.api.service.FileValidator;
import ch.kostceco.filesystem.impl.internal.Activator;

public class FileTestCase
{
	private ServiceTracker<FileValidator, FileValidator> validatorTracker;

	private ServiceTracker<FileSystem, FileSystem> systemTracker;

	private File dir;
	
	private File noFile;
	
	private File folder;
	
	private File wrongExtensionFile;
	
	private File rightExtensionFile;
	
	@Before
	public void setUp() throws Exception
	{
		validatorTracker = new ServiceTracker<FileValidator, FileValidator>(Activator.getBundleContext(), FileValidator.class, null);
		validatorTracker.open();

		systemTracker = new ServiceTracker<FileSystem, FileSystem>(Activator.getBundleContext(), FileSystem.class, null);
		systemTracker.open();

		/*
		 * Initialize folder for testfiles in user home.
		 */
		String path = System.getProperty("user.home").concat(File.separator.concat("SIARD.val.OSGi-Test"));
		dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		/*
		 * create some files
		 */
		noFile = new File(dir.getAbsolutePath().concat(File.separator.concat("inexistent-file.siard")));
		folder = new File(dir.getAbsolutePath().concat(File.separator.concat("directory.siard")));
		if (!folder.exists())
		{
			folder.mkdirs();
		}
		wrongExtensionFile = new File(dir.getAbsolutePath().concat(File.separator.concat("wrong-extension-file.zip")));
		if (!wrongExtensionFile.exists())
		{
			wrongExtensionFile.createNewFile();
		}
		rightExtensionFile = new File(dir.getAbsolutePath().concat(File.separator.concat("right-extension-file.siard")));
		if (!rightExtensionFile.exists())
		{
			rightExtensionFile.createNewFile();
		}
	}

	@After
	public void tearDown() throws Exception
	{
		systemTracker.close();
		validatorTracker.close();
	}

	@Test
	public void test()
	{
		FileValidator validator = validatorTracker.getService();
		Assert.assertNotNull(validator);

		Assert.assertFalse(validator.checkFile(noFile).isOK());
		Assert.assertTrue(validator.checkDirectory(noFile).isOK());
		Assert.assertTrue(validator.checkExtension(noFile, new String[] { ".siard" }).isOK());

		Assert.assertTrue(validator.checkFile(folder).isOK());
		Assert.assertFalse(validator.checkDirectory(folder).isOK());
		Assert.assertTrue(validator.checkExtension(folder, new String[] { ".siard" }).isOK());

		Assert.assertTrue(validator.checkFile(wrongExtensionFile).isOK());
		Assert.assertTrue(validator.checkDirectory(wrongExtensionFile).isOK());
		Assert.assertFalse(validator.checkExtension(wrongExtensionFile, new String[] { ".siard" }).isOK());

		Assert.assertTrue(validator.checkFile(rightExtensionFile).isOK());
		Assert.assertTrue(validator.checkDirectory(rightExtensionFile).isOK());
		Assert.assertTrue(validator.checkExtension(rightExtensionFile, new String[] { ".siard" }).isOK());
	
		Assert.assertFalse(validator.validate(noFile).isOK());
		Assert.assertFalse(validator.validate(folder).isOK());
		Assert.assertFalse(validator.validate(wrongExtensionFile).isOK());
		Assert.assertTrue(validator.validate(rightExtensionFile).isOK());

		FileSystem system = systemTracker.getService();
		Assert.assertNotNull(system);
		
		File file = new File("U:/Incubator Projekte/SIARD.val/Documentation/gebaeudeversicherung.siard");
		InputStream inputStream;
		try
		{
			inputStream = new FileInputStream(file);
			File handle = system.cache(inputStream);
			Assert.assertTrue(handle.exists());
		} 
		catch (FileNotFoundException e)
		{
			Assert.fail(e.getLocalizedMessage());
		} 
		catch (IOException e)
		{
			Assert.fail(e.getLocalizedMessage());
		}
	}

}
