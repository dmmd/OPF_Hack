package org.opf.pst;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.Stack;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;
import com.pff.PSTObject;

public class PstParser extends AbstractParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7158793395029661433L;
	@SuppressWarnings("unused")
	private static final Set<MediaType> SUPPORTED_TYPES = Collections
			.singleton(MediaType.application("vnd.ms-outlook"));

	public Set<MediaType> getSupportedTypes(ParseContext context) {
		return Collections.singleton(MediaType.application("vnd.ms-outlook"));
	}

	public static final String OUTLOOK_MIME_TYPE = "application/vnd.ms-outlook";

	public void parse(InputStream in, ContentHandler ch, Metadata mtdt,
			ParseContext pc) throws IOException, SAXException, TikaException {
		mtdt.set(Metadata.CONTENT_TYPE, OUTLOOK_MIME_TYPE);
		mtdt.set(Metadata.CONTENT_ENCODING, "utf-8"); // FIXME not detected
		TemporaryResources tmp = new TemporaryResources();
		try {
			TikaInputStream tis = TikaInputStream.get(in, tmp);
			PSTFile p = new PSTFile(tis.getFile());
			XHTMLContentHandler xhtml = new XHTMLContentHandler(ch, mtdt);
			parseFolder(p.getRootFolder(), new Stack<String>(), xhtml, mtdt, pc);
		} catch (PSTException p) {
			System.err.println(p);
		} finally {
			tmp.dispose();
		}
	}

	private void parseFolder(PSTFolder folder, Stack<String> folderNames,
			XHTMLContentHandler ch, Metadata mtdt, ParseContext pc)
			throws TikaException {
		folderNames.push(folder.getDisplayName());

		try {
			// parse emails
			if (folder.getContentCount() > 0) {
				for (PSTObject o = folder.getNextChild(); o != null; o = folder
						.getNextChild()) {
					if (o instanceof PSTMessage) {
						PSTMessage email = (PSTMessage) o;
						parseMessage(email, folderNames, ch, mtdt, pc);
					}
				}

			}

			for (PSTFolder f : folder.getSubFolders()) {
				parseFolder(f, folderNames, ch, mtdt, pc);
			}
		} catch (Exception e) {
			throw new TikaException("Error parsing folder", e);
		}
	}

	private void parseMessage(PSTMessage email, Stack<String> folderNames,
		XHTMLContentHandler xhtml, Metadata mtdt, ParseContext pc) throws TikaException, SAXException, IOException {
		String subject = email.getSubject();
		String sendEmail = email.getSenderEmailAddress();
		String body = email.getBody();
		String receiveEmail = email.getReceivedByAddress();
		Date receiveTime = email.getMessageDeliveryTime();
		boolean hasAttachment = email.hasAttachments();
		int numAttachments = email.getNumberOfAttachments();

        xhtml.startElement("div", "class", "email-entry");
		xhtml.startElement("div", "class", "header");
       	xhtml.startElement("dl");
	
		header(xhtml, "From", sendEmail);
		header(xhtml, "To", receiveEmail);
		header(xhtml, "Received Time", receiveTime.toString());
		header(xhtml, "Subject", subject);
	//header(xhtml, "Cc", cc.toString());
	//header(xhtml, "Bcc", bcc.toString());
		xhtml.endElement("dl");
		xhtml.endElement("div");
		xhtml.startElement("div", "class", "body");
		xhtml.element("div", body);
        xhtml.endElement("div");
	}
	
	private void header(XHTMLContentHandler xhtml, String key, String value)
			throws SAXException {
		if (value.length() > 0) {
			xhtml.element("dt", key);
			xhtml.element("dd", value);
		}
	}
}
