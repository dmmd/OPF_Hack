package org.opf.pst;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;

public class PstParserTest {

	@Test
	public void test() throws Exception {
        System.out.println("PST Tika");
        File file = new File("src/test/resources/org/opf/pst/test_ansi.pst");
		//File file = new File("/home/count0/Downloads/outlook.pst");
        PstDetector pstd = new PstDetector();
        MediaType mt = pstd.detect(new FileInputStream(file), new Metadata());
        if(mt != MediaType.application("vnd.ms-outlook")){
        	fail("Did not detect vnd.ms-outlook mimetype");        
        }
        System.out.println("Outlook file detected");
        TransformerFactory tf = TransformerFactory.newInstance();
        if(!tf.getFeature(SAXTransformerFactory.FEATURE)){
          throw new RuntimeException(
            "Did not find a SAX-compatible TransformerFactory.");
        }
        SAXTransformerFactory stf = (SAXTransformerFactory)tf;
        TransformerHandler th = stf.newTransformerHandler();
        th.setResult(new StreamResult(System.out));
        th.startDocument();
        PstParser parser = new PstParser();
        parser.parse(new FileInputStream(file), th, new Metadata(), new ParseContext());
        th.endDocument();
	}
	
	//@Test
	public void testANSI() throws Exception {
        System.out.println("PST Tika");
        File file = new File("src/test/resources/org/opf/pst/test_ansi.pst");
        PstDetector pstd = new PstDetector();
        MediaType mt = pstd.detect(new FileInputStream(file), new Metadata());
        if(mt != MediaType.application("vnd.ms-outlook")){
        	fail("Did not detect vnd.ms-outlook mimetype");
            System.out.println("Outlook file detected");
            PstParser parser = new PstParser();
            parser.parse(new FileInputStream(file), new BodyContentHandler(System.out), new Metadata(), new ParseContext());        
        }
	}

}
