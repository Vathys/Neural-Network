package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class MainGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/* This GUI will contain a tabbed pane again.
	 * GUI will be more for visually looking at all the different weights and neurons. 
	 * As such we will have different tabs that will be able to change important parts of the main network, such as bias values.
	 * and learning rate.
	 * And we will have one tab that displays the whole of neural network, as in the weights, biases, and feedforward information in an organized manner.
	 * */
	
	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnFeedForward = new JButton("Feed Forward");
		panel.add(btnFeedForward);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		panel.add(horizontalStrut);
		
		JButton btnBackPropogation = new JButton("Back Propogation");
		panel.add(btnBackPropogation);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
