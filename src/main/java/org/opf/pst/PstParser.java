package org.opf.pst;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import com.pff.*;
import java.io.*;

public class PstParser extends AbstractParser{
    private static final Set<MediaType> SUPPORTED_TYPES =
        Collections.singleton(MediaType.application("vnd.ms-outlook"));

    public static final String OUTLOOK_MIME_TYPE = "application/vnd.ms-outlook";
    
    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return Collections.singleton(MediaType.application("vnd.ms-outlook"));
    }

    @Override
    public void parse(InputStream in, ContentHandler ch, Metadata mtdt, ParseContext pc) throws IOException, SAXException, TikaException {
        
		mtdt.set(Metadata.CONTENT_TYPE, OUTLOOK_MIME_TYPE);
        mtdt.set(Metadata.CONTENT_ENCODING, "utf-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null){
            //libpst stuffs here
        }
		System.out.println(mtdt);
    }
}
