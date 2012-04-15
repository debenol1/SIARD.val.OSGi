package ch.kostceco.siard.impl.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.checksum.api.service.ChecksumService;
import ch.kostceco.filesystem.api.service.FileSystem;
import ch.kostceco.siard.api.IStatus;
import ch.kostceco.siard.api.Status;
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
	
	protected void startup(ComponentContext context)
	{
		this.context = context;
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
	
//	@Override
//	public IStatus checkDirectoryStructure(String path)
//	{
//		IStatus status = Status.OK_STATUS;
//		File file = new File(path);
//		try
//		{
//			Document document = getMetadataXml(path);
//			Element root = document.getRootElement();
//			System.out.println(root);
//		}
//		catch (IOException e)
//		{
//			status = new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Datei " + file.getName() + " in " + file.getParent() + " konnte nicht gelesen werden.", e);
//		} 
//		catch (JDOMException e)
//		{
//			e.printStackTrace();
//			status = new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Ungültiges Dateiformat.", e);
//		}
//		return status;
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
	public IStatus<ValidateDirectoryStructureAction, ValidateDirectoryStructureMessage> checkDirectoryStructure(File file)
	{
		IStatus<ValidateDirectoryStructureAction, ValidateDirectoryStructureMessage> status = new Status<ValidateDirectoryStructureAction, ValidateDirectoryStructureMessage>(new ValidateDirectoryStructureAction());
		try
		{
			checkDirectoryStructure(status, zipService.getDirectories(file));
		} 
		catch (IOException e)
		{
			status.update(ValidateDirectoryStructureMessage.FILE_FORMAT, false);
			return status;
		}
		return status;
	}

	private IStatus<ValidateDirectoryStructureAction, ValidateDirectoryStructureMessage> checkDirectoryStructure(IStatus<ValidateDirectoryStructureAction, ValidateDirectoryStructureMessage> status, String[] directories)
	{
		for (String directory : directories)
		{
			String path[] = directory.split("/");
			if (path.length == 1)
			{
				/*
				 * top level element
				 */
			}
			if (directory.equals("header/"))
			{
				status.getAction().addMessage(ValidateDirectoryStructureMessage.HEADER, true);
			}
			else if (directory.equals("content/"))
			{
				status.getAction().addMessage(ValidateDirectoryStructureMessage.CONTENT, true);
			}
//			else if (directory.startsWith("content/schema"))
//			{
//				String[] elements = directory.split("/");
//				System.out.println(elements);
//				if (elements.length > 4)
//				{
//					status.update(ok)
//				}
//			}
		}
		return status;
	}
}
