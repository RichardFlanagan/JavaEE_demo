package data;

import java.io.StringReader;
import java.text.MessageFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "publisher")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name" })
public class Publisher {
	private int id = -1;
	private String name = "";

	public Publisher() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getListHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append("<span style='font-size:150%;'>Publisher: ");
		str.append(MessageFormat.format("<a href=\"/publishers/{0}\">{1}</a>", getId(), getName()));
		str.append("</span>");
		str.append("</div>");
		str.append("<hr/>");
		return str.toString();
	}

	public String getPageHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append(MessageFormat.format("<h1>{0}</h1>", getName()));
		str.append(MessageFormat.format("<p>ID: {0}</p>", getId()));
		str.append(MessageFormat.format("<p>Name: {0}</p>", getName()));
		str.append("</div>");
		return str.toString();
	}

	public static Publisher parseFromXML(String xml) {
		Publisher publisher = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			publisher = (Publisher) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return publisher;
	}

}
