package ch.kostceco.tools.siardval.service.impl.a.internal;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.siardval.service.api.SiardValidator;
import ch.kostceco.tools.siardval.zip.api.service.ZipService;

public class StepAValidatorComponent implements SiardValidator
{
	private LogService logService;
	
	private ZipService zipService;
		
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void releaseLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void setZipService(ZipService service)
	{
		this.zipService = service;
	}

	protected void releaseZipService(ZipService service)
	{
		this.zipService = null;
	}

	protected void startup(ComponentContext componentContext)
	{
		
	}
	
	protected void shutdown(ComponentContext componentContext)
	{
		
	}
	
	@Override
	public IStatus validate(File file)
	{
		IStatus status = zipService.checkIntegrity(file);
		if (status.isOK())
		{
			status = zipService.checkEncryption(file);
			if (status.isOK())
			{
				status = zipService.checkCompression(file);
			}
		}
		return status;
	}

}
