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

@XmlRootElement(name = "authors")
@XmlAccessorType (XmlAccessType.FIELD)
public class AuthorList {
	@XmlElement(name = "author")
	private ArrayList<Author> authors = null;

	public AuthorList() {}

	public AuthorList(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public ArrayList<Author> getAuthorList() {
		return authors;
	}

	public void setAuthorList(ArrayList<Author> authors) {
		this.authors = authors;
	}
	
	public String getListHTML(){
		Iterator<Author> it = getAuthorList().iterator();
		StringBuilder displayString = new StringBuilder();
		
		displayString.append("<div>");
		while(it.hasNext()){
			Author a = it.next();
			displayString.append(a.getListHTML());
		}
		displayString.append("</div>");
		
		return displayString.toString();
	}
	
	public static AuthorList parseFromXML(String xml){
		AuthorList authorlist = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AuthorList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			authorlist = (AuthorList) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return authorlist;
	}

}
