package ch.kostceco.siard.api.service;

public interface SiardService
{
	String getVersion();
	
//	IStatus checkDirectoryStructure(String path);

	String[] getValidExtensions();
}
