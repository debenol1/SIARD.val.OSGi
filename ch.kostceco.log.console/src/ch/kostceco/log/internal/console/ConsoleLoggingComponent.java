package ch.kostceco.log.internal.console;

import org.osgi.service.component.ComponentContext;

import ch.kostceco.log.service.LogService;

public class ConsoleLoggingComponent implements LogService
{
	protected void startup(ComponentContext context)
	{
	}

	protected void shutdown(ComponentContext context)
	{
	}

	@Override
	public void log(LogService.Status status, String message)
	{
		//TODO
		System.out.println(message);
	}

}
