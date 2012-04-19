package ch.kostceco.tools.siardval.siard.impl.internal.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.service.component.ComponentContext;

import ch.kostceco.tools.siardval.siard.api.service.SiardService;

public class SiardConsoleComponent implements CommandProvider 
{
	private SiardService siardService;

	protected void addSiardService(SiardService service)
	{
		this.siardService = service;
	}
	
	protected void removeSiardService(SiardService service)
	{
		this.siardService = null;
	}
	
	protected void startup(ComponentContext context)
	{
		
	}
	
	protected void shutdown(ComponentContext context)
	{
		
	}
	
	public void _siard(CommandInterpreter commandInterpreter)
	{
		File file = null;
		List<String> args = new ArrayList<String>();
		String arg = commandInterpreter.nextArgument();
		while (arg instanceof String)
		{
			File f = new File(arg);
			if (f.exists())
			{
				file = f;
			}
			else
			{
				args.add(arg);
			}
			arg = commandInterpreter.nextArgument();
		}
		
		if (file == null)
		{
			commandInterpreter.println("File not found.");
			return;
		}
		
		for (String option : args)
		{
			if (option.equals("-l") || option.equals("--list"))
			{
				try
				{
					listEntries(file, commandInterpreter);
				} 
				catch (IOException e)
				{
					System.out.println("Error opening file " + file.getAbsolutePath() + ".");
				}
			}
			if (option.equals("-v") || option.equals("--version"))
			{
				printVersion(file, commandInterpreter);
			}
		}
		String path = "U:\\Incubator Projekte\\SIARD.val\\Documentation\\Northwind.siard";
//		String path = "/Volumes/NTFS/Incubator Projekte/SIARD.val/Documentation/Northwind.siard";
//		siardSpecificationService.checkStructure(path);
	}
	
	@Override
	public String getHelp() 
	{
		return new StringBuilder("Usage: siard [options] [path]\n\n")
		.append("-l\t--list\tlist file entries\n").toString();
	}

	private void listEntries(File file, CommandInterpreter ci) throws IOException
	{
		int max = 0;
		List<Entry> entryList = new ArrayList<Entry>();
		Enumeration<? extends ZipEntry> entries = siardService.listEntries(file);
		while (entries.hasMoreElements())
		{
			ZipEntry entry = entries.nextElement();
			if (entry.getName().length() > max)
			{
				max = entry.getName().length();
			}
			entryList.add(new Entry(entry.getName(), entry.getCompressedSize(), entry.getSize()));
		}

		Entry[] printList = entryList.toArray(new Entry[0]);
		Arrays.sort(printList);
		System.out.printf("%-" + max + "s %12s %12s", "Name", "Compr. Size", "Size");
		System.out.println();
		for (Entry entry : printList)
		{
    		System.out.printf("%-" + max + "s %12s %12s", entry.getName(), entry.getCompressedSize(), entry.getSize());
    		System.out.println();
		}
	}
	
	private void printVersion(File file, CommandInterpreter ci)
	{
		try
		{
			String version = siardService.getVersion(file);
	   		ci.println("Version of package " + file.getAbsolutePath() + ": " + version);
		} 
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				ci.println("Error opening file " + file.getAbsolutePath());
			}
			else
			{
				ci.println(e.getMessage());
			}
		}
	}
	
	private class Entry implements Comparable<Entry>
	{
		private final String name;
		private final long compressedSize;
		private final long size;
		
		public Entry(String name, long compressedSize, long size)
		{
			this.name = name;
			this.compressedSize = compressedSize;
			this.size = size;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public long getCompressedSize()
		{
			return this.compressedSize;
		}
		
		public long getSize()
		{
			return this.size;
		}

		@Override
		public int compareTo(Entry other)
		{
			return this.name.compareTo(other.getName());
		}
	}
}
