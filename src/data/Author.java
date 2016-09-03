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

@XmlRootElement(name = "author")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "firstName", "lastName" })
public class Author {
	private int id = -1;
	private String firstName = "";
	private String lastName = "";

	public Author() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getListHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append("<span style='font-size:150%;'>Author: ");
		str.append(MessageFormat.format("<a href=\"/authors/{0}\">{1}, {2}</a>", getId(), getLastName(), getFirstName()));
		str.append("</span>");
		str.append("</div>");
		str.append("<hr/>");
		return str.toString();
	}

	public String getPageHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append(MessageFormat.format("<h1>{0}, {1}</h1>", getLastName(), getFirstName()));
		str.append(MessageFormat.format("<p>ID: {0}</p>", getId()));
		str.append(MessageFormat.format("<p>Name: {0} {1}</p>", getFirstName(), getLastName()));
		str.append("</div>");
		return str.toString();
	}

	public static Author parseFromXML(String xml) {
		Author author = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Author.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			author = (Author) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return author;
	}

}
