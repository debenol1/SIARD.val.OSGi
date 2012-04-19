package ch.kostceco.tools.siardval.siard.impl.internal.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.checksum.api.service.ChecksumService;
import ch.kostceco.tools.siardval.fs.api.service.FileSystem;
import ch.kostceco.tools.siardval.siard.api.service.SiardService;
import ch.kostceco.tools.siardval.siard.impl.internal.Activator;
import ch.kostceco.tools.siardval.xml.api.service.XmlService;
import ch.kostceco.tools.siardval.zip.api.service.ZipService;

public class SiardServiceComponent implements SiardService
{
	private static final String PROPERTY_KEY_SIARD_VERSION = "siard.version";
	
	private static final String PROPERTY_KEY_SIARD_EXTENSIONS = "siard.extensions";
	
	private LogService logService;
	
	private ZipService zipService;
	
	private FileSystem fileSystem;
	
	private XmlService xmlService;
	
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
	
	protected void releaseLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void setXmlService(XmlService service)
	{
		this.xmlService = service;
	}
	
	protected void releaseXmlService(XmlService service)
	{
		this.xmlService = null;
	}
	
	protected void setZipService(ZipService service)
	{
		this.zipService = service;
	}
	
	protected void releaseZipService(ZipService service)
	{
		this.zipService = null;
	}
	
	protected void setFileSystem(FileSystem fileSystem)
	{
		this.fileSystem = fileSystem;
	}
	
	protected void releaseFileSystem(FileSystem fileSystem)
	{
		this.fileSystem = null;
	}
	
	protected void setChecksumService(ChecksumService service)
	{
		this.checksumService = service;
	}
	
	protected void releaseChecksumService(ChecksumService service)
	{
		this.checksumService = null;
	}
	
//	@Override
//	public boolean validateHeaderMetadataXsd(File file) throws IOException
//	{
//		InputStream in = this.getClass().getResourceAsStream("/META-INF/metadata.xsd");
//		File internal = fileSystem.cache(in);
//		in.close();
//		in = this.getClass().getResourceAsStream("/META-INF/metadata.xsd");
//		File external = fileSystem.cache(in);
//		in.close();
//		return checksumService.compare(internal, external);
//	}
	
//	@Override
//	public IStatus checkDirectoryStructure(File file)
//	{
//		IStatus status = Status.OK_STATUS;
//		try
//		{	
//			String[] directories = zipService.getDirectories(file);
//			status.addMessage(DirectoryStructureValidationMessage.FILE_FORMAT.setValid(true));
//			checkDirectoryStructure(status, directories);
//		} 
//		catch (IOException e)
//		{
//			status.addMessage(DirectoryStructureValidationMessage.FILE_FORMAT.setThrowable(e));
//			return status;
//		}
//		return status;
//	}
//
//	private IStatus checkDirectoryStructure(IStatus status, String[] directories)
//	{
//		Arrays.sort(directories);
//		for (String directory : directories)
//		{
//			String path[] = directory.split("/");
//			if (path.length == 1)
//			{
//				/*
//				 * top level element
//				 * 
//				 * There are only two top level elements allowed:
//				 * 
//				 * header/
//				 * content/
//				 * 
//				 * both are directories
//				 */
//				checkRoot(status, directory);
//			}
//			else if (directory.startsWith("header/"))
//			{
//				status.addMessage(DirectoryStructureValidationMessage.NESTED_HEADER_DIRECTORY.setValid(false));
//			}
//			else if (directory.startsWith("content/"))
//			{
//				status = checkContent(status, directory);
//			}
//		}
//		return status;
//	}
//	
//	private IStatus checkRoot(IStatus status, String entry)
//	{
//		if (entry.equals("header/"))
//		{
//			status.addMessage(DirectoryStructureValidationMessage.HEADER.setValid(true));
//		}
//		else if (entry.equals("content/"))
//		{
//			status.addMessage(DirectoryStructureValidationMessage.CONTENT.setValid(true));
//		}
//		else
//		{
//			status.addMessage(DirectoryStructureValidationMessage.TOP_LEVEL_ENTRY.setValid(false));
//		}
//		return status;
//	}
//
//	private IStatus checkContent(IStatus< status, String entry)
//	{
//		if (entry.startsWith("content/schema"))
//		{
//			String[] elements = entry.split("/");
//			if (elements.length == 2)
//			{
//				status = checkSchema(status, elements[1]);
//			}
//			else if (elements.length == 3)
//			{
//				status = checkTable(status, elements[2]);
//			}
//			else if (elements.length == 4)
//			{
//				status = checkLob(status, elements[3]);
//			}
//			else
//			{
//				status.addMessage(DirectoryStructureValidationMessage.CONTENT.setValid(false));
//			}
//		}
//		else
//		{
//			status.addMessage(DirectoryStructureValidationMessage.SCHEMA.setValid(false));
//		}
//		return status;
//	}
//	
//	private IStatus checkSchema(IStatus status, String entry)
//	{
//		String schema = entry.replace("schema", "");
//		try
//		{
//			Integer.valueOf(schema);
//			status.addMessage(DirectoryStructureValidationMessage.SCHEMA.setValid(true));
//		}
//		catch (NumberFormatException e)
//		{
//			status.addMessage(DirectoryStructureValidationMessage.SCHEMA.setThrowable(e));
//		}
//		return status;
//	}
//	
//	private IStatus checkTable(IStatus status, String entry)
//	{
//		String table = entry.replace("table", "");
//		try
//		{
//			Integer.valueOf(table);
//			status.addMessage(DirectoryStructureValidationMessage.TABLE.setValid(true));
//		}
//		catch (NumberFormatException e)
//		{
//			status.addMessage(DirectoryStructureValidationMessage.TABLE.setThrowable(e));
//		}
//		return status;
//	}
//	
//	private IStatus checkLob(IStatus status, String entry)
//	{
//		String lob = entry.replace("lob", "");
//		try
//		{
//			Integer.valueOf(lob);
//			status.addMessage(DirectoryStructureValidationMessage.LOB.setValid(true));
//		}
//		catch (NumberFormatException e)
//		{
//			status.addMessage(DirectoryStructureValidationMessage.LOB.setThrowable(e));
//		}
//		return status;
//	}
//
//	@Override
//	public IStatus checkVersion(File file)
//	{
//		IStatus status = Status.OK_STATUS;
//		try
//		{
//			InputStream entry = zipService.getEntry(file, "header/metadata.xml");
//			String version = xmlService.getSiardVersion(entry);
//		} 
//		catch (IOException e)
//		{
//			status.addMessage(VersionCheckMessage.VERSION.setThrowable(e));
//		}
//		catch (JDOMException e)
//		{
//			status.addMessage(VersionCheckMessage.VERSION.setThrowable(e));
//		}
//		return null;
//	}
//	
//	private Document getDocument(File file, boolean validate) throws IOException, JDOMException
//	{
//		Document document = null;
//		InputStream xml = zipService.getInputStream(file, "header/metadata.xml");
//		InputStream in = zipService.getInputStream(file, "header/metadata.xsd");
////		InputStream in = this.getClass().getResourceAsStream("/META-INF/metadata.xsd");
//		File tmp = File.createTempFile("tmp_", ".xsd");
//		OutputStream out = new FileOutputStream(tmp);
//		while (in.available() > 0)
//		{
//			byte[] b = new byte[in.available()];
//			int len = in.read(b);
//			out.write(b, 0, len);
//		}
//		out.close();
//		in.close();
//
//		if (validate)
//		{
//			XMLReaderXSDFactory xsdFactory = new XMLReaderXSDFactory(tmp);
//			saxBuilder = new SAXBuilder(xsdFactory);
//			saxBuilder.setFeature("http://apache.org/xml/features/validation/schema", true);
//		}
//		else
//		{
//			saxBuilder = new SAXBuilder();
//		}
//		document = saxBuilder.build(xml);
//		System.out.println(document);
//		return document;
//	}
	
