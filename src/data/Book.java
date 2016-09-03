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

@XmlRootElement(name = "book")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "title", "publishYear", "author", "publisher" })
public class Book {
	private int id = -1;
	private String title = "";
	private String publishYear = "0";
	private Author author = null;
	private Publisher publisher = null;

	public Book() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getListHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append("<span style='font-size:150%;'>Title: ");
		str.append(MessageFormat.format("<a href=\"/books/{0}\">{1}</a>", getId(), getTitle()));
		str.append(MessageFormat.format("</span> ({0})", getPublishYear()));
		str.append("<br/>");
		str.append(MessageFormat.format("Author: <a href=\"/authors/{0}\">{1}, {2}</a>", author.getId(), author.getLastName(), author.getFirstName()));
		str.append("</div>");
		str.append("<hr/>");
		return str.toString();
	}

	public String getPageHTML() {
		StringBuilder str = new StringBuilder();
		str.append("<div>");
		str.append(MessageFormat.format("<h1>{0}</h1>", getTitle()));
		str.append(MessageFormat.format("<p>ID: {0}</p>", getId()));
		str.append(MessageFormat.format("<p>Title: {0}</p>", getTitle()));
		str.append(MessageFormat.format("<p>Year: {0}</p>", getPublishYear()));
		str.append(MessageFormat.format("<p>Author: <a href=\"/authors/{0}\">{1}, {2}</a></p>", author.getId(), author.getLastName(), author.getFirstName()));
		str.append(MessageFormat.format("<p>Publisher: <a href=\"/publishers/{0}\">{1}</a></p>", publisher.getId(), publisher.getName()));
		str.append("</div>");
		return str.toString();
	}

	public static Book parseFromXML(String xml) {
		Book book = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			book = (Book) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return book;
	}

}
