package ch.kostceco.tools.siardval.xml.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.xml.api.service.XmlService;
import ch.kostceco.tools.siardval.xml.impl.internal.Activator;

public class XmlTestCase
{
	private ServiceTracker<XmlService, XmlService> xmlServiceTracker;

	private XmlService xmlService;
	
	private ServiceTracker<LogService, LogService> logServiceTracker;

	private ServiceTracker<LogReaderService, LogReaderService> logReaderServiceTracker;

	private LogService logService;
	
	private LogReaderService logReaderService;
	
	private String tmpDir;
	
	@Before
	public void setUp() throws Exception
	{
		xmlServiceTracker = new ServiceTracker<XmlService, XmlService>(Activator.getBundleContext(), XmlService.class, null);
		xmlServiceTracker.open();
		xmlService = xmlServiceTracker.getService();

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
		xmlServiceTracker.close();
	}

	@Test
	public void testValidateMetadataXmlAgainstInternalMetadataXsd()
	{
		try
		{
			URL url = xmlService.getClass().getResource("/META-INF/metadata.xsd");
			File file = new File(tmpDir.concat(File.separator.concat("metadata.xml")));
			InputStream in = new FileInputStream(file);
			Assert.assertTrue(xmlService.validate(in, url).isOK());
		} 
		catch (FileNotFoundException e)
		{
			Assert.fail("FileNotFoundException thrown");
		}
	}

	@Test
	public void testValidateMetadataXmlAgainstExternalMetadataXsd()
	{
		try
		{
			File file = new File(tmpDir.concat(File.separator.concat("metadata.xml")));
			InputStream in = new FileInputStream(file);
			file = new File(tmpDir.concat(File.separator.concat("metadata.xsd")));
			URL url = file.toURI().toURL();
			Assert.assertTrue(xmlService.validate(in, url).isOK());
		} 
		catch (FileNotFoundException e)
		{
			Assert.fail("FileNotFoundException thrown");
		} 
		catch (MalformedURLException e)
		{
			Assert.fail("MalformedURLException thrown");
		}
	}

	@Test
	public void testValidateMetadataXsdAgainstXmlSchema()
	{
		try
		{
			File file = new File(tmpDir.concat(File.separator.concat("metadata.xsd")));
			URL url = file.toURI().toURL();
			Assert.assertTrue(xmlService.validate(url).isOK());
		} 
		catch (MalformedURLException e)
		{
			Assert.fail("MalformedURLException thrown");
		}
	}

//	@Test
//	public void testGetSiardVersion()
//	{
//		fail("Not yet implemented");
//	}

}
