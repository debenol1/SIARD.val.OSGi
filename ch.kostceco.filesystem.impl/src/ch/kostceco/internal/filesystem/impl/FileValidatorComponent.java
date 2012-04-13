package ch.kostceco.internal.filesystem.impl;

import java.io.File;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.filesystem.IStatus;
import ch.kostceco.filesystem.IStatus.Action;
import ch.kostceco.filesystem.Status;
import ch.kostceco.filesystem.service.FileValidator;

public class FileValidatorComponent implements FileValidator 
{
	public static final String EXTENSION_DELIMITER = ".";

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
	public IStatus checkFile(File file)
	{
		IStatus status = Status.instance(Action.CHECK_EXISTENCE);
		if (!file.exists())
		{
			status.update(file, false);
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

	@Override
	public IStatus checkExtension(File file, String[] extensions)
	{
		IStatus status = Status.instance(Action.CHECK_EXTENSION);
		status.update(file, false);
		for (String extension : extensions)
		{
			if (file.getName().endsWith(extension))
			{
				status.update(file, true);
			}
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

	@Override
	public IStatus checkDirectory(File file)
	{
		IStatus status = Status.instance(Action.CHECK_DIRECTORY);
		if (file.isDirectory())
		{
			status.update(file, false);
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

}
