package org.opf.pst;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.junit.Test;
import org.xml.sax.helpers.DefaultHandler;

public class PstParserTest {

	@Test
	public void test() throws Exception {
        System.out.println("PST Tika");
        File file = new File("test-inbox.pst");
        PstDetector pstd = new PstDetector();
        MediaType mt = pstd.detect(new FileInputStream(file), new Metadata());
        if(mt != MediaType.application("vnd.ms-outlook")){
        	fail("Did not detect vnd.ms-outlook mimetype");
//            System.out.println("Outlook file detected");
//            PstParser parser = new PstParser();
//
//				 parser.parse(new FileInputStream(file), new DefaultHandler(), new Metadata(), new ParseContext());
//
//           
        }
	}

}