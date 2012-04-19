package ch.kostceco.tools.siardval.xml.impl.internal.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.osgi.service.component.ComponentContext;
import org.xml.sax.SAXException;

import ch.kostceco.tools.siardval.xml.api.service.XmlService;
import ch.kostceco.tools.siardval.xml.impl.internal.Activator;

public class XmlServiceComponent implements XmlService
{
	protected void startup(ComponentContext context)
	{
		
	}
	
	protected void shutdown(ComponentContext context)
	{
		
	}
	
	@Override
	public IStatus validate(InputStream in, File schemaLocation)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(in);
			validator.validate(source);
		} 
		catch (SAXException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Validierungsfehler", e);
		}
		catch (IOException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Validierungsfehler", e);
		}
		return status;
	}

	@Override
	public IStatus validate(InputStream in, URL schemaLocation)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(in);
			validator.validate(source);
		} 
		catch (SAXException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Validierungsfehler", e);
		}
		catch (IOException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Validierungsfehler", e);
		}
		return status;
	}

	@Override
	public IStatus validate(URL schemaLocation)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			factory.newSchema(schemaLocation);
		} 
		catch (SAXException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Validierungsfehler", e);
		}
		return status;
	}

	@Override
	public String getSiardVersion(InputStream in) throws IOException
	{
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document document = builder.build(in);
			String version = document.getRootElement().getAttributeValue("version");
			return version;
		} 
		catch (JDOMException e)
		{
			throw new RuntimeException("Invalid xml document.");
		}
	}
}
