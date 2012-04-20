package ch.kostceco.tools.siardval.service.impl.a.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.service.api.SiardValidator;
import ch.kostceco.tools.siardval.service.impl.a.internal.Activator;

public class ATestCase
{
	private ServiceTracker<SiardValidator, SiardValidator> siardTracker;
	
	SiardValidator siardValidator;
	
	private String tmpDir;
	
	@Before
	public void setUp() throws Exception
	{
		siardTracker = new ServiceTracker<SiardValidator, SiardValidator>(Activator.getContext(), SiardValidator.class, null);
		siardTracker.open();
		siardValidator = siardTracker.getService();

		File file = File.createTempFile("tmp_", ".siard");
		tmpDir = file.getParent();
	}

	@After
	public void tearDown() throws Exception
	{
		siardTracker.close();
	}

	@Test
	public void testValidate()
	{
		File file = new File(tmpDir.concat(File.separator.concat("gebaeudeversicherung.siard")));
		Assert.assertTrue(siardValidator.validate(file).isOK());
	}

}
