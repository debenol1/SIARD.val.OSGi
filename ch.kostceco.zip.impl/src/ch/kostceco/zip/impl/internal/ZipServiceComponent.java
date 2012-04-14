package ch.kostceco.zip.impl.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.zip.api.IStatus;
import ch.kostceco.zip.api.IStatus.Action;
import ch.kostceco.zip.api.Status;
import ch.kostceco.zip.api.service.ZipService;
import de.schlichtherle.truezip.zip.ZipEntry;
import de.schlichtherle.truezip.zip.ZipFile;

public class ZipServiceComponent implements ZipService
{
	private LogService logService;
	
	protected void setLogService(LogService logService)
	{
		this.logService = logService;
	}
	
	protected void clearLogService(LogService logService)
	{
		this.logService = null;
	}
	
	protected void startup(ComponentContext context)
	{
		this.logService.log(LogService.LOG_DEBUG, "Service " + this.getClass().getName() + " started.");
	}
	
	protected void shutdown(ComponentContext context)
	{
		this.logService.log(LogService.LOG_DEBUG, "Service " + this.getClass().getName() + " stopped.");
	}
	
	@Override
	public InputStream getInputStream(File file, String element) throws IOException
	{
		ZipFile zipFile = new ZipFile(file);
		ZipEntry entry = zipFile.getEntry(element);
		return zipFile.getInputStream(entry);
	}

	@Override
	public IStatus checkCompression(File file)
	{
		IStatus status = Status.instance(Action.CHECK_COMPRESSION, file, true);
		try
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				if (entry.getMethod() != ZipEntry.STORED)
				{
					//TODO collect all compressed entries?
					status.update(false);
				}
			}
			zipFile.close();
		}
		catch (IOException e)
		{
			status.update(e);
		}
		return status;
	}

	/**
	 * Checks if file is a valid zip file. This method equals to checkZip(file, Method.HEADER_CHECK)
	 * 
	 * @param file
	 * @return
	 */
	@Override
	public IStatus validateZip(File file)
	{
		return validateZip(file, ZipService.Method.HEADER_CHECK);
	}

	@Override
	public IStatus validateZip(File file, ZipService.Method method)
	{
		switch (method)
		{
		case HEADER_CHECK:
		{
			return checkHeader(file);
		}
		case FULL_CHECK:
		{
			fullCheck(file);
		}
		default:
		{
			return checkHeader(file);
		}
		}
	}

	private IStatus checkHeader(File file)
	{
		IStatus status = Status.instance(Action.VALIDATE_ZIP_HEADERS, file, true);
	    try 
	    {
	        ZipFile zipFile = new ZipFile(file);
	        zipFile.close();
	    } 
	    catch (ZipException e) 
	    {
	        status.update(e);
	    } 
	    catch (IOException e) 
	    {
	        status.update(e);
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
		IStatus status = Status.instance(Action.FULL_ZIP_VALIDATION, file, true);
	    try 
	    {
	        ZipFile zipFile = new ZipFile(file);
	        Enumeration<? extends ZipEntry> entries = zipFile.entries();
	        while (entries.hasMoreElements())
	        {
	        	ZipEntry entry = entries.nextElement();
	        	InputStream in = null;
	        	try
	        	{
	        		in = zipFile.getInputStream(entry);
		        	byte[] b = new byte[512];
		        	while (in.read(b) > -1)
		        	{
		        	}
	        	}
	        	finally
	        	{
	        		if (in != null)
	        		{
	    	        	in.close();
	        		}
	        	}
	        }
	        zipFile.close();
	    } 
	    catch (ZipException e) 
	    {
	        status.update(e);
	    } 
	    catch (IOException e) 
	    {
	        status.update(e);
	    } 
	    return status;
	}

	@Override
	public IStatus checkEncryption(File file)
	{
		IStatus status = Status.instance(Action.FULL_ZIP_VALIDATION, file, true);
		try
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				if (entry.isEncrypted())
				{
					//TODO collect all encrypted entries?
					status.update(false);
				}
			}
			zipFile.close();
		}
		catch (IOException e)
		{
			status.update(e);
		}
		return status;
	}

	@Override
	public IStatus checkDirectoryStructure(File file, String[] validEntries)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public enum Method
	{
		STORED, DEFLATED, BZIP2, UNKNOWN;
	}

}
