package org.opf.pst;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.fail;

public class EnronPstTest {
    PstDetector pstd = new PstDetector();

    @Test
    public void test() throws IOException,TransformerConfigurationException, SAXException, TikaException {
        System.out.println("ENRON TEST");
        File[] enronFiles = new File("src/test/resources/org/opf/enron_pst").listFiles();
        for(File file: enronFiles){
            MediaType mt = pstd.detect(new FileInputStream(file), new Metadata());
            if(mt != MediaType.application("vnd.ms-outlook")){
                fail("Did not detect vnd.ms-outlook mimetype");
            }

            //transform(file);
            System.out.println(file.getName() + ": " + mt);
        }
    }

    private void transform(File file) throws TransformerConfigurationException, SAXException,
            IOException, TikaException {
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
}
