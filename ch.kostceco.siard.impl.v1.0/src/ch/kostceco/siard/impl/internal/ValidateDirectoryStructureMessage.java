package ch.kostceco.siard.impl.internal;

import ch.kostceco.siard.api.IMessage;


public enum ValidateDirectoryStructureMessage implements IMessage
{
	FILE_FORMAT, HEADER, CONTENT, TOP_LEVEL_ENTRY, NESTED_HEADER_DIRECTORY, SCHEMA, TABLE, LOB;
	
	private boolean valid;
	
	@Override
	public void setValid(boolean valid)
	{
		this.valid = valid;
	}
	
	@Override
	public boolean isValid()
	{
		return valid;
	}
	
	public String message()
	{
		switch (this)
		{
		case FILE_FORMAT:
		{
			return valid ? "OK" : "Invalid file format.";
		}
		case HEADER:
		{
			return valid ? "OK" : "Directory \"header\" missing.";
		}
		case CONTENT:
		{
			return valid ? "OK" : "Directory \"content\" missing.";
		}
		case TOP_LEVEL_ENTRY:
		{
			return valid ? "OK" : "Invalid top level entry.";
		}
		case NESTED_HEADER_DIRECTORY:
		{
			return valid ? "OK" : "Header directory contains nested directory.";
		}
		case SCHEMA:
		{
			return valid ? "OK" : "Content directory contains invalid entry.";
		}
		case TABLE:
		{
			return valid ? "OK" : "Schema directory contains invalid entry.";
		}
		case LOB:
		{
			return valid ? "OK" : "Table directory contains invalid entry.";
		}
		default:
		{
			throw new RuntimeException("Invalid validate directory structure message");
		}
		}
	}
}
