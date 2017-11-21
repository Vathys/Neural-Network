package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import java.math.BigDecimal;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;

public class CreateGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JButton btnSetChanges;
	
	
	private String networkName;
	private int networkSize;
	private BigDecimal learningRate;
	/**
	 * Create the frame.
	 */
	public CreateGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							LoadGUI frame = new LoadGUI();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				close();
			}
		});
		setBounds(600, 100, 450, 300); //x , y , width, height
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Name", null, panel1, null);
		
		JPanel panel2 = makeTextPanel("Panel #2");
		tabbedPane.addTab("Layers", null, panel2, null);
		
		JPanel panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("LearningRate", null, panel3, null);
		
		JPanel panel4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel4.getLayout();
		flowLayout.setVgap(90);
		
		JButton btn = new JButton("Continue");
		
		btn.addActionListener(this);
		
		panel4.add(btn);
		tabbedPane.addTab("Continue", null, panel4, null);
	}

	protected JPanel makeTextPanel(String text) {
	    JPanel panel = new JPanel(false);
	    panel.setBackground(Color.LIGHT_GRAY);
	    panel.setLayout(new BorderLayout(0, 0));
	    
	    JPanel mainPanel = new JPanel();
	    mainPanel.setBorder(new EmptyBorder(40, 1, 20, 1));
	    mainPanel.setLayout(new BorderLayout(0, 0));
	    
	    panel.add(mainPanel, BorderLayout.CENTER);
	    
	    JPanel textPanel = new JPanel();
	    mainPanel.add(textPanel, BorderLayout.SOUTH);
	    
	    JLabel tabLabel;
	    
	    if(text.equals("Panel #1")){
	    	tabLabel = new JLabel("Name");
		}
	    else if(text.equals("Panel #2")){
			tabLabel = new JLabel("Number of Layers");
		}
	    else if(text.equals("Panel #3")){
			tabLabel = new JLabel("Learning Rate");
		}
	    else{
	    	tabLabel = new JLabel();
	    }
		
	    tabLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    mainPanel.add(tabLabel, BorderLayout.CENTER);
	    
	    JTextField txt = new JTextField("", 20);
	    textPanel.add(txt);
	    txt.setHorizontalAlignment(SwingConstants.CENTER);
	    txt.setBackground(Color.WHITE);

	    JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		

	    btnSetChanges = new JButton("Set Changes");
	    
	    btnSetChanges.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(text.equals("Panel #1")){
	    			Main.getFrame().insertElement(txt.getText()); //Adds the name typed in to the list on LoadGUI
	    			networkName = txt.getText();
	    		}
	    		if(text.equals("Panel #2")){
	    			networkSize = Integer.valueOf(txt.getText());
	    		}
	    		if(text.equals("Panel #3")){
	    			learningRate = BigDecimal.valueOf(Double.valueOf(txt.getText()));
	    		}
	    		tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
	    	}
	    });
		btnSetChanges.setPreferredSize(new Dimension(150, 40));
		ButtonPanel.add(btnSetChanges);
		
		panel.add(ButtonPanel, BorderLayout.SOUTH);
		
	    return panel;
	}
	
	protected void close(){ //When CreateGUI closes the neural network will stop
		Main.setCalculating(false);
		LoadGUI.getCreateButton().setEnabled(true); //allows new creation gui to be loaded
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LayersGUI frame = new LayersGUI(networkName, networkSize, learningRate);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

