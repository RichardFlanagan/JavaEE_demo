package client;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Author;
import data.Book;
import data.Publisher;

public class Dialog_EditBook extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField name_input;
	private JTextField year_input;
	private JTextField author_input;
	private JTextField publisher_input;
	private Book book;

	public Dialog_EditBook(JFrame parent, Book book){
		super(parent, "Edit a book", true);
		this.book = book;
		build();
		this.setLocation(300, 300);
		this.setVisible(true);
	}
	
	private void build(){
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(450, 250));
		
		JLabel main_label = new JLabel("<html><h2>Edit an existing book</h2></html>");
		mainPanel.add(main_label);
		
		JPanel name_panel = new JPanel();
		mainPanel.add(name_panel);
		
		JLabel name_label = new JLabel("Book Title: ");
		name_panel.add(name_label);
		
		name_input = new JTextField(30);
		name_panel.add(name_input);
		
		JPanel year_panel = new JPanel();
		mainPanel.add(year_panel);
		
		JLabel year_label = new JLabel("Publish Year: ");
		year_panel.add(year_label);
		
		year_input = new JTextField(30);
		year_panel.add(year_input);
		
		JPanel author_panel = new JPanel();
		mainPanel.add(author_panel);
		
		JLabel author_label = new JLabel("Author Id: ");
		author_panel.add(author_label);
		
		author_input = new JTextField(30);
		author_panel.add(author_input);
		
		JPanel publisher_panel = new JPanel();
		mainPanel.add(publisher_panel);
		
		JLabel publisher_label = new JLabel("Publisher Id: ");
		publisher_panel.add(publisher_label);
		
		publisher_input = new JTextField(30);
		publisher_panel.add(publisher_input);
		
		JPanel button_panel = new JPanel();
		mainPanel.add(button_panel);
		
		JButton submit_button = new JButton("Submit");
		button_panel.add(submit_button);
		
		submit_button.addActionListener(e -> {
			String name = name_input.getText();
			String year = year_input.getText();
			int authorId = Integer.parseInt(author_input.getText());
			int publisherId = Integer.parseInt(publisher_input.getText());
			
			book.setTitle(name);
			book.setPublishYear(year);
			
			Author a = new Author();
			a.setId(authorId);
			book.setAuthor(a);
			
			Publisher p = new Publisher();
			p.setId(publisherId);
			book.setPublisher(p);
			
			RestManager.editBook(book);
			this.dispose();
		});
		
		JButton cancel_button = new JButton("Cancel");
		button_panel.add(cancel_button);
		
		cancel_button.addActionListener(e -> {
			this.dispose();
		});
		
		this.add(mainPanel);
		this.pack();
	}

}
