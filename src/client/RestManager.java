package client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import data.Author;
import data.Book;
import data.Publisher;

public class RestManager {

	public static String getHomepage() {
		return get("/");
	}

	public static String getBooks() {
		return get("/books");
	}

	public static String getBooks(int id) {
		return get("/books/" + id);
	}

	public static String getAuthors() {
		return get("/authors");
	}

	public static String getAuthor(int id) {
		return get("/authors/" + id);
	}
	
	public static String getPublishers() {
		return get("/publishers");
	}

	public static String getPublisher(int id) {
		return get("/publishers/" + id);
	}
	
	public static boolean addBook(Book book) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("title", book.getTitle()));
		postParameters.add(new BasicNameValuePair("year", "" + book.getPublishYear()));
		postParameters.add(new BasicNameValuePair("author", "" + book.getAuthor().getId()));
		postParameters.add(new BasicNameValuePair("publisher", "" + book.getPublisher().getId()));
		return post("/books", postParameters);
	}

	public static boolean addAuthor(Author author) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("first_name", author.getFirstName()));
		postParameters.add(new BasicNameValuePair("last_name", author.getLastName()));
		return post("/authors", postParameters);
	}
	
	public static boolean addPublisher(Publisher publisher) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("name", publisher.getName()));
		return post("/publishers", postParameters);
	}
	
	public static boolean editBook(Book book) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("title", book.getTitle()));
		postParameters.add(new BasicNameValuePair("year", "" + book.getPublishYear()));
		postParameters.add(new BasicNameValuePair("author", "" + book.getAuthor().getId()));
		postParameters.add(new BasicNameValuePair("publisher", "" + book.getPublisher().getId()));
		return put("/books/"+book.getId(), postParameters);
	}
	
	public static boolean editAuthor(Author author) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("first_name", author.getFirstName()));
		postParameters.add(new BasicNameValuePair("last_name", author.getLastName()));
		return put("/authors/"+author.getId(), postParameters);
	}
	
	public static boolean editPublisher(Publisher publisher) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("name", publisher.getName()));
		return put("/publishers/"+publisher.getId(), postParameters);
	}
	
	public static boolean deleteBook(Book book){
		return delete("/books/"+book.getId());
	}
	
	public static boolean deleteAuthor(Author author){
		return delete("/authors/"+author.getId());
	}
	
	public static boolean deletePublisher(Publisher publisher){
		return delete("/publishers/"+publisher.getId());
	}

	public static String get(String path) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {
			HttpGet httpGet = new HttpGet("http://localhost:8080/A00193644_RichardFlanagan/library" + path);

			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);

			String str = getASCIIContentFromEntity(response.getEntity());

			httpClient.close();
			response.close();
			return str;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static boolean post(String path, ArrayList<NameValuePair> postParameters) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {
			HttpPost httpPost = new HttpPost("http://localhost:8080/A00193644_RichardFlanagan/library" + path);
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));

			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			httpClient.close();
			response.close();

			return (statusCode == 200);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean put(String path, ArrayList<NameValuePair> postParameters) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {
			HttpPut httpPut = new HttpPut("http://localhost:8080/A00193644_RichardFlanagan/library" + path);
			httpPut.setEntity(new UrlEncodedFormEntity(postParameters));

			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPut);

			int statusCode = response.getStatusLine().getStatusCode();

			httpClient.close();
			response.close();

			return (statusCode == 200);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(String path){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {
			HttpDelete httpDelete = new HttpDelete("http://localhost:8080/A00193644_RichardFlanagan/library" + path);

			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpDelete);

			int statusCode = response.getStatusLine().getStatusCode();

			httpClient.close();
			response.close();

			return (statusCode == 200);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}

}
