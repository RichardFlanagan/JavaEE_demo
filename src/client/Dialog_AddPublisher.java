package client;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Publisher;

public class Dialog_AddPublisher extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField name_input;

	public Dialog_AddPublisher(JFrame parent){
		super(parent, "Add a publisher", true);
		build();
		this.setLocation(300, 300);
		this.setVisible(true);
	}
	
	private void build(){
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(450, 180));
		
		JLabel main_label = new JLabel("<html><h2>Add a new publisher</h2></html>");
		mainPanel.add(main_label);
		
		JPanel name_panel = new JPanel();
		mainPanel.add(name_panel);
		
		JLabel name_label = new JLabel("Publisher Name: ");
		name_panel.add(name_label);
		
		name_input = new JTextField(30);
		name_panel.add(name_input);
		
		JPanel button_panel = new JPanel();
		mainPanel.add(button_panel);
		
		JButton submit_button = new JButton("Submit");
		button_panel.add(submit_button);
		
		submit_button.addActionListener(e -> {
			String name = name_input.getText();
			
			Publisher p = new Publisher();
			p.setName(name);
			
			RestManager.addPublisher(p);
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
