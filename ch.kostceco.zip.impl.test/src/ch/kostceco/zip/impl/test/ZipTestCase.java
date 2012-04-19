package ch.kostceco.zip.impl.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.zip.api.service.ZipService;
import ch.kostceco.tools.siardval.zip.impl.internal.Activator;

public class ZipTestCase
{
	private ServiceTracker<ZipService, ZipService> zipServiceTracker;

	private ServiceTracker<LogService, LogService> logServiceTracker;

	private ServiceTracker<LogReaderService, LogReaderService> logReaderServiceTracker;

	private ZipService zipService;

	private LogService logService;
	
	private LogReaderService logReaderService;
	
	private String tmpDir;

	@Before
	public void setUp() throws Exception
	{
		zipServiceTracker = new ServiceTracker<ZipService, ZipService>(Activator.getBundleContext(), ZipService.class, null);
		zipServiceTracker.open();
		zipService = zipServiceTracker.getService();
		
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
		zipServiceTracker.close();
	}

	@Test
	public void testZip()
	{
		Assert.assertNotNull(zipService);
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung.siard")));
		logService.log(LogService.LOG_INFO, "Check file " + file.getName());
		Assert.assertTrue(zipService.checkIntegrity(file).isOK());
		Assert.assertTrue(zipService.checkCompression(file).isOK());
		Assert.assertTrue(zipService.checkEncryption(file).isOK());
	}
	
	@Test
	public void testFolder()
	{
		Assert.assertNotNull(zipService);
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung")));
		logService.log(LogService.LOG_INFO, "Check file " + file.getName());
		Assert.assertFalse(zipService.checkIntegrity(file).isOK());
		Assert.assertFalse(zipService.checkCompression(file).isOK());
		Assert.assertTrue(zipService.checkEncryption(file).isOK());
	}
	
	@Test
	public void testFile()
	{
		Assert.assertNotNull(zipService);
		File file = new File(tmpDir.concat(File.separator.concat("test.pdf")));
		logService.log(LogService.LOG_INFO, "Check file " + file.getName());
		Assert.assertFalse(zipService.checkIntegrity(file).isOK());
		Assert.assertFalse(zipService.checkCompression(file).isOK());
		Assert.assertTrue(zipService.checkEncryption(file).isOK());
	}
	
	@Test
	public void testCompressedZip()
	{
		Assert.assertNotNull(zipService);
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung3.siard")));
		logService.log(LogService.LOG_INFO, "Check file " + file.getName());
		Assert.assertFalse(zipService.checkIntegrity(file).isOK());
		Assert.assertFalse(zipService.checkCompression(file).isOK());
		Assert.assertTrue(zipService.checkEncryption(file).isOK());
	}
}
