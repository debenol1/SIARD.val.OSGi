package ch.kostceco.siard.impl.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.siard.api.IAction;
import ch.kostceco.siard.api.IMessage;
import ch.kostceco.siard.api.IStatus;
import ch.kostceco.siard.api.service.SiardService;
import ch.kostceco.siard.impl.internal.Activator;

public class SiardTestCase
{
	private ServiceTracker<SiardService, SiardService> tracker;
	
	@Before
	public void setUp() throws Exception
	{
		tracker = new ServiceTracker<SiardService, SiardService>(Activator.getContext(), SiardService.class, null);
		tracker.open();
	}

	@After
	public void tearDown() throws Exception
	{
		tracker.close();
	}

	@Test
	public void testValidateHeaderMetadataXsd()
	{
		SiardService service = tracker.getService();
		Assert.assertNotNull(service);

		String path = "U:/Incubator Projekte/SIARD.val/Test Data/gebaeudeversicherung.siard";
		File file = new File(path);

		IStatus<? extends IAction<? extends IMessage>, ? extends IMessage> status = service.checkDirectoryStructure(file);
		Assert.assertTrue(status.isOK());

		try
		{
			Assert.assertTrue(service.validateHeaderMetadataXsd(file));
		} catch (IOException e)
		{
			Assert.assertFalse(false);
		}

	}

}
