package ch.kostceco.zip.impl.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.zip.api.service.ZipService;
import ch.kostceco.zip.impl.internal.Activator;

public class ZipTestCase
{
	private final String path = "U:/Incubator Projekte/SIARD.val/Test Data";
	
	private ServiceTracker<ZipService, ZipService> tracker;

	private ZipService service;
	
	private File pdf;
	
	private File siard;
	
	@Before
	public void setUp() throws Exception
	{
		tracker = new ServiceTracker<ZipService, ZipService>(Activator.getBundleContext(), ZipService.class, null);
		tracker.open();
		
		service = tracker.getService();

		/*
		 * preconditions: the files gebaeudeversicherung.siard and test.pdf must be in the users temp directory befor executing the tests
		 * 
		 */
		File tmp = File.createTempFile("tmp_", ".siard");
		siard = new File(tmp.getParent().concat(File.separator.concat("gebaeudeversicherung.siard")));
		pdf = new File(tmp.getParent().concat(File.separator.concat("test.pdf")));
	}
	
	@After
	public void tearDown() throws Exception
	{
		tracker.close();
	}

	@Test
	public void testIntegrity()
	{
		Assert.assertNotNull(service);
		Assert.assertFalse(service.checkIntegrity(pdf).isOK());
		Assert.assertTrue(service.checkIntegrity(siard).isOK());
	}
	
	@Test
	public void testCompression()
	{
		Assert.assertNotNull(service);
		Assert.assertFalse(service.checkCompression(pdf).isOK());
		Assert.assertTrue(service.checkCompression(siard).isOK());
	}
	
	@Test
	public void testEncryption()
	{
		Assert.assertNotNull(service);
		Assert.assertFalse(service.checkEncryption(pdf).isOK());
		Assert.assertTrue(service.checkEncryption(siard).isOK());
	}
}
