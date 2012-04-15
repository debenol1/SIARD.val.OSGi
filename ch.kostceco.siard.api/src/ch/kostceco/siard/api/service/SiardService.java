package ch.kostceco.siard.api.service;

import java.io.File;
import java.io.IOException;

import ch.kostceco.siard.api.IAction;
import ch.kostceco.siard.api.IMessage;
import ch.kostceco.siard.api.IStatus;

public interface SiardService
{
	String getVersion();
	
	boolean validateHeaderMetadataXsd(File file) throws IOException;
	
	String[] getValidExtensions();
	
	IStatus<? extends IAction<? extends IMessage>, ? extends IMessage> checkDirectoryStructure(File file);
}
