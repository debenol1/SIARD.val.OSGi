package ch.kostceco.checksum.api.service;

import java.io.File;
import java.io.IOException;

public interface ChecksumService
{
	boolean compare(File file1, File file2) throws IOException;
}
