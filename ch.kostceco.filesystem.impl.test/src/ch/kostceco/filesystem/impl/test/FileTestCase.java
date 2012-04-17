package ch.kostceco.filesystem.impl.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

	private FileValidator validator;
	
	private ServiceTracker<FileSystem, FileSystem> systemTracker;

	private FileSystem system;
	
	private File pdf;
	
	private File siard;
	
	@Before
	public void setUp() throws Exception
	{
		validatorTracker = new ServiceTracker<FileValidator, FileValidator>(Activator.getBundleContext(), FileValidator.class, null);
		validatorTracker.open();
		validator = validatorTracker.getService();

		systemTracker = new ServiceTracker<FileSystem, FileSystem>(Activator.getBundleContext(), FileSystem.class, null);
		systemTracker.open();

		File tmp = File.createTempFile("tmp_", ".siard");
		
		siard = new File(tmp.getParent().concat(File.separator.concat("gebaeudeversicherung.siard")));
		if (!siard.exists())
		{
			InputStream in = this.getClass().getResourceAsStream("gebaeudeversicherung.siard");
			OutputStream out = new FileOutputStream(siard);
			while (in.available() > 0)
			{
				byte[] b = new byte[in.available()];
				int len = in.read(b);
				out.write(b, 0, len);
			}
			in.close();
			out.close();
		}

		pdf = new File(tmp.getParent().concat(File.separator.concat("test.pdf")));
		if (!pdf.exists())
		{
			InputStream in = this.getClass().getResourceAsStream("test.pdf");
			OutputStream out = new FileOutputStream(pdf);
			while (in.available() > 0)
			{
				byte[] b = new byte[in.available()];
				int len = in.read(b);
				out.write(b, 0, len);
			}
			in.close();
			out.close();
		}
	}

	@After
	public void tearDown() throws Exception
	{
		systemTracker.close();
		validatorTracker.close();
	}

	@Test
	public void testNullFile()
	{
		Assert.assertNotNull(validator);
		Assert.assertFalse(validator.checkReadable(new File("")).isOK());
		Assert.assertTrue(validator.checkDirectory(new File("")).isOK());
		Assert.assertFalse(validator.validate(new File("")).isOK());
	}
	
	public void testPdfFile()
	{
		Assert.assertNotNull(validator);
		Assert.assertTrue(validator.checkReadable(pdf).isOK());
		Assert.assertTrue(validator.checkDirectory(pdf).isOK());
		Assert.assertTrue(validator.validate(pdf).isOK());
	}
	
	public void testSiardFile()
	{
		Assert.assertNotNull(validator);
		Assert.assertTrue(validator.checkReadable(siard).isOK());
		Assert.assertTrue(validator.checkDirectory(siard).isOK());
		Assert.assertTrue(validator.validate(siard).isOK());
	}
}
