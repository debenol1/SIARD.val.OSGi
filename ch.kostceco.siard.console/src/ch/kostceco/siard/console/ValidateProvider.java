package ch.kostceco.siard.console;

import java.io.File;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.service.component.ComponentContext;

import ch.kostceco.filesystem.api.service.FileValidator;
import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.zip.api.service.ZipService;

public class ValidateProvider implements CommandProvider 
{
	private ZipService zipService;
	
	private FileValidator fileValidator;
	
	private SiardService siardSpecificationService;
	
	protected void setZipService(ZipService service)
	{
		this.zipService = service;
	}
	
	protected void clearZipService(ZipService service)
	{
		this.zipService = null;
	}
	
	protected void setFileValidator(FileValidator validator)
	{
		this.fileValidator = validator;
	}
	
	protected void clearFileValidator(FileValidator validator)
	{
		this.fileValidator = null;
	}
	
	protected void setSiardSpecificationService(SiardService service)
	{
		this.siardSpecificationService = service;
	}
	
	protected void clearSiardSpecificationService(SiardService service)
	{
		this.siardSpecificationService = null;
	}
	
	protected void startup(ComponentContext context)
	{
		
	}
	
	protected void shutdown(ComponentContext context)
	{
		
	}
	
	public void _validate(CommandInterpreter commandInterpreter)
	{
		String	path = commandInterpreter.nextArgument();
		if (path == null)
		{
			commandInterpreter.print("Usage: validate <path>\n");
			return;
		}
		File file = new File(path);
		fileValidator.checkExtension(file, siardSpecificationService.getValidExtensions());
	}
	
	@Override
	public String getHelp() 
	{
		return new StringBuilder("Usage: validate [options] [path]\n\n")
		.append("Options:\n").toString();
	}

}
