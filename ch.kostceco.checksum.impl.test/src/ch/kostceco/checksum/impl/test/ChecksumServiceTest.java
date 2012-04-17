package ch.kostceco.checksum.impl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	private String tmpDir;
	@Before
	public void setUp() throws Exception
	{
		tracker = new ServiceTracker<ChecksumService, ChecksumService>(Activator.getBundleContext(), ChecksumService.class, null);
		tracker.open();

		File file = File.createTempFile("tmp_", ".siard");
		tmpDir = file.getParent();
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

		System.out.println();
		System.out.println("Available Digests");
		String[] digests = service.getAvailableDigests();
		for (String digest : digests)
		{
			System.out.println("Digest: " + digest);
		}

		try
		{
			File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung.siard")));
			String s = service.getChecksum(new FileInputStream(file));
			System.out.println(file.getName() + ": " + s);
			
			File file2 = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung2.siard")));
			String s2 = service.getChecksum(new FileInputStream(file2));
			System.out.println(file.getName() + ": " + s2);

			File file3 = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung3.siard")));
			String s3 = service.getChecksum(new FileInputStream(file3));
			System.out.println(file.getName() + ": " + s3);

			System.out.println(file.getName() + " equals " + file2.getName() + ": " + s.equals(s2));
			System.out.println(file.getName() + " equals " + file3.getName() + ": " + s.equals(s3));
			System.out.println(file2.getName() + " equals " + file3.getName() + ": " + s2.equals(s3));

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