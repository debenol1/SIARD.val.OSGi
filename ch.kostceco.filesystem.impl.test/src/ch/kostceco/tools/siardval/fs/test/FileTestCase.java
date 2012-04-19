package ch.kostceco.tools.siardval.fs.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.fs.api.service.FileSystem;
import ch.kostceco.tools.siardval.fs.api.service.FileValidator;
import ch.kostceco.tools.siardval.fs.impl.internal.Activator;

public class FileTestCase
{
	private ServiceTracker<FileValidator, FileValidator> fileValidatorTracker;

	private FileValidator fileValidator;
	
	private ServiceTracker<FileSystem, FileSystem> fileSystemTracker;

	private FileSystem fileSystem;
	
	private ServiceTracker<LogService, LogService> logServiceTracker;

	private ServiceTracker<LogReaderService, LogReaderService> logReaderServiceTracker;

	private LogService logService;
	
	private LogReaderService logReaderService;
	
	private String tmpDir;
	
	@Before
	public void setUp() throws Exception
	{
		fileValidatorTracker = new ServiceTracker<FileValidator, FileValidator>(Activator.getBundleContext(), FileValidator.class, null);
		fileValidatorTracker.open();
		fileValidator = fileValidatorTracker.getService();

		fileSystemTracker = new ServiceTracker<FileSystem, FileSystem>(Activator.getBundleContext(), FileSystem.class, null);
		fileSystemTracker.open();

		logServiceTracker = new ServiceTracker<LogService, LogService>(Activator.getBundleContext(), LogService.class, null);
		logServiceTracker.open();
		logService = logServiceTracker.getService();

		logReaderServiceTracker = new ServiceTracker<LogReaderService, LogReaderService>(Activator.getBundleContext(), LogReaderService.class, null);
		logReaderServiceTracker.open();
		logReaderService = logReaderServiceTracker.getService();
		
		File file = File.createTempFile("tmp_", ".siard");
		tmpDir = file.getParent();
	}

	@After
	public void tearDown() throws Exception
	{
		logReaderServiceTracker.close();
		logServiceTracker.close();
		fileSystemTracker.close();
		fileValidatorTracker.close();
	}

	@Test
	public void testNullFile()
	{
		
		File file = new File(tmpDir.concat(File.separator.concat("gigi.txt")));
		Assert.assertNotNull(fileValidator);
		Assert.assertFalse(fileValidator.checkReadable(file).isOK());
		Assert.assertTrue(fileValidator.checkDirectory(file).isOK());
		Assert.assertFalse(fileValidator.validate(file).isOK());
	}
	
	public void testFolder()
	{
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung")));
		Assert.assertNotNull(fileValidator);
		Assert.assertTrue(fileValidator.checkReadable(file).isOK());
		Assert.assertTrue(fileValidator.checkDirectory(file).isOK());
		Assert.assertTrue(fileValidator.validate(file).isOK());
	}
	
	public void testFile()
	{
		File file = new File(tmpDir.concat(File.separator.concat("geaeudeversicherung.siard")));
		Assert.assertNotNull(fileValidator);
		Assert.assertTrue(fileValidator.checkReadable(file).isOK());
		Assert.assertTrue(fileValidator.checkDirectory(file).isOK());
		Assert.assertTrue(fileValidator.validate(file).isOK());
	}

	public void testLockedFile()
	{
		File file = new File(tmpDir.concat(File.separator.concat("test.odt")));
		Assert.assertNotNull(fileValidator);
		Assert.assertTrue(fileValidator.checkReadable(file).isOK());
		Assert.assertTrue(fileValidator.checkDirectory(file).isOK());
		Assert.assertTrue(fileValidator.validate(file).isOK());
	}
}
