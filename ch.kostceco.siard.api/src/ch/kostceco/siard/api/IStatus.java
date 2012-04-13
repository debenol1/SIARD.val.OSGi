package ch.kostceco.siard.api;

import java.io.File;

public interface IStatus
{
	boolean isOK();
	
	void update(boolean ok);
	
	String getAction();
	
	String getMessage();
	
	public enum Action
	{
		CHECK_EXISTENCE, CHECK_DIRECTORY, CHECK_EXTENSION;
	
		private boolean ok = true;
		
		private File file;

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
		
		public String action()
		{
			String name = file == null ? "???" : file.getName();
			String parent = file == null ? "???" : file.getParent() == null ? "???" : file.getParent();
			switch(this)
			{
			case CHECK_EXISTENCE:
			{
				return "Check if file " + name + " in " + parent + " exists.";
			}
			case CHECK_DIRECTORY:
			{
				return "Check if file " + name + " in " + parent + " is directory.";
			}
			case CHECK_EXTENSION:
			{
				return "Check file extension of " + name + " in " + parent + ".";
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
			case CHECK_EXISTENCE:
			{
				return ok ? "OK" : "File not found.";
			}
			case CHECK_DIRECTORY:
			{
				return ok ? "OK" : "File is a directory.";
			}
			case CHECK_EXTENSION:
			{
				return ok ? "OK" : "Extension not valid.";
			}
			default:
			{
				return "";
			}
			}
		}
	}
}
