package ch.kostceco.tools.siardval.service.api;

import java.io.File;

import org.eclipse.core.runtime.IStatus;

public interface SiardValidator
{
	IStatus validate(File file);
}
