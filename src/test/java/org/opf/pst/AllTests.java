package org.opf.pst;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LibPstTest.class, PstDetectorTest.class, PstParserTest.class })
public class AllTests {
	private static final String TEST_ROOT = "org/opf/";
	private static final String TEST_PST = TEST_ROOT + "pst/";
	private static final String TEST_NONPST = TEST_ROOT + "nonpst/";
	private static final String PST_EXT = "pst";

	/**
	 * @return all of pst test files
	 * @throws URISyntaxException when looking up the test resource goes wrong...
	 */
	public static final Collection<File> getPstTestFiles() throws URISyntaxException {
		return getResourceFilesByExt(TEST_PST, true, PST_EXT);
	}

	/**
	 * @return all of non pst test files
	 * @throws URISyntaxException when looking up the test resource goes wrong...
	 */
	public static final Collection<File> getNonPstTestFiles() throws URISyntaxException {
		return getResourceFilesByExt(TEST_NONPST, true, PST_EXT);
	}

	/**
	 * @param resName
	 *            the name of the resource to retrieve a file for
	 * @return the java.io.File for the named resource
	 * @throws URISyntaxException
	 *             if the named resource can't be converted to a URI
	 */
	public final static File getResourceAsFile(String resName)
			throws URISyntaxException {
		URI uri = ClassLoader.getSystemResource(resName).toURI();
		return new File(uri);
	}

	@SuppressWarnings("unused")
	private final static Collection<File> getResourceFiles(String resName,
			boolean recurse) throws URISyntaxException {
		return getResourceFilesByExt(resName, recurse, null);
	}

	private final static Collection<File> getResourceFilesByExt(String resName,
			boolean recurse, String ext) throws URISyntaxException {
		File root = getResourceAsFile(resName);
		return FileUtils.listFiles(root, new String[]{ext}, recurse);
	}

}
