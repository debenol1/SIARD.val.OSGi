package ch.kostceco.siard.impl.internal;

import ch.kostceco.siard.api.IMessage;


public enum CheckVersionMessage implements IMessage
{
	METADATA, VERSION;
	
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
		case METADATA:
		{
			return valid ? "OK" : "Invalid metadata file format.";
		}
		case VERSION:
		{
			return valid ? "OK" : "Invalid version.";
		}
		default:
		{
			throw new RuntimeException("Invalid version message");
		}
		}
	}
}
