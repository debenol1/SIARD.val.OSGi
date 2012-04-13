package ch.kostceco.filesystem.impl.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{
	private static BundleContext context;
	
	public static BundleContext getBundleContext()
	{
		return context;
	}
	
	@Override
	public void start(BundleContext context) throws Exception
	{
		Activator.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		Activator.context = null;
	}

}
