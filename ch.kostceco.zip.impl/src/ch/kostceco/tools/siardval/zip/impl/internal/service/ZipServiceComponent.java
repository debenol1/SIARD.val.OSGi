package ch.kostceco.tools.siardval.zip.impl.internal.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.siardval.zip.api.service.ZipService;
import ch.kostceco.tools.siardval.zip.impl.internal.Activator;
import de.schlichtherle.truezip.zip.ZipEntry;
import de.schlichtherle.truezip.zip.ZipFile;

public class ZipServiceComponent implements ZipService
{
	private LogService logService;
	
	protected void setLogService(LogService logService)
	{
		this.logService = logService;
	}
	
	protected void releaseLogService(LogService logService)
	{
		this.logService = null;
	}
	
	protected void startup(ComponentContext context)
	{
	}
	
	protected void shutdown(ComponentContext context)
	{
	}
	
	@Override
	public InputStream getEntryAsStream(File file, String path) throws IOException
	{
		ZipFile zipFile = new ZipFile(file);
		ZipEntry entry = zipFile.getEntry(path);
		return zipFile.getInputStream(entry);
	}

	@Override
	public URL getEntryAsUrl(File file, String path) throws IOException
	{
		return getEntryAsFile(file, path).toURI().toURL();
	}

	@Override
	public IStatus checkCompression(File file)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				if (entry.getMethod() != ZipEntry.STORED)
				{
					status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Die Datei " + file.getName() + " im Verzeichnis " + file.getParent() + " enthält komprimierte Daten.");
					break;
				}
			}
			zipFile.close();
		}
		catch (IOException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Die Datei " + file.getName() + " im Verzeichnis " + file.getParent() + " enthält komprimierte Daten.");
		}
		log(status.getSeverity() == IStatus.OK ? LogService.LOG_INFO : LogService.LOG_ERROR, "Kompressionstest: " + status.getMessage());
		return status;
	}

	private void log(int level, String message)
	{
		logService.log(level, message);
	}
	/**
	 * Checks if file is a valid zip file. This method equals to checkZip(file, Method.HEADER_CHECK)
	 * 
	 * @param file
	 * @return
	 */
	@Override
	public IStatus checkIntegrity(File file)
	{
		return checkIntegrity(file, ZipService.Method.FULL_CHECK);
	}

	@Override
	public IStatus checkIntegrity(File file, ZipService.Method method)
	{
		IStatus status = Status.OK_STATUS;
		switch (method)
		{
		case HEADER_CHECK:
		{
			status = checkHeader(file);
		}
		case FULL_CHECK:
		{
			status = fullCheck(file);
		}
		default:
		{
			status = fullCheck(file);
		}
		}
		log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, "Integritätstest (" + method.toString() + "):" + status.getMessage());
		return status;
	}

	private IStatus checkHeader(File file)
	{
		IStatus status = Status.OK_STATUS;
	    try 
	    {
	        ZipFile zipFile = new ZipFile(file);
	        zipFile.close();
	    } 
	    catch (ZipException e) 
	    {
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Beim Lesen der Datei " + file.getName() + " in " + file.getParent() + " ist ein Fehler aufgetreten.", e);
	    } 
	    catch (IOException e) 
	    {
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Beim Öffnen der Datei " + file.getName() + " in " + file.getParent() + " ist ein Fehler aufgetreten.", e);
	    }
	    return status;
	}

	/**
	 * Perform a full check of entries. Open each entry and read whole stream in
	 * 
	 * @param file
	 * @return
	 */
	private IStatus fullCheck(File file)
	{
		IStatus status = Status.OK_STATUS;
	    try 
	    {
			logService.log(LogService.LOG_INFO, "Check integrity of file " + file.getName() + ".");
	        ZipFile zipFile = new ZipFile(file);
	        Enumeration<? extends ZipEntry> entries = zipFile.entries();
	        while (entries.hasMoreElements())
	        {
	        	ZipEntry entry = entries.nextElement();
	        	InputStream in = null;
        		if (entry.isDirectory())
        		{
        			logService.log(LogService.LOG_INFO, "Entry " + entry.getName() + " is directory.");
        		}
        		else
        		{
	        		int size = 0;
    	        	try
    	        	{
		        		in = zipFile.getInputStream(entry);
			        	while (in.available() > 0)
			        	{
			        		byte[] b = new byte[in.available()];
			        		size += in.read(b, 0, in.available());
			        	}
	        		}
    	        	finally
    	        	{
    	        		if (in != null)
    	        		{
    	        			logService.log(LogService.LOG_INFO, "Entry " + entry.getName() + " read successfully having " + size + " bytes.");
    	    	        	in.close();
    	        		}
    	        	}
	        	}
	        }
	        zipFile.close();
	    } 
	    catch (ZipException e) 
	    {
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Beim Lesen der Datei " + file.getName() + " in " + file.getParent() + " ist ein Fehler aufgetreten.", e);
			logService.log(LogService.LOG_ERROR, "Error reading file " + file.getName() + " for integrity check.");
	    } 
	    catch (IOException e) 
	    {
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Beim Öffnen der Datei " + file.getName() + " in " + file.getParent() + " ist ein Fehler aufgetreten.", e);
			logService.log(LogService.LOG_ERROR, "Error opening file " + file.getName() + " for integrity check.");
	    } 
	    return status;
	}

	@Override
	public IStatus checkEncryption(File file)
	{
		IStatus status = Status.OK_STATUS;
		try
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				if (entry.isEncrypted())
				{
					status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Die Datei " + file.getName() + " in " + file.getParent() + " ist verschlüsselt.");
				}
			}
			zipFile.close();
		}
		catch (IOException e)
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Beim Öffnen der Datei " + file.getName() + " in " + file.getParent() + " ist ein Fehler aufgetreten.", e);
		}
		log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, "Verschlüsselungstest: " + status.getMessage());
		return status;
	}

	@Override
	public File getEntryAsFile(File file, String path) throws IOException
	{
		InputStream in = this.getEntryAsStream(file, path);
		File tmp = File.createTempFile("tmp_", ".siard");
		OutputStream out = new FileOutputStream(tmp);
		try
		{
			byte[] b = new byte[in.available()];
			while (in.available() > 0)
			{
				int size = in.read(b, 0, in.available());
				out.write(b, 0, size);
			}
		}
		finally
		{
			out.close();
			in.close();
		}
		return file;
	}

