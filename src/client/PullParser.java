package client;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullParser {

	boolean inHello = false;

	public String parse(String xml) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser pullParser = factory.newPullParser();
			pullParser.setInput(new StringReader(xml));
			return processXml(pullParser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String processXml(XmlPullParser pullParser) throws XmlPullParserException, IOException {
		String message = "";
		int eventType = pullParser.getEventType();
		
		while(true) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				message += formatString(pullParser, "Start Document", "");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				message += formatString(pullParser, "End Document", "");
				break;
			} else if (eventType == XmlPullParser.START_TAG) {
				message += formatString(pullParser, "Start Tag", pullParser.getName());
			} else if (eventType == XmlPullParser.END_TAG) {
				message += formatString(pullParser, "End Tag", pullParser.getName());
			} else if (eventType == XmlPullParser.TEXT) {
				message += formatString(pullParser, "Text", pullParser.getText());
			}
			eventType = pullParser.next();
		}
		
		return message;
	}
	
	public String formatString(XmlPullParser pullParser, String prefix, String content){
		StringBuilder str = new StringBuilder();
		
		for(int i=0; i<pullParser.getDepth(); i++){
			str.append("&nbsp;&nbsp;&nbsp;&nbsp;");//"&#09;";
		}
		
		str.append("<span style='font-style:italics;'>");
		str.append(prefix);
		str.append(content.equals("") ? "" : ": ");
		str.append("</span>");
		
		str.append(content);
		str.append("<br/>");
		
		return str.toString();
	}
}
