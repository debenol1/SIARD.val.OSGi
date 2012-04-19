package ch.kostceco.tools.checksum.api.service;

import java.io.IOException;
import java.io.InputStream;

public interface ChecksumService
{
	String getChecksum(InputStream in) throws IOException;

	String[] getAvailableDigests();
}
