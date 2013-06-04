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

public class PstParser extends AbstractParser{

    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return Collections.singleton(MediaType.application("vnd.ms-outlook"));
    }

    @Override
    public void parse(InputStream in, ContentHandler ch, Metadata mtdt, ParseContext pc) throws IOException, SAXException, TikaException {
        
    }
}
