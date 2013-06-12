package org.nypl.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.opf.pst.PstDetector;
import org.opf.pst.PstParser;
import java.io.File;
import java.io.IOException;


public class TikaTest {
    public static void main(String[] args) throws IOException, TikaException{
        File f = new File("src/test/resources/org/opf/pst/test_ansi.pst");
        Tika tika = new Tika(new PstDetector(), new PstParser());
        System.out.println(tika.detect(f));
        System.out.println(tika.parseToString(f));
    }
}
