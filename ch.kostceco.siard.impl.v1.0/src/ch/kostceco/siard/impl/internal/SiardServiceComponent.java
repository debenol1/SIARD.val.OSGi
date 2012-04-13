package ch.kostceco.siard.impl.internal;

import org.osgi.service.component.ComponentContext;

import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.zip.api.service.ZipService;

public class SiardServiceComponent implements SiardService
{
	private static final String PROPERTY_KEY_SIARD_VERSION = "siard.version";
	
	private static final String PROPERTY_KEY_SIARD_EXTENSIONS = "siard.extensions";
	
	private ZipService zipService;
	
	private ComponentContext context;
	
	protected void startup(ComponentContext context)
	{
		this.context = context;
	}
	
	protected void shutdown(ComponentContext context)
	{
		this.context = null;
	}

	protected void setZipService(ZipService service)
	{
		this.zipService = service;
	}
	
	protected void clearZipService(ZipService service)
	{
		this.zipService = null;
	}
	
	@Override
	public String getVersion()
	{
		return (String) context.getProperties().get(PROPERTY_KEY_SIARD_VERSION);
	}
	
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
	
}
