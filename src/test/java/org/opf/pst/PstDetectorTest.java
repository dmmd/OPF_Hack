/**
 * 
 */
package org.opf.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Carl Wilson (carl@openplanetsfoundation.org)
 *
 */
public class PstDetectorTest {

    private MimeTypes mimeTypes;
	private MediaType pstType = MediaType.application("vnd.ms-outlook");


    /** @inheritDoc */
    @Before
    public void setUp() throws Exception {
        this.mimeTypes = TikaConfig.getDefaultConfig().getMimeRepository();
    }

	/**
	 * OK for now loop through the sample psts and identify
	 * 
	 * Test method for {@link org.opf.pst.PstDetector#detect(java.io.InputStream, org.apache.tika.metadata.Metadata)}.
	 * @throws URISyntaxException 
	 */
	@Test
	public void testDetectPst() throws URISyntaxException {
		PstDetector detector = new PstDetector();
		for (File file : AllTests.getPstTestFiles()) {
			try {
				MediaType mt = detector.detect(new FileInputStream(file), new Metadata());
				assertEquals("Expected a pst mime type", mt, this.pstType);
			} catch (IOException e) {
				System.err.println("Failed to find test file that was detected:");
				e.printStackTrace();
				fail("Test data disappeared?");
			}
		}
	}

	/**
	 * OK for now loop through the sample non psts and check they're not mis-identified
	 * 
	 * Test method for {@link org.opf.pst.PstDetector#detect(java.io.InputStream, org.apache.tika.metadata.Metadata)}.
	 * @throws URISyntaxException 
	 */
	@Test
	public void testNotDetectNonPst() throws URISyntaxException {
		PstDetector detector = new PstDetector();
		for (File file : AllTests.getNonPstTestFiles()) {
			try {
				MediaType mt = detector.detect(new FileInputStream(file), new Metadata());
				assertNotEquals("Expected a none pst mime type", mt, this.pstType);
			} catch (IOException e) {
				System.err.println("Failed to find test file that was detected:");
				e.printStackTrace();
				fail("Test data disappeared?");
			}
		}
	}

	/**
	 * So this test is a bit naughty as it's really testing the addition of the .pst sig tested above
	 * to Tika, the extended version of which is in the OPF's Bintray site.
	 * @throws URISyntaxException 
	 */
	@Test
	public void testNewTikaDetection() throws URISyntaxException {
		for (File file : AllTests.getNonPstTestFiles()) {
			try {
				MediaType mt = this.mimeTypes.detect(new FileInputStream(file), new Metadata());
				assertNotEquals("Expected a none pst mime type", mt, this.pstType);
			} catch (IOException e) {
				System.err.println("Failed to find test file that was detected:");
				e.printStackTrace();
				fail("Test data disappeared?");
			}
		}
	}
}
