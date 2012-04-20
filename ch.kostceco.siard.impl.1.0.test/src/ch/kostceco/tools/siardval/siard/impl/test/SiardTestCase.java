package ch.kostceco.tools.siardval.siard.impl.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.siard.api.service.SiardService;
import ch.kostceco.tools.siardval.siard.impl.internal.Activator;

public class SiardTestCase
{
	private ServiceTracker<SiardService, SiardService> siardServiceTracker;

	private SiardService siardService;
	
	private ServiceTracker<LogService, LogService> logServiceTracker;

	private ServiceTracker<LogReaderService, LogReaderService> logReaderServiceTracker;

	private LogService logService;
	
	private LogReaderService logReaderService;
	
	private String tmpDir;
	
	@Before
	public void setUp() throws Exception
	{
		siardServiceTracker = new ServiceTracker<SiardService, SiardService>(Activator.getContext(), SiardService.class, null);
		siardServiceTracker.open();
		siardService = siardServiceTracker.getService();

		logServiceTracker = new ServiceTracker<LogService, LogService>(Activator.getContext(), LogService.class, null);
		logServiceTracker.open();
		logService = logServiceTracker.getService();

		logReaderServiceTracker = new ServiceTracker<LogReaderService, LogReaderService>(Activator.getContext(), LogReaderService.class, null);
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
		siardServiceTracker.close();
	}

//	@Test
//	public void testContentStructure()
//	{
//		try
//		{
//			Enumeration<? extends ZipEntry> entries = service.listEntries(file);
//			while (entries.hasMoreElements())
//			{
//				ZipEntry entry = entries.nextElement();
//				System.out.println(entry.getName() + " " + entry.getCompressedSize() + " " + entry.getSize());
//			}
//		} 
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testMetadata()
	{
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung.siard")));
		Assert.assertTrue(siardService.validateMetadataXmlAgainstMetadataXsd(file).isOK());
		Assert.assertTrue(siardService.validateMetadataXmlAgainstInternalMetadataXsd(file).isOK());
		Assert.assertTrue(siardService.validateMetadataXsd(file).isOK());
		Assert.assertTrue(siardService.validatesMetadataXsdAgainstInternalMetadataXsd(file).isOK());
	}
}
