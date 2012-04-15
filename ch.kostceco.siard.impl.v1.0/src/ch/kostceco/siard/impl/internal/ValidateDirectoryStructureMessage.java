package ch.kostceco.siard.impl.internal;

import ch.kostceco.siard.api.IMessage;


public enum ValidateDirectoryStructureMessage implements IMessage
{
	HEADER, CONTENT;
	
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
		case HEADER:
		{
			return valid ? "OK" : "Directory header missing";
		}
		default:
		{
			throw new RuntimeException("Invalid validate directory structure message");
		}
		}
	}
}
