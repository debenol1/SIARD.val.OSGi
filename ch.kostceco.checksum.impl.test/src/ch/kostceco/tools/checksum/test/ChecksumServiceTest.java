package ch.kostceco.tools.checksum.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.checksum.api.service.ChecksumService;
import ch.kostceco.tools.checksum.impl.internal.Activator;

public class ChecksumServiceTest
{
	private ServiceTracker<ChecksumService, ChecksumService> checksumServiceTracker;

	private ServiceTracker<LogService, LogService> logServiceTracker;

	private ServiceTracker<LogReaderService, LogReaderService> logReaderServiceTracker;

	private ChecksumService checksumService;

	private LogService logService;
	
	private LogReaderService logReaderService;
	
	private String tmpDir;

	@Before
	public void setUp() throws Exception
	{
		
		checksumServiceTracker = new ServiceTracker<ChecksumService, ChecksumService>(Activator.getBundleContext(), ChecksumService.class, null);
		checksumServiceTracker.open();
		checksumService = checksumServiceTracker.getService();
		
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
		checksumServiceTracker.close();
	}

	@Test
	public void testCompare()
	{
		Assert.assertNotNull(checksumService);
		Assert.assertNotNull(logService);
		Assert.assertNotNull(logReaderService);

		try
		{
			logReaderService.addLogListener(new LogListener() 
			{
				@Override
				public void logged(LogEntry entry)
				{
					System.out.println(entry.getMessage());
				}
			});
			
			File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung.siard")));
			String s = checksumService.getChecksum(new FileInputStream(file));
			logService.log(LogService.LOG_ERROR, "Generate checksum of " + file.getName() + ": " + s);
			
			File file2 = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung2.siard")));
			String s2 = checksumService.getChecksum(new FileInputStream(file2));
			logService.log(LogService.LOG_ERROR, "Generate checksum of " + file2.getName() + ": " + s2);

			File file3 = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung3.siard")));
			String s3 = checksumService.getChecksum(new FileInputStream(file3));
			logService.log(LogService.LOG_ERROR, "Generate checksum of " + file3.getName() + ": " + s3);

			logService.log(LogService.LOG_ERROR, "Compare " + file.getName() + " and " + file.getName() + ": " + (s.equals(s2) ? "equals" : "differs"));
			logService.log(LogService.LOG_ERROR, "Compare " + file.getName() + " and " + file.getName() + ": " + (s.equals(s3) ? "equals" : "differs"));
			logService.log(LogService.LOG_ERROR, "Compare " + file.getName() + " and " + file2.getName() + ": " + (s.equals(s3) ? "equals" : "differs"));

			Assert.assertTrue(s.equals(s2));
			Assert.assertFalse(s.equals(s3));
			Assert.assertFalse(s2.equals(s3));
		} 
		catch (FileNotFoundException e)
		{
			Assert.fail(e.getMessage());
		} 
		catch (IOException e)
		{
			Assert.fail(e.getMessage());
		}
	}
}