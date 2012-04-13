package ch.kostceco.zip.api;

import java.io.File;

public class Status implements IStatus
{
	private final Action action;
	
	public Status(Action action, File file, boolean ok)
	{
		this.action = action;
		this.action.file(file);
		this.action.ok(ok);
	}
	
	public static Status instance(Action action, File file, boolean ok)
	{
		return new Status(action, file, ok);
	}
	
	@Override
	public boolean isOK()
	{
		return action.ok();
	}

	@Override
	public void update(boolean ok)
	{
		this.action.ok(ok);
	}

	@Override
	public void update(Throwable throwable)
	{
		this.action.ok(throwable == null ? true : false);
		this.action.throwable(throwable);
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

	@Override
	public Throwable getThrowable()
	{
		return action.throwable();
	}
}
