package ch.kostceco.log.file.internal.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

public class FileLogComponent implements LogListener
{
	private LogReaderService logReaderService;
	
	private PrintStream out;
	
	protected void setLogReader(LogReaderService service)
	{
		this.logReaderService = service;
	}

	protected void releaseLogReader(LogReaderService service)
	{
		this.logReaderService = null;
	}

	protected void startup(ComponentContext context)
	{
		File file = new File("user.home".concat(File.separator.concat("SIARD.val")));
		if (!file.exists())
		{
			file.mkdirs();
		}
		try
		{
			File log = File.createTempFile("log", ".log", file);
			out = new PrintStream(new FileOutputStream(log));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void shutdown(ComponentContext context)
	{
		out.close();
	}

	@Override
	public void logged(LogEntry entry)
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(entry.getTime());
		String log = String.format("[%s %s] <%> %s", DateFormat.getDateTimeInstance().format(calendar), getLevelAsString(entry.getLevel()), entry.getBundle().getSymbolicName(), entry.getMessage());
		out.println(log);
	}
	
	private String getLevelAsString(int level)
	{
		switch(level)
		{
		case LogService.LOG_DEBUG:
		{
			return "DEBUG";
		}
		case LogService.LOG_INFO:
		{
			return "INFO";
		}
		case LogService.LOG_WARNING:
		{
			return "WARNING";
		}
		case LogService.LOG_ERROR:
		{
			return "ERROR";
		}
		default:
		{
			return "UNKNOWN";
		}
		}
	}

}
