package ch.kostceco.xml.api.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IStatus;

public interface XmlService
{
	IStatus validate(InputStream in, File schema);
	
	String getSiardVersion(InputStream in) throws IOException;
}
