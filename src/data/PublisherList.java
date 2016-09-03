package data;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "publishers")
@XmlAccessorType (XmlAccessType.FIELD)
public class PublisherList {
	@XmlElement(name = "publisher")
	private ArrayList<Publisher> publishers = null;

	public PublisherList() {}

	public PublisherList(ArrayList<Publisher> publishers) {
		this.publishers = publishers;
	}

	public ArrayList<Publisher> getPublisherList() {
		return publishers;
	}

	public void setPublisherList(ArrayList<Publisher> publishers) {
		this.publishers = publishers;
	}
	
	public String getListHTML(){
		Iterator<Publisher> it = getPublisherList().iterator();
		StringBuilder displayString = new StringBuilder();
		
		displayString.append("<div>");
		while(it.hasNext()){
			Publisher p = it.next();
			displayString.append(p.getListHTML());
		}
		displayString.append("</div>");
		
		return displayString.toString();
	}
	
	public static PublisherList parseFromXML(String xml){
		PublisherList publisherList = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PublisherList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			publisherList = (PublisherList) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return publisherList;
	}

}
