package ch.kostceco.siard.impl.internal;

import ch.kostceco.siard.api.IMessage;


public enum ValidateDirectoryStructureMessage implements IMessage
{
	FILE_FORMAT, HEADER, CONTENT;
	
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
			return valid ? "OK" : "Invalid file format";
		}
		case HEADER:
		{
			return valid ? "OK" : "Directory \"header\" missing";
		}
		case CONTENT:
		{
			return valid ? "OK" : "Directory \"content\" missing";
		}
		default:
		{
			throw new RuntimeException("Invalid validate directory structure message");
		}
		}
	}
}
