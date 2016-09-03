package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;

import data.Author;
import data.AuthorList;
import data.Book;
import data.BookList;
import data.Publisher;
import data.PublisherList;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextPane displayPane = null;
	
	private JButton b_displayBooks;
	private JButton b_addBook;
	private JButton b_editBook;
	private JButton b_deleteBook;
	
	private JButton b_displayAuthors;
	private JButton b_addAuthor;
	private JButton b_editAuthor;
	private JButton b_deleteAuthor;
	
	private JButton b_displayPublishers;
	private JButton b_addPublisher;
	private JButton b_editPublisher;
	private JButton b_deletePublisher;
	
	private String xml = "";
	private Book book;
	private Author author;
	private Publisher publisher;

	public Client() {
		buildMenuBar();
		buildSidebar();
		buildDisplayPanel();
		this.pack();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("A00193644 : Distributed Systems");
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
		
		renderHTML(RestManager.getHomepage());
	}
	
	public void showButtons(boolean showAllBookButtons, boolean showAllAuthorButtons, boolean showAllPublisherButtons){
		b_displayBooks.setEnabled(true);
		b_addBook.setEnabled(true);
		b_editBook.setEnabled(showAllBookButtons);
		b_deleteBook.setEnabled(showAllBookButtons);
		
		b_displayAuthors.setEnabled(true);
		b_addAuthor.setEnabled(true);
		b_editAuthor.setEnabled(showAllAuthorButtons);
		b_deleteAuthor.setEnabled(showAllAuthorButtons);
		
		b_displayPublishers.setEnabled(true);
		b_addPublisher.setEnabled(true);
		b_editPublisher.setEnabled(showAllPublisherButtons);
		b_deletePublisher.setEnabled(showAllPublisherButtons);
	}

	private void buildSidebar() {
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setPreferredSize(new Dimension(200, 500));

		JLabel label_1 = new JLabel("<html><div style='font-size:large;'>Book Info System</div><html>",
				SwingConstants.CENTER);
		label_1.setPreferredSize(new Dimension(180, 24));
		sidebarPanel.add(label_1);

		JLabel label_2 = new JLabel("Richard Flanagan", SwingConstants.CENTER);
		label_2.setPreferredSize(new Dimension(180, 18));
		sidebarPanel.add(label_2);

		JLabel label_3 = new JLabel("A00193644", SwingConstants.CENTER);
		label_3.setPreferredSize(new Dimension(180, 18));
		sidebarPanel.add(label_3);
		
		JPanel booksButtonGroup = new JPanel();
		booksButtonGroup.setPreferredSize(new Dimension(190, 150));
		booksButtonGroup.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel authorsButtonGroup = new JPanel();
		authorsButtonGroup.setPreferredSize(new Dimension(190, 150));
		authorsButtonGroup.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel publishersButtonGroup = new JPanel();
		publishersButtonGroup.setPreferredSize(new Dimension(190, 150));
		publishersButtonGroup.setBorder(BorderFactory.createEtchedBorder());

		// Home Button
		JButton b_home = new JButton("Home");
		b_home.setPreferredSize(new Dimension(180, 30));
		sidebarPanel.add(b_home);

		b_home.addActionListener(e -> {
				renderHTML(RestManager.getHomepage());
				this.showButtons(false, false, false);
			}
		);

		// Show all books button
		b_displayBooks = new JButton("Browse Books");
		b_displayBooks.setPreferredSize(new Dimension(180, 30));
		booksButtonGroup.add(b_displayBooks);

		b_displayBooks.addActionListener(e -> {
				String displayString = "Something went wrong!";
				BookList booklist = BookList.parseFromXML(RestManager.getBooks());

				if (booklist != null) {
					displayString = booklist.getListHTML();
				}

				renderHTML(displayString);
				this.showButtons(false, false, false);
			}
		);
		
		// Add new book button
		b_addBook = new JButton("Add Book");
		b_addBook.setPreferredSize(new Dimension(180, 30));
		booksButtonGroup.add(b_addBook);

		b_addBook.addActionListener(e -> {
				this.showButtons(false, false, false);
				new Dialog_AddBook(this);
				this.b_displayBooks.doClick();
			}
		);
		
		// Edit book button
		b_editBook = new JButton("Edit Book");
		b_editBook.setPreferredSize(new Dimension(180, 30));
		booksButtonGroup.add(b_editBook);

		b_editBook.addActionListener(e -> {
				new Dialog_EditBook(this, book);
				this.b_displayBooks.doClick();
			}
		);
		
		// Delete book button
		b_deleteBook = new JButton("Delete Book");
		b_deleteBook.setPreferredSize(new Dimension(180, 30));
		booksButtonGroup.add(b_deleteBook);

		b_deleteBook.addActionListener(e -> {
				RestManager.deleteBook(book);
				this.b_displayBooks.doClick();
			}
		);
		
		// Show all authors button
		b_displayAuthors = new JButton("Browse Authors");
		b_displayAuthors.setPreferredSize(new Dimension(180, 30));
		authorsButtonGroup.add(b_displayAuthors);

		b_displayAuthors.addActionListener(e -> {
				String displayString = "Something went wrong!";
				AuthorList authorlist = AuthorList.parseFromXML(RestManager.getAuthors());

				if (authorlist != null) {
					displayString = authorlist.getListHTML();
				}

				renderHTML(displayString);
				this.showButtons(false, false, false);
			}
		);
		
		// Add new author button
		b_addAuthor = new JButton("Add Author");
		b_addAuthor.setPreferredSize(new Dimension(180, 30));
		authorsButtonGroup.add(b_addAuthor);
	
		b_addAuthor.addActionListener(e -> {
				this.showButtons(false, false, false);
				new Dialog_AddAuthor(this);
				this.b_displayAuthors.doClick();
			}
		);
		
		// Edit author button
		b_editAuthor = new JButton("Edit Author");
		b_editAuthor.setPreferredSize(new Dimension(180, 30));
		authorsButtonGroup.add(b_editAuthor);

		b_editAuthor.addActionListener(e -> {
				new Dialog_EditAuthor(this, author);
				this.b_displayAuthors.doClick();
			}
		);
		
		// Delete author button
		b_deleteAuthor = new JButton("Delete Author");
		b_deleteAuthor.setPreferredSize(new Dimension(180, 30));
		authorsButtonGroup.add(b_deleteAuthor);

		b_deleteAuthor.addActionListener(e -> {
				RestManager.deleteAuthor(author);
				this.b_displayAuthors.doClick();
			}
		);
		
		// Show all publishers button
		b_displayPublishers = new JButton("Browse Publishers");
		b_displayPublishers.setPreferredSize(new Dimension(180, 30));
		publishersButtonGroup.add(b_displayPublishers);

		b_displayPublishers.addActionListener(e -> {
				String displayString = "Something went wrong!";
				PublisherList publisherList = PublisherList.parseFromXML(RestManager.getPublishers());

				if (publisherList != null) {
					displayString = publisherList.getListHTML();
				}

				renderHTML(displayString);
				this.showButtons(false, false, false);
			}
		);
		
		// Add new publisher button
		b_addPublisher = new JButton("Add Publisher");
		b_addPublisher.setPreferredSize(new Dimension(180, 30));
		publishersButtonGroup.add(b_addPublisher);

		b_addPublisher.addActionListener(e -> {
				this.showButtons(false, false, false);
				new Dialog_AddPublisher(this);
				this.b_displayPublishers.doClick();
			}
		);
		
		// Edit publisher button
		b_editPublisher = new JButton("Edit Publisher");
		b_editPublisher.setPreferredSize(new Dimension(180, 30));
		publishersButtonGroup.add(b_editPublisher);

		b_editPublisher.addActionListener(e -> {
				new Dialog_EditPublisher(this, publisher);
				this.b_displayPublishers.doClick();
			}
		);
		
		// Delete publisher button
		b_deletePublisher = new JButton("Delete Publisher");
		b_deletePublisher.setPreferredSize(new Dimension(180, 30));
		publishersButtonGroup.add(b_deletePublisher);

		b_deletePublisher.addActionListener(e -> {
				RestManager.deletePublisher(publisher);
				this.b_displayPublishers.doClick();
			}
		);
		
		sidebarPanel.add(booksButtonGroup);
		sidebarPanel.add(authorsButtonGroup);
		sidebarPanel.add(publishersButtonGroup);
		
		// Pull parser demo
		JButton b_pullParser = new JButton("Pull Parser Demo");
		b_pullParser.setPreferredSize(new Dimension(180, 30));
		sidebarPanel.add(b_pullParser);
		b_pullParser.addActionListener(e -> {
			showButtons(false, false, false);
			displayPane.setText(new PullParser().parse(xml));
		});
		
		// Quit
		JButton b_quit = new JButton("Quit");
		b_quit.setPreferredSize(new Dimension(180, 30));
		sidebarPanel.add(b_quit);
		b_quit.addActionListener(e -> System.exit(0));
		
		showButtons(false, false, false);
		this.add(sidebarPanel, BorderLayout.WEST);
	}

	private void buildDisplayPanel() {
		JPanel displayPanel = new JPanel();
		displayPanel.setPreferredSize(new Dimension(600, 660));
		displayPanel.setBackground(new Color(255, 127, 127));

		displayPane = new JTextPane();
		displayPane.setContentType("text/html");
		displayPane.setPreferredSize(new Dimension(590, 648));
		displayPane.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(displayPane);
		displayPanel.add(scrollPane);

		displayPane.addHyperlinkListener(e -> {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					String target = e.getDescription();
					String response = RestManager.get(e.getDescription());
					
					if(target.contains("/books/")){
						book = Book.parseFromXML(response);
						renderHTML(book.getPageHTML());
						this.showButtons(true, false, false);
					}
					else if(target.contains("/authors/")){
						author = Author.parseFromXML(response);
						renderHTML(author.getPageHTML());
						this.showButtons(false, true, false);
					} 
					else if(target.contains("/publishers/")){
						publisher = Publisher.parseFromXML(response);
						renderHTML(publisher.getPageHTML());
						this.showButtons(false, false, true);
					}
					else {
						this.showButtons(false, false, false);
					}
				}
			}
		);

		this.add(displayPanel, BorderLayout.EAST);
	}

	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitItem);

		this.setJMenuBar(menuBar);
	}

	private void renderHTML(String html) {
		xml = html;
		
		String str = "<html><body style='font-family: sans-serif;'>"+html+"</body></html>";
		displayPane.setContentType("text/html");
		displayPane.setText(str);
	}
}
