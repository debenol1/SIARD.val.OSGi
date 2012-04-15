package ch.kostceco.siard.impl.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.checksum.api.service.ChecksumService;
import ch.kostceco.filesystem.api.service.FileSystem;
import ch.kostceco.siard.api.Action;
import ch.kostceco.siard.api.CheckVersionMessage;
import ch.kostceco.siard.api.IStatus;
import ch.kostceco.siard.api.Status;
import ch.kostceco.siard.api.ValidateDirectoryStructureMessage;
import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.zip.api.service.ZipService;

public class SiardServiceComponent implements SiardService
{
	private static final String PROPERTY_KEY_SIARD_VERSION = "siard.version";
	
	private static final String PROPERTY_KEY_SIARD_EXTENSIONS = "siard.extensions";
	
	private LogService logService;
	
	private ZipService zipService;
	
	private FileSystem fileSystem;
	
	private ChecksumService checksumService;
	
	private ComponentContext context;

	private SAXBuilder saxBuilder;
	
	protected void startup(ComponentContext context)
	{
		this.context = context;
		this.saxBuilder = new SAXBuilder();
		this.logService.log(LogService.LOG_DEBUG, "Service " + this.getClass().getName() + " started.");
	}
	
	protected void shutdown(ComponentContext context)
	{
		this.logService.log(LogService.LOG_DEBUG, "Service " + this.getClass().getName() + " started.");
		this.context = null;
	}

	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void clearLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void setZipService(ZipService service)
	{
		this.zipService = service;
	}
	
	protected void clearZipService(ZipService service)
	{
		this.zipService = null;
	}
	
	protected void setFileSystem(FileSystem fileSystem)
	{
		this.fileSystem = fileSystem;
	}
	
	protected void clearFileSystem(FileSystem fileSystem)
	{
		this.fileSystem = null;
	}
	
	protected void setChecksumService(ChecksumService service)
	{
		this.checksumService = service;
	}
	
	protected void clearChecksumService(ChecksumService service)
	{
		this.checksumService = null;
	}
	
	@Override
	public String getVersion()
	{
		return (String) context.getProperties().get(PROPERTY_KEY_SIARD_VERSION);
	}
	