//	@Override
//	public IStatus checkDirectoryStructure(File file, String[] validEntries)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Enumeration<? extends java.util.zip.ZipEntry> listEntries(File file) throws IOException
	{
		java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);
		Enumeration<? extends java.util.zip.ZipEntry> entries = zipFile.entries();
		return entries;
	}

//	@Override
//	public String[] getDirectories(File file) throws IOException
//	{
//		Collection<String> directories = new ArrayList<String>();
//		ZipFile zipFile = new ZipFile(file);
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		while (entries.hasMoreElements())
//		{
//			String name = entries.nextElement().getName();
//			if (name.endsWith("/"))
//			{
//				directories.add(name);
//			}
//		}
//		return directories.toArray(new String[0]);
//	}
//
//	@Override
//	public List<String> getChildren(File file, String parent) throws IOException
//	{
//		List<String> children = new ArrayList<String>();
//		ZipFile zipFile = new ZipFile(file);
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		while (entries.hasMoreElements())
//		{
//			ZipEntry entry = entries.nextElement();
//			if (entry.getName().startsWith(parent)) 
//			{
//				
//			}
//		}
//		return children;
//	}
//
//	@Override
//	public List<String> getDescendants(File file, String parent) throws IOException
//	{
//		List<String> descendants = new ArrayList<String>();
//		ZipFile zipFile = new ZipFile(file);
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		while (entries.hasMoreElements())
//		{
//			ZipEntry entry = entries.nextElement();
//			if (entry.getName().startsWith(parent) && entry.getName().length() > parent.length()) 
//			{
//				descendants.add(entry.getName());
//			}
//		}
//		return descendants;
//	}

	public enum Method
	{
		STORED, DEFLATED, BZIP2, UNKNOWN;
	}

}
