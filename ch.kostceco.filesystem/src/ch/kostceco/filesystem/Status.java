package ch.kostceco.filesystem;

import java.io.File;

public class Status implements IStatus
{
	private final Action action;
	
	public Status(Action action)
	{
		this.action = action;
	}
	
	public static Status instance(Action action)
	{
		return new Status(action);
	}
	
	@Override
	public boolean isOK()
	{
		return action.ok();
	}

	@Override
	public void update(File target, boolean ok)
	{
		this.action.ok(ok);
		this.action.file(target);
	}

	@Override
	public String getMessage()
	{
		return action.message();
	}

	@Override
	public String getAction()
	{
		return action.action();
	}
}
