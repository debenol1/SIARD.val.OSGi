package ch.kostceco.zip.impl.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.zip.api.service.ZipService;
import ch.kostceco.zip.api.service.ZipService.Method;
import ch.kostceco.zip.impl.internal.Activator;

public class ZipTestCase
{
	private final String path = "U:/Incubator Projekte/SIARD.val/Documentation/test";
	
	private ServiceTracker<ZipService, ZipService> tracker;

	private File dir;
	
	@Before
	public void setUp() throws Exception
	{
		tracker = new ServiceTracker<ZipService, ZipService>(Activator.getBundleContext(), ZipService.class, null);
		tracker.open();
		
		/*
		 * Initialize folder for testfiles in user home.
		 */
		String path = System.getProperty("user.home").concat(File.separator.concat("SIARD.val.OSGi-Test"));
		dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}

	}
	
	@After
	public void tearDown() throws Exception
	{
		tracker.close();
	}

	@Test
	public void test()
	{
		ZipService service = tracker.getService();
		if (service != null)
		{

			Assert.assertFalse(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("is-a-pdf.siard")))).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.siard")))).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.zip")))).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.siard")))).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.zip")))).isOK());

			Assert.assertFalse(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("is-a-pdf.siard"))), Method.FULL_CHECK).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.siard"))), Method.FULL_CHECK).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.zip"))), Method.FULL_CHECK).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.siard"))), Method.FULL_CHECK).isOK());
			Assert.assertTrue(service.validateZip(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.zip"))), Method.FULL_CHECK).isOK());

			Assert.assertFalse(service.checkCompression(new File(dir.getAbsolutePath().concat(File.separator.concat("is-a-pdf.siard")))).isOK());
			Assert.assertTrue(service.checkCompression(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.siard")))).isOK());
			Assert.assertFalse(service.checkCompression(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.zip")))).isOK());
			Assert.assertTrue(service.checkCompression(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.siard")))).isOK());
			Assert.assertTrue(service.checkCompression(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.zip")))).isOK());

			Assert.assertFalse(service.checkEncryption(new File(dir.getAbsolutePath().concat(File.separator.concat("is-a-pdf.siard")))).isOK());
			Assert.assertTrue(service.checkEncryption(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.siard")))).isOK());
			Assert.assertTrue(service.checkEncryption(new File(dir.getAbsolutePath().concat(File.separator.concat("gebaeudeversicherung.zip")))).isOK());
			Assert.assertTrue(service.checkEncryption(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.siard")))).isOK());
			Assert.assertTrue(service.checkEncryption(new File(dir.getAbsolutePath().concat(File.separator.concat("Northwind.zip")))).isOK());
		}
	}

}
