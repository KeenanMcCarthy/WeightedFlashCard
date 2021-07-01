package flashCardPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* ************************************************************ *
 * Graphical User Interface build with Java Swing Framework,    *
 * Presents FlashCardDeck on desktop dashboard       			*
 * ************************************************************ */

public class SwingFrame extends WindowAdapter implements ActionListener{
	
	public JButton button = new JButton();
	public JTextField textField = new JTextField();
	public JLabel country_label = new JLabel();
	public JLabel veracity_label = new JLabel();
	public FlashCardDeck deck = new FlashCardDeck();
	public FlashCard fc = null;

	public void createAndShowGUI() throws IOException {
        
		URL url = getClass().getResource("input.txt");
        File input_file = new File(url.getPath());
        this.deck.addFlashCard(input_file);
        
        this.fc = this.deck.sample();
        
		this.button.setBounds(500, 500, 30, 30);
		this.button.addActionListener(this);
		this.button.setText("Enter");
		
		this.textField.setPreferredSize(new Dimension(100, 25));
		this.textField.addActionListener(this);
		
		country_label.setText(fc.country);
		country_label.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		country_label.setHorizontalAlignment(JLabel.CENTER);
        country_label.setForeground(new Color(0x000000));	
        country_label.setSize(500, 10);
        
        veracity_label.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel(); 
        panel1.add(country_label);
        panel1.setPreferredSize(new Dimension(400, 30));
        panel2.add(textField);
        panel2.setPreferredSize(new Dimension(400, 25));
        panel3.add(button);
        panel3.setPreferredSize(new Dimension(400, 25));
        panel4.add(veracity_label);
        panel4.setPreferredSize(new Dimension(400, 25));
        
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(4, 1));
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        
        frame.addWindowListener(this);
        frame.pack();
 
    }
	
	@Override
    public void windowClosing(WindowEvent e)
    {
        this.deck.closeDeck();
        e.getWindow().dispose();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.button || e.getSource() == this.textField){
			String answer = this.textField.getText();

			if (this.deck.isCapital(this.fc, answer)){
				this.veracity_label.setText("correct! "+String.valueOf(fc.weight));
			} else {
				String text_output = "incorrect: ";
				for (String capital: this.fc.capitals){
					text_output += (capital + " ");
				}
				text_output += String.valueOf(fc.weight);
				this.veracity_label.setText(text_output);
			}
			
			this.textField.setText("");
			this.fc = this.deck.sample();
			this.country_label.setText(fc.country);
		}
		
	}
	
}
