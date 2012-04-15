package ch.kostceco.siard.api.service;

import java.io.File;
import java.io.IOException;

import ch.kostceco.siard.api.Action;
import ch.kostceco.siard.api.CheckVersionMessage;
import ch.kostceco.siard.api.IStatus;
import ch.kostceco.siard.api.ValidateDirectoryStructureMessage;

public interface SiardService
{
	String getVersion();
	
	IStatus<Action<CheckVersionMessage>, CheckVersionMessage> checkVersion(File file);
	
	boolean validateHeaderMetadataXsd(File file) throws IOException;
	
	String[] getValidExtensions();
	
	IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> checkDirectoryStructure(File file);
}