	@Override
	public boolean validateHeaderMetadataXsd(File file) throws IOException
	{
		InputStream in = this.getClass().getResourceAsStream("/META-INF/metadata.xsd");
		File internal = fileSystem.cache(in);
		in.close();
		in = this.getClass().getResourceAsStream("/META-INF/metadata.xsd");
		File external = fileSystem.cache(in);
		in.close();
		return checksumService.compare(internal, external);
	}
	
//	@Override
//	public Document getDocument(InputStream inputStream, URL schema)
//	{
//		Document document = null;
//		try
//		{
//			saxBuilder.setValidation(true);
//			saxBuilder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", schema);
//			document = saxBuilder.build(inputStream);
//			Element root = document.getRootElement();
//			System.out.println(root);
//			return document;
//		} 
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		catch (JDOMException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return document;
//	}
//	private Document getMetadataXml(String path) throws IOException, JDOMException
//	{
//		File file = new File(path);
//		ZipFile zipFile = new ZipFile(file);
//		ZipEntry zipEntry = zipFile.getEntry("header/metadata.xml");
//		InputStream metadata = zipFile.getInputStream(zipEntry);
//		SAXBuilder builder = new SAXBuilder(true);
//		builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", this.getClass().getResource("META-INF/metadata.xsd"));
//		Document document = builder.build(metadata);
//		Element root = document.getRootElement();
//		System.out.println(root);
//		return document;
//	}
	
	@Override
	public String[] getValidExtensions()
	{
		Object property = context.getProperties().get(PROPERTY_KEY_SIARD_EXTENSIONS);
		if (property instanceof String)
		{
			return new String[] { (String) property };
		}
		else if (property instanceof String[])
		{
			return (String[]) property;
		}
		return new String[0];
	}

	@Override
	public IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkDirectoryStructure(File file)
	{
		IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status = new Status<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage>(new Action<ValidateDirectoryStructureMessage>());
		try
		{	
			String[] directories = zipService.getDirectories(file);
			status.update(ValidateDirectoryStructureMessage.FILE_FORMAT, true);
			checkDirectoryStructure(status, directories);
		} 
		catch (IOException e)
		{
			status.update(ValidateDirectoryStructureMessage.FILE_FORMAT, false);
			return status;
		}
		return status;
	}

	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkDirectoryStructure(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String[] directories)
	{
		Arrays.sort(directories);
		for (String directory : directories)
		{
			String path[] = directory.split("/");
			if (path.length == 1)
			{
				/*
				 * top level element
				 * 
				 * There are only two top level elements allowed:
				 * 
				 * header/
				 * content/
				 * 
				 * both are directories
				 */
				checkRoot(status, directory);
			}
			else if (directory.startsWith("header/"))
			{
				status.update(ValidateDirectoryStructureMessage.NESTED_HEADER_DIRECTORY, false);
			}
			else if (directory.startsWith("content/"))
			{
				status = checkContent(status, directory);
			}
		}
		return status;
	}
	
	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkRoot(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String entry)
	{
		if (entry.equals("header/"))
		{
			status.update(ValidateDirectoryStructureMessage.HEADER, true);
		}
		else if (entry.equals("content/"))
		{
			status.update(ValidateDirectoryStructureMessage.CONTENT, true);
		}
		else
		{
			status.update(ValidateDirectoryStructureMessage.TOP_LEVEL_ENTRY, false);
		}
		return status;
	}

	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkContent(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String entry)
	{
		if (entry.startsWith("content/schema"))
		{
			String[] elements = entry.split("/");
			if (elements.length == 2)
			{
				status = checkSchema(status, elements[1]);
			}
			else if (elements.length == 3)
			{
				status = checkTable(status, elements[2]);
			}
			else if (elements.length == 4)
			{
				status = checkLob(status, elements[3]);
			}
			else
			{
				status.update(ValidateDirectoryStructureMessage.CONTENT, false);
			}
		}
		else
		{
			status.update(ValidateDirectoryStructureMessage.SCHEMA, false);
		}
		return status;
	}
	
	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkSchema(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String entry)
	{
		String schema = entry.replace("schema", "");
		try
		{
			Integer.valueOf(schema);
			status.update(ValidateDirectoryStructureMessage.SCHEMA, true);
		}
		catch (NumberFormatException e)
		{
			status.update(ValidateDirectoryStructureMessage.SCHEMA, false);
		}
		return status;
	}
	
	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkTable(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String entry)
	{
		String table = entry.replace("table", "");
		try
		{
			Integer.valueOf(table);
			status.update(ValidateDirectoryStructureMessage.TABLE, true);
		}
		catch (NumberFormatException e)
		{
			status.update(ValidateDirectoryStructureMessage.TABLE, false);
		}
		return status;
	}
	
	private IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkLob(IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status, String entry)
	{
		String lob = entry.replace("lob", "");
		try
		{
			Integer.valueOf(lob);
			status.update(ValidateDirectoryStructureMessage.LOB, true);
		}
		catch (NumberFormatException e)
		{
			status.update(ValidateDirectoryStructureMessage.LOB, false);
		}
		return status;
	}

	@Override
	public IStatus<Action<CheckVersionMessage>, CheckVersionMessage> checkVersion(File file)
	{
		try
		{
			Document document = getDocument(file);
			System.out.println();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Document getDocument(File file) throws IOException
	{
		InputStream inputStream = zipService.getInputStream(file, "header/metadata.xml");
		URL schema = this.getClass().getResource("/META-INF/metadata.xsd");
		Document document = null;
		try
		{
			saxBuilder.setValidation(true);
			saxBuilder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", schema);
			document = saxBuilder.build(inputStream);
			Element root = document.getRootElement();
			System.out.println(root);
			return document;
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (JDOMException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
}
