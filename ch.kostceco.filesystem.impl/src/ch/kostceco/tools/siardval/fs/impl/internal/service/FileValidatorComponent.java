package ch.kostceco.tools.siardval.fs.impl.internal.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.siardval.fs.api.service.FileValidator;
import ch.kostceco.tools.siardval.fs.impl.internal.Activator;

public class FileValidatorComponent implements FileValidator 
{
	public static final String EXTENSION_DELIMITER = ".";

	private LogService logService;
	
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void releaseLogService(LogService service)
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
	public IStatus checkReadable(File file)
	{
		IStatus status = Status.OK_STATUS;
		if (file.canRead())
		{
			logService.log(LogService.LOG_INFO, "Die Datei " + file.getName() + " im Verzeichnis " + file.getParent() + " kann gelesen werden.");
		}
		else
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Die Datei " + file.getName() + " im Verzeichnis " + file.getParent() + " kann nicht gelesen werden.");
			logService.log(LogService.LOG_ERROR, status.getMessage(), status.getException());
		}
		return status;
	}

	@Override
	public IStatus checkDirectory(File file)
	{
		IStatus status = Status.OK_STATUS;
		if (file.isDirectory())
		{
			status = new Status(IStatus.ERROR, Activator.getBundleContext().getBundle().getSymbolicName(), "Die Datei " + file.getName() + " im Verzeichnis " + file.getParent() + "ist nicht vorhanden.", new FileNotFoundException("File not found."));
			logService.log(LogService.LOG_ERROR, status.getMessage());
		}
		else
		{
			logService.log(LogService.LOG_INFO, "Die Datei " + file.getName() + " ist kein Verzeichnis.");
		}
		return status;
	}

	@Override
	public IStatus validate(File file)
	{
		IStatus status = checkReadable(file);
		if (status.isOK())
		{
			status = checkDirectory(file);
		}
		return status;
	}
}
