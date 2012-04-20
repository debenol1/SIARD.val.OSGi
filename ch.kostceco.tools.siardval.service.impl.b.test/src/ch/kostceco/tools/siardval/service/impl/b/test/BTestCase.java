package ch.kostceco.tools.siardval.service.impl.b.test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import ch.kostceco.tools.siardval.service.api.SiardValidator;
import ch.kostceco.tools.siardval.service.impl.b.internal.Activator;

public class BTestCase
{
	private ServiceTracker<SiardValidator, SiardValidator> siardTracker;
	
	Map<Integer, SiardValidator> siardValidators = new HashMap<Integer, SiardValidator>();
	
	private String tmpDir;
	
	@Before
	public void setUp() throws Exception
	{
		siardTracker = new ServiceTracker<SiardValidator, SiardValidator>(Activator.getContext(), SiardValidator.class, null);
		siardTracker.open();
		ServiceReference<SiardValidator>[] references = siardTracker.getServiceReferences();
		for (ServiceReference<SiardValidator> reference : references)
		{
			Integer ranking = (Integer) reference.getProperty(Constants.SERVICE_RANKING);
			if (ranking != null)
			{
				siardValidators.put(ranking, siardTracker.getService(reference));
			}
		}
		
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
		Integer[] keys = siardValidators.keySet().toArray(new Integer[0]);
		Arrays.sort(keys);
		for (int i = keys.length - 1; i >= 0; i--)
		{
			SiardValidator validator = siardValidators.get(keys[i]);
			Assert.assertTrue(validator.validate(file).isOK());
		}
	}

}
