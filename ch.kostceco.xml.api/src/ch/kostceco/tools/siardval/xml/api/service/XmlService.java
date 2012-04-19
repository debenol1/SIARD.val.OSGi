package ch.kostceco.tools.siardval.xml.api.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;

public interface XmlService
{
	IStatus validate(InputStream xml, File schema);
	
	IStatus validate(InputStream xml, URL schema);
	
	IStatus validate(URL schema);
	
	IStatus validate(File schema);
	
	String getSiardVersion(InputStream in) throws IOException;
}
