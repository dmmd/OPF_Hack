package org.opf.pst;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.LookaheadInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

public class PstDetector implements Detector{

    @Override
    public MediaType detect(InputStream in, Metadata mtdt) throws IOException {
                
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(0x21);
        bos.write(0x42);
        bos.write(0x44);
        bos.write(0x4e);
        
        MediaType type = MediaType.OCTET_STREAM;
        InputStream lookahead = new LookaheadInputStream(in, 1024);        
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        
        for(int i = 0; i < 4; i++){
            int x = lookahead.read();
            fos.write(x);
        }
        
        if(Arrays.equals(bos.toByteArray(), fos.toByteArray())){
            return MediaType.application("vnd.ms-outlook");
        }
        return type;
    }
    
}
