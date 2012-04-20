package ch.kostceco.tools.siardval.service.impl.b.internal;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.siardval.service.api.SiardValidator;
import ch.kostceco.tools.siardval.siard.api.service.SiardService;

public class StepBValidatorComponent implements SiardValidator
{
	private ComponentContext componentContext;
	
	private LogService logService;
	
	private SiardService siardService;
		
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void releaseLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void setSiardService(SiardService service)
	{
		this.siardService = service;
	}

	protected void releaseSiardService(SiardService service)
	{
		this.siardService = null;
	}

	protected void startup(ComponentContext componentContext)
	{
		this.componentContext = componentContext;
	}
	
	protected void shutdown(ComponentContext componentContext)
	{
		this.componentContext = null;
	}
	
	@Override
	public IStatus validate(File file)
	{
		IStatus status = siardService.checkVersion(file);
		if (status.isOK())
		{
			status = siardService.validateMetadataXsd(file);
			if (!status.isOK())
			{
				status = siardService.validatesMetadataXsdAgainstInternalMetadataXsd(file);
			}
			if (status.isOK())
			{
				status = siardService.validateMetadataXmlAgainstMetadataXsd(file);
			}
		} 
		return status;
	}

}
