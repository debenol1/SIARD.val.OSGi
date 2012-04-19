package ch.kostceco.tools.siardval.fs.api.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileSystem
{
	File cache(InputStream inputStream) throws IOException;
}
