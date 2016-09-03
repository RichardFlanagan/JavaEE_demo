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

@XmlRootElement(name = "books")
@XmlAccessorType (XmlAccessType.FIELD)
public class BookList {
	@XmlElement(name = "book")
	private ArrayList<Book> books = null;

	public BookList() {}

	public BookList(ArrayList<Book> books) {
		this.books = books;
	}

	public ArrayList<Book> getBookList() {
		return books;
	}

	public void setBookList(ArrayList<Book> books) {
		this.books = books;
	}
	
	public String getListHTML(){
		Iterator<Book> it = getBookList().iterator();
		StringBuilder displayString = new StringBuilder();
		
		displayString.append("<div>");
		while(it.hasNext()){
			Book b = it.next();
			displayString.append(b.getListHTML());
		}
		displayString.append("</div>");
		
		return displayString.toString();
	}
	
	public static BookList parseFromXML(String xml){
		BookList booklist = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			booklist = (BookList) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return booklist;
	}

}