	@Override
	public Enumeration<? extends ZipEntry> listEntries(File file) throws IOException
	{
		return zipService.listEntries(file);
	}

//	
//	public IStatus validateContent(File file)
//	{
//		InputStream inputStream = zipService.getInputStream(file, "header/metadata.xml");
//		Document document = saxBuilder.build(inputStream);
//		List<Element> schemas = document.getRootElement().getChild("content").getChildren();
//		for (Element schema : schemas)
//		{
//			String entry = zipService.getEntry(File file, String name);
//			List<Element> tables = schema.getChildren();
//			for (Element table : tables)
//			{
//				
//			}
//		}
//	}
	
	@Override
	public IStatus checkExtension(File file)
	{
		IStatus status = Status.OK_STATUS;
		Object property = context.getProperties().get(PROPERTY_KEY_SIARD_EXTENSIONS);
		if (property instanceof String)
		{
			String extension = (String) property;
			if (file.getName().endsWith(extension))
			{
				status = new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Invalid file extension (only " + extension + " is allowed).");
			}
		}
		else if (property instanceof String[])
		{
			String[] extensions = (String[]) property;
			for (String extension : extensions)
			{
				if (file.getName().endsWith(extension)) 
				{
					return status;
				}
			}
			status = new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Invalid file extension (only " + property.toString() + " is allowed).");
		}
		return status;
	}

	@Override
	public String getVersion(File file) throws Exception
	{
		InputStream in = zipService.getEntryAsStream(file, "header/metadata.xml");
		return xmlService.getSiardVersion(in);
	}

	@Override
	public IStatus validateMetadataXsd(File file)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			File tmp = zipService.getEntryAsFile(file, "header/metadata.xsd");
			status = xmlService.validate(tmp);
			tmp.delete();
		} 
		catch (IOException e)
		{
			status = new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Error reading file " + file.getAbsolutePath() + ".");
			logService.log(LogService.LOG_ERROR, status.getMessage());
		}
		return status;
	}
}
