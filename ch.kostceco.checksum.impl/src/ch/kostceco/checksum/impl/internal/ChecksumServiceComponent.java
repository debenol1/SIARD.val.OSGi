package ch.kostceco.checksum.impl.internal;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.checksum.api.service.ChecksumService;

public class ChecksumServiceComponent implements ChecksumService
{
	private LogService logService;
	
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void clearLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void startup(ComponentContext context)
	{
		logService.log(LogService.LOG_INFO, "Service " + this.getClass().getName() + " started");
	}
	
	protected void shutdown(ComponentContext context)
	{
		logService.log(LogService.LOG_INFO, "Service " + this.getClass().getName() + " stopped");
	}

	@Override
	public boolean compare(File file1, File file2)
	{
		if (!file1.exists() || file1.isDirectory() || !file1.canRead())
		{
			return false;
		}
		if (!file2.exists() || file2.isDirectory() || !file2.canRead())
		{
			return false;
		}
		boolean comparison = false;
		try
		{
			AbstractChecksum checksum = JacksumAPI.getChecksumInstance("md5");
			checksum.readFile(file1.getAbsolutePath());
			byte[] b1 = checksum.getByteArray();
			System.out.println(b1);
			System.out.println("Long value: " + checksum.getValue());
			System.out.println("Formatted value: " + checksum.getFormattedValue());
			checksum.readFile(file2.getAbsolutePath());
			byte[] b2 = checksum.getByteArray();
			System.out.println(b2);
			System.out.println("Long value: " + checksum.getValue());
			System.out.println("Formatted value: " + checksum.getFormattedValue());
			comparison = Arrays.equals(b1, b2);
		} 
		catch (NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comparison;
	}

}
