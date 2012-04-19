package ch.kostceco.tools.siardval.fs.impl.internal.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import ch.kostceco.tools.siardval.fs.api.service.FileSystem;

public class FileSystemComponent implements FileSystem
{
	private LogService logService;
	
	protected void setLogService(LogService service)
	{
		this.logService = service;
	}
	
	protected void releaseLogService(LogService service)
	{
		this.logService = null;
	}
	
	protected void startup(ComponentContext context)
	{
		logService.log(LogService.LOG_INFO, "Service " + this.getClass().getName() + " started");
	}
	
	protected void shutdown(ComponentContext context)
	{
		logService.log(LogService.LOG_INFO, "Service " + this.getClass().getName() + " stopped");
	}

	@Override
	public File cache(InputStream inputStream) throws IOException
	{
		File tmp = File.createTempFile("tmp_", ".siard");
		OutputStream out = new FileOutputStream(tmp);
		while (inputStream.available() > 0)
		{
			byte[] b = new byte[inputStream.available()];
			int len = inputStream.read(b);
			out.write(b, 0, len);
		}
		out.close();
		return tmp;
	}
}
