/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opf.pst;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
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
            if(folder.hasSubfolders()){
                getFolders(folder, ++depth);
            }
        }
    }
}
