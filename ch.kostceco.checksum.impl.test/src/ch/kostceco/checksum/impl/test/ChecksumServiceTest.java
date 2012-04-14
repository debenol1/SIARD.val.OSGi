package ch.kostceco.checksum.impl.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.checksum.api.service.ChecksumService;
import ch.kostceco.checksum.impl.internal.Activator;

public class ChecksumServiceTest
{
	private ServiceTracker<ChecksumService, ChecksumService> tracker;
	
	@Before
	public void setUp() throws Exception
	{
		tracker = new ServiceTracker<ChecksumService, ChecksumService>(Activator.getBundleContext(), ChecksumService.class, null);
		tracker.open();
	}

	@After
	public void tearDown() throws Exception
	{
		tracker.close();
	}

	@Test
	public void testCompare()
	{
		ChecksumService service = tracker.getService();
		Assert.assertNotNull(service);

		File file1 = new File("U:/Incubator Projekte/SIARD.val/Documentation/gebaeudeversicherung.siard");
		File file2 = new File("U:/Incubator Projekte/SIARD.val/Documentation/gebaeudeversicherung");
		try
		{
			Assert.assertFalse(service.compare(file1, file2));
		} catch (IOException e)
		{
			Assert.fail(e.getLocalizedMessage());
		}

		file1 = new File("U:/Incubator Projekte/SIARD.val/Documentation/gebaeudeversicherung.siard");
		file2 = new File("U:/Incubator Projekte/SIARD.val/Documentation/gebaeudeversicherung2.siard");
		try
		{
			Assert.assertTrue(service.compare(file1, file2));
		} 
		catch (IOException e)
		{
			Assert.fail(e.getLocalizedMessage());
		}

	}

}
