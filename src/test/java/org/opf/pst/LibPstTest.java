package org.opf.pst;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;

public class LibPstTest {

	@Test
	public void test() throws Exception {
		File f = new File("src/test/resources/org/opf/pst/sample1.pst");
		PSTFile p = new PSTFile(f);
		PSTFolder root = p.getRootFolder();
		getFolders(root, 0);
	}

	private void getFolders(PSTFolder folderIn, int depth) throws PSTException,
			IOException {
		StringBuilder sb = new StringBuilder();
		if (depth > 0) {
			for (int i = 0; i < depth; i++) {
				sb.append("  ");
			}
		}

		for (PSTFolder folder : folderIn.getSubFolders()) {
			System.out.println(sb.toString() + folder.getDisplayName());

			if (folder.getContentCount() > 0) {
				sb.append("  ");
				PSTMessage email = (PSTMessage) folder.getNextChild();
				
				String subject = email.getSubject();
				String sendEmail = email.getSenderEmailAddress();
				String body = email.getBody();
				String receiveEmail = email.getReceivedByAddress();
				Date receiveTime = email.getMessageDeliveryTime();
				boolean hasAttachment = email.hasAttachments();
				int numAttachments = email.getNumberOfAttachments();
				
				while (email != null) {
					System.out.println(
							"<div><class><email-entry>\nSUBJECT: " + subject
							+ "\nFROM: " + sendEmail
							+ "\nTO:" + receiveEmail
							+ "\nDATE: " + receiveTime
							+"\nHAS ATTACH: " + hasAttachment
							+"\nNUM ATTACHMENTS: " + numAttachments
							+ "\nCONTENT:" + body +"</email-entry></class><div>\n"
							);
							
					email = (PSTMessage) folder.getNextChild();
				}
			}

			if (folder.hasSubfolders()) {
				getFolders(folder, ++depth);
			}
		}
	}
}
