package org.opf.pst;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;

public class LibPstTest {

	@Test
	public void testANSI() throws Exception {
		File f = new File("src/test/resources/org/opf/pst/test_ansi.pst");
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

				while (email != null) {
					System.out.println(sb.toString() + "Subject: "
							+ email.getSubject() + " FROM: "
							+ email.getDisplayName());
					email = (PSTMessage) folder.getNextChild();
				}
			}

			if (folder.hasSubfolders()) {
				getFolders(folder, ++depth);
			}
		}
	}
}
