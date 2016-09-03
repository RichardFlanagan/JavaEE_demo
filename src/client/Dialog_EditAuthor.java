package client;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Author;

public class Dialog_EditAuthor extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField firstName_input;
	private JTextField lastName_input;
	private Author author;

	public Dialog_EditAuthor(JFrame parent, Author author){
		super(parent, "Edit an author", true);
		this.author = author;
		build();
		this.setLocation(300, 300);
		this.setVisible(true);
	}
	
	private void build(){
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(450, 180));
		
		JLabel main_label = new JLabel("<html><h2>Edit an existing author</h2></html>");
		mainPanel.add(main_label);
		
		JPanel firstName_panel = new JPanel();
		mainPanel.add(firstName_panel);
		
		JLabel firstName_label = new JLabel("First Name: ");
		firstName_panel.add(firstName_label);
		
		firstName_input = new JTextField(30);
		firstName_panel.add(firstName_input);
		
		JPanel lastName_panel = new JPanel();
		mainPanel.add(lastName_panel);
		
		JLabel lastName_label = new JLabel("Last Name: ");
		lastName_panel.add(lastName_label);
		
		lastName_input = new JTextField(30);
		lastName_panel.add(lastName_input);
		
		JPanel button_panel = new JPanel();
		mainPanel.add(button_panel);
		
		JButton submit_button = new JButton("Submit");
		button_panel.add(submit_button);
		
		submit_button.addActionListener(e -> {
			String firstName = firstName_input.getText();
			String lastName = lastName_input.getText();
			author.setFirstName(firstName);
			author.setLastName(lastName);
			
			RestManager.editAuthor(author);
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
