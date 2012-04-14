package ch.kostceco.filesystem.impl.internal;

import java.io.File;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.filesystem.api.IStatus;
import ch.kostceco.filesystem.api.IStatus.Action;
import ch.kostceco.filesystem.api.Status;
import ch.kostceco.filesystem.api.service.FileValidator;
import ch.kostceco.siard.api.service.SiardService;

public class FileValidatorComponent implements FileValidator 
{
	public static final String EXTENSION_DELIMITER = ".";

	private LogService logService;
	
	private SiardService siardService;
	
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void clearLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void setSiardService(SiardService service)
	{
		this.siardService = service;
	}
	
	protected void clearSiardService(SiardService service)
	{
		this.siardService = null;
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
		IStatus status = Status.instance(Action.CHECK_EXISTENCE, file, true);
		if (!file.exists())
		{
			status.update(false);
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

	@Override
	public IStatus checkExtension(File file, String[] extensions)
	{
		IStatus status = Status.instance(Action.CHECK_EXTENSION, file, false);
		for (String extension : extensions)
		{
			if (file.getName().endsWith(extension))
			{
				status.update(true);
			}
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

	@Override
	public IStatus checkDirectory(File file)
	{
		IStatus status = Status.instance(Action.CHECK_DIRECTORY, file, true);
		if (file.isDirectory())
		{
			status.update(false);
		}
		logService.log(status.isOK() ? LogService.LOG_INFO : LogService.LOG_ERROR, status.getAction() + ": " + status.getMessage());
		return status;
	}

	@Override
	public IStatus validate(File file)
	{
		IStatus status = checkFile(file);
		if (status.isOK())
		{
			status = checkDirectory(file);
			if (status.isOK())
			{
				status = checkExtension(file, siardService.getValidExtensions());
			}
		}
		return status;
	}
}
