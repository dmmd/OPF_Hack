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

import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.junit.Test;

/**
 * @author Carl Wilson (carl@openplanetsfoundation.org)
 *
 */
public class PstDetectorTest {

	/**
	 * OK for now loop through the sample psts and identify
	 * 
	 * Test method for {@link org.opf.pst.PstDetector#detect(java.io.InputStream, org.apache.tika.metadata.Metadata)}.
	 * @throws URISyntaxException 
	 */
	@Test
	public void testDetectPst() throws URISyntaxException {
		PstDetector detector = new PstDetector();
		 MediaType pstType = MediaType.application("vnd.ms-outlook");
		for (File file : AllTests.getPstTestFiles()) {
			try {
				MediaType mt = detector.detect(new FileInputStream(file), new Metadata());
				assertEquals("Expected a pst mime type", mt, pstType);
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
		 MediaType pstType = MediaType.application("vnd.ms-outlook");
		for (File file : AllTests.getNonPstTestFiles()) {
			try {
				MediaType mt = detector.detect(new FileInputStream(file), new Metadata());
				assertNotEquals("Expected a none pst mime type", mt, pstType);
			} catch (IOException e) {
				System.err.println("Failed to find test file that was detected:");
				e.printStackTrace();
				fail("Test data disappeared?");
			}
		}
	}
}
