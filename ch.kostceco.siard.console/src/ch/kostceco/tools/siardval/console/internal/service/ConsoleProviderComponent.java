package ch.kostceco.tools.siardval.console.internal.service;

import java.io.File;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

public class ConsoleProviderComponent implements CommandProvider 
{
	private LogService logService;
	
//	private ZipService zipService;
//	
//	private XmlService xmlService;
//	
//	private FileValidator fileValidator;
//	
//	private SiardService siardSpecificationService;
	
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void releaseLogService(LogService service)
	{
		this.logService = null;
	}
	
//	protected void setZipService(ZipService service)
//	{
//		this.zipService = service;
//	}
//	
//	protected void clearZipService(ZipService service)
//	{
//		this.zipService = null;
//	}
//	
//	protected void setXmlService(XmlService service)
//	{
//		this.xmlService = service;
//	}
//	
//	protected void clearXmlService(XmlService service)
//	{
//		this.xmlService = null;
//	}
//	
//	protected void setFileValidator(FileValidator validator)
//	{
//		this.fileValidator = validator;
//	}
//	
//	protected void clearFileValidator(FileValidator validator)
//	{
//		this.fileValidator = null;
//	}
//	
//	protected void setSiardSpecificationService(SiardService service)
//	{
//		this.siardSpecificationService = service;
//	}
//	
//	protected void clearSiardSpecificationService(SiardService service)
//	{
//		this.siardSpecificationService = null;
//	}
	
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
		logService.log(LogService.LOG_ERROR, "Validated");
	}
	
	@Override
	public String getHelp() 
	{
		return new StringBuilder("Usage: validate [options] [path]\n\n")
		.append("Options:\n").toString();
	}

}
