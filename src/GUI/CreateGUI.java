package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import network.main.Main;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSetChanges;
	/**
	 * Create the frame.
	 */
	public CreateGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				close();
			}
		});
		setBounds(600, 100, 450, 300); //x , y , width, height
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JComponent panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Name", panel1);

		JComponent panel2 = makeTextPanel("Panel #2");
		tabbedPane.addTab("Iterations", panel2);

		JComponent panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("Start/Stop", panel3);
		
		JComponent panel4 = makeTextPanel("Panel #4");
		tabbedPane.addTab("StopValue", panel4);
		
		JComponent panel5 = makeTextPanel("Panel #5");
		tabbedPane.addTab("LearningRate", panel5);
	}

	protected JComponent makeTextPanel(String text) {
	    JPanel panel = new JPanel(false);
	    panel.setBackground(Color.LIGHT_GRAY);
	    panel.setLayout(null);
	    
	    if(text.equals("Panel #3")){ //Loads the Stop, Start, Pause buttons for only this panel
		    JButton btnStop = new JButton("RESTART/STOP");
		    JButton btnPause = new JButton("PAUSE");
		    JButton btnStart = new JButton("START");
		    btnStart.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		Main.setPaused(false);
		    		btnStart.setEnabled(false); //determines which button is clickable
		    		btnPause.setEnabled(true);
		    		btnStop.setEnabled(true);
		    	}
		    });
		    btnStart.setBounds(120, 15, 200, 50); //x , y , width, height
		    panel.add(btnStart);
		    
		    btnPause.addActionListener(new ActionListener() { //Pauses the Neural Network
		    	public void actionPerformed(ActionEvent e) {
		    		Main.setPaused(true);
		    		btnPause.setEnabled(false); //determines which button is clickable
		    		btnStart.setEnabled(true);
		    		btnStop.setEnabled(true);
		    	}
		    });
		    btnPause.setEnabled(false);
		    btnPause.setBounds(120, 85, 200, 50); //x , y , width, height
		    panel.add(btnPause);
		    
		    btnStop.addActionListener(new ActionListener() { //Stops the Neural Network
		    	public void actionPerformed(ActionEvent e) {
		    		Main.setCalculating(false);
		    		btnPause.setEnabled(false); //determines which button is clickable
		    		btnStart.setEnabled(true);
		    		btnStop.setEnabled(false);
		    	}
		    });
		    btnStop.setEnabled(false);
		    btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    btnStop.setBounds(120, 155, 200, 50); //x , y , width, height
		    panel.add(btnStop);
	    }
	    else{ //All other panels unless specified will be like this
	    
		    btnSetChanges = new JButton("set changes");
		    
		    JTextField txt = new JTextField("", 20);
		    txt.setBounds(131, 76, 158, 74); //x , y , width, height
		    txt.setHorizontalAlignment(SwingConstants.CENTER);
		    txt.setBackground(Color.WHITE);
		    panel.add(txt);
		    txt.setColumns(10);
		    
		   
		    //Modifies the certain variable within the neural network
		    btnSetChanges.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if(text.equals("Panel #1")){
		    			Main.getFrame().insertElement(txt.getText()); //Adds the name typed in to the list on LoadGUI
		    		}
		    		if(text.equals("Panel #2")){
		    			Main.setNumberOfIterations(Integer.valueOf(txt.getText())); //modifies the Number of Iterations
		    		}
		    		if(text.equals("Panel #3")){
		    			//unused
		    		}
		    		if(text.equals("Panel #4")){
		    			Main.setStopValue(Double.parseDouble(txt.getText())); //modifies the Stop Value
		    		}
		    		if(text.equals("Panel #5")){
		    			Main.setLearningRate(Double.parseDouble(txt.getText())); //modifies the Learning Rate
		    		}
		    		
		    		//Main.setLearningRate(.005);
		    	}
		    });
		    btnSetChanges.setBounds(146, 189, 127, 23); //x , y , width, height
		    panel.add(btnSetChanges);

	    
	    }
	    return panel;
	}
	
	protected void close(){ //When CreateGUI closes the neural network will stop
		Main.setCalculating(false);
		LoadGUI.getCreateButton().setEnabled(true); //allows new creation gui to be loaded
	}
	
	
}

