package ch.kostceco.siard.impl.test;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.siard.impl.internal.Activator;

public class SiardTestCase
{
	private ServiceTracker<SiardService, SiardService> tracker;

	private File file;

	private SiardService service;
	
	@Before
	public void setUp() throws Exception
	{
		String path = "U:/Incubator Projekte/SIARD.val/Test Data/gebaeudeversicherung.siard";
		file = new File(path);

		tracker = new ServiceTracker<SiardService, SiardService>(Activator.getContext(), SiardService.class, null);
		tracker.open();

		service = tracker.getService();
	}

	@After
	public void tearDown() throws Exception
	{
		service = null;
		tracker.close();
	}

	@Test
	public void testContentStructure()
	{
		try
		{
			Enumeration<? extends ZipEntry> entries = service.listEntries(file);
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				System.out.println(entry.getName() + " " + entry.getCompressedSize() + " " + entry.getSize());
			}
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
