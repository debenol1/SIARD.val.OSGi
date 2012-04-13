package ch.kostceco.zip.api;

import java.io.File;

public interface IStatus
{
	boolean isOK();
	
	void update(boolean ok);
	
	void update(Throwable throwable);
	
	String getAction();
	
	String getMessage();
	
	Throwable getThrowable();
	
	public enum Action
	{
		VALIDATE_ZIP_HEADERS, FULL_ZIP_VALIDATION, CHECK_ENCRYPTION, CHECK_COMPRESSION;
	
		private boolean ok = true;
		
		private File file;

		private Throwable throwable;
		
		public void ok(boolean ok)
		{
			this.ok = ok;
		}
		
		public boolean ok()
		{
			return ok;
		}
		
		public void file(File file)
		{
			this.file = file;
		}
		
		public void throwable(Throwable throwable)
		{
			this.throwable = throwable;
		}
		
		public Throwable throwable()
		{
			return throwable;
		}
		
		public String action()
		{
			String name = file == null ? "???" : file.getName();
			String parent = file == null ? "???" : file.getParent() == null ? "???" : file.getParent();
			switch(this)
			{
			case VALIDATE_ZIP_HEADERS:
			{
				return "Verify if file " + name + " in " + parent + " has valid format (check entries without stream validation).";
			}
			case FULL_ZIP_VALIDATION:
			{
				return "Verify if file " + name + " in " + parent + " has valid format (deep validation, may take a long time).";
			}
			case CHECK_ENCRYPTION:
			{
				return "Check if file " + name + " in " + parent + " is encrypted.";
			}
			case CHECK_COMPRESSION:
			{
				return "Check if file " + name + " in " + parent + " is compressed.";
			}
			default:
			{
				return "";
			}
			}
		}

		public String message()
		{
			switch(this)
			{
			case VALIDATE_ZIP_HEADERS:
			{
				return ok ? "OK" : "Not valid zip file.";
			}
			case FULL_ZIP_VALIDATION:
			{
				return ok ? "OK" : "Not valid zip file.";
			}
			case CHECK_ENCRYPTION:
			{
				return ok ? "OK" : "File is encrypted.";
			}
			case CHECK_COMPRESSION:
			{
				return ok ? "OK" : "File is compressed.";
			}
			default:
			{
				return "";
			}
			}
		}
	}
}
