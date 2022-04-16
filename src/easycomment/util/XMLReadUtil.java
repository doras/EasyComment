package easycomment.util;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class XMLReadUtil {
	public static int getLineNoOfCurrentElement(InputStream is, int lineNo) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		int resultLineNo = -1;
		try {
			int depth = 0;
			XMLStreamReader xmlreader = factory.createXMLStreamReader(is);
			while (xmlreader.hasNext()) {
				if(xmlreader.getLocation().getLineNumber() > lineNo + 1) {
					break;
				}
				switch (xmlreader.getEventType()) {
					case XMLStreamReader.START_ELEMENT: {
						if(depth == 1) {
							resultLineNo = xmlreader.getLocation().getLineNumber();
						}
						depth++;
						break;
					}
					case XMLStreamReader.END_ELEMENT: {
						depth--;
						break;
					}
				}
				xmlreader.next();
			}
			xmlreader.close();
		} catch (Throwable e) {
		}

		return resultLineNo;
	}
}
