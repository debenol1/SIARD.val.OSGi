package ch.kostceco.siard.api.service;

import java.io.File;
import java.io.IOException;

public interface SiardService
{
	String getVersion();
	
	boolean validateHeaderMetadataXsd(File file) throws IOException;
	
	String[] getValidExtensions();
}
