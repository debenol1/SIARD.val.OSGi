package ch.kostceco.siard.impl.internal;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.service.component.ComponentContext;

import ch.kostceco.siard.api.service.SiardService;

public class ListStructureProvider implements CommandProvider 
{
	private SiardService siardService;

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
		
	}
	
	protected void shutdown(ComponentContext context)
	{
		
	}
	
	public void _structure(CommandInterpreter commandInterpreter)
	{
		String path = "U:\\Incubator Projekte\\SIARD.val\\Documentation\\Northwind.siard";
//		String path = "/Volumes/NTFS/Incubator Projekte/SIARD.val/Documentation/Northwind.siard";
//		siardSpecificationService.checkStructure(path);
	}
	
	@Override
	public String getHelp() 
	{
		return new StringBuilder("Usage: structure [options] [path]\n\n")
		.append("          list structure\n\n").toString();
	}

}
