package ch.kostceco.siard.impl.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.siard.api.Action;
import ch.kostceco.siard.api.CheckVersionMessage;
import ch.kostceco.siard.api.IStatus;
import ch.kostceco.siard.api.ValidateDirectoryStructureMessage;
import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.siard.impl.internal.Activator;

public class SiardTestCase
{
	private ServiceTracker<SiardService, SiardService> tracker;

	private File file;

	private SiardService service;
	
	@Before
	public void setUp() throws Exception
	{
		String path = "U:/Incubator Projekte/SIARD.val/Test Data/gebaeudeversicherung.siard";
		file = new File(path);

		tracker = new ServiceTracker<SiardService, SiardService>(Activator.getContext(), SiardService.class, null);
		tracker.open();

		service = tracker.getService();
	}

	@After
	public void tearDown() throws Exception
	{
		service = null;
		tracker.close();
	}

	@Test
	public void testDirectoryStructure()
	{
		Assert.assertNotNull(service);

		IStatus<Action<ValidateDirectoryStructureMessage>, ValidateDirectoryStructureMessage> status = service.checkDirectoryStructure(file);
		Assert.assertTrue(status.isOK());
	}

	@Test
	public void testCheckVersion()
	{
		IStatus<Action<CheckVersionMessage>, CheckVersionMessage> status = service.checkVersion(file);
		Assert.assertTrue(status.isOK());
	}

}
