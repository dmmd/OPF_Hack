package org.opf.pst;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class PstReader{

    public PstReader() throws FileNotFoundException, PSTException, IOException {
        File f = new File("/Users/dm/Desktop/outlook.ost");
        PSTFile p = new PSTFile(f);
        PSTFolder root = p.getRootFolder();
        getFolders(root, 0);
    
    }
    
    public static void main(String[] args) throws FileNotFoundException, PSTException, IOException{
        PstReader p = new PstReader();
    }
    
    private void getFolders(PSTFolder folderIn, int depth) throws PSTException, IOException{
        StringBuilder sb = new StringBuilder();
        if(depth > 0){
            for(int i = 0; i < depth; i++){sb.append("\t");}
        }

        for(PSTFolder folder : folderIn.getSubFolders()){
            System.out.println(sb.toString() + folder.getDisplayName());
			
			if (folder.getContentCount() > 0) {
	            sb.append("\t");
	            PSTMessage email = (PSTMessage)folder.getNextChild();
	            while (email != null) {
	                System.out.println(sb.toString() + "Email: "+email.getSubject());
	                email = (PSTMessage)folder.getNextChild();
	            }
	        }
	        
	
            if(folder.hasSubfolders()){
                getFolders(folder, ++depth);
            }
        }
    }
}
