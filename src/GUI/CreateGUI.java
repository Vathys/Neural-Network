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
import javax.swing.KeyStroke;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;

public class CreateGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JButton btnSetChanges;
	private JButton btn;
	
	private String networkName;
	private int networkSize;
	private BigDecimal learningRate;
	private JTextField[] txt = new JTextField[3];
	/**
	 * Create the frame.
	 */
	public CreateGUI() {
		Action action = new AbstractAction("Set Changes"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1)
					btn.doClick();
				else
					btnSetChanges.doClick();
			}
		};
		KeyStroke keyStroke;
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
			
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
		
		contentPane.getActionMap().put("Set Changes", action);
		contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "Set Changes");
		
		for(int i = 0; i < txt.length; i++){
			txt[i] = new JTextField("", 20);
		    txt[i].setHorizontalAlignment(SwingConstants.CENTER);
		    txt[i].setBackground(Color.WHITE);
		}
		
		JPanel panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Name", null, panel1, null);
		
		JPanel panel2 = makeTextPanel("Panel #2");
		tabbedPane.addTab("Layers", null, panel2, null);
		
		JPanel panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("LearningRate", null, panel3, null);
		
		JPanel panel4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel4.getLayout();
		flowLayout.setVgap(90);
		
		btn = new JButton("Continue");
		
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
	    	int n = 1;
	    	for(int i = 0; i < LoadGUI.getList().getModel().getSize(); i++){
	    		if(LoadGUI.getList().getModel().getElementAt(i).length() > 9 && ((LoadGUI.getList().getModel().getElementAt(i)).substring(0,9)).equals("New File ")){
	    			if(LoadGUI.getList().getModel().getElementAt(i).substring(9).equals(String.valueOf(n))){
	    				n++;
	    			}
	    		}
	    	}
	    	txt[0].setText("New File " + String.valueOf(n));
		    textPanel.add(txt[0]);
		}
	    else if(text.equals("Panel #2")){
			tabLabel = new JLabel("Number of Layers");
	    	txt[1].setText("4");
		    textPanel.add(txt[1]);
		}
	    else if(text.equals("Panel #3")){
			tabLabel = new JLabel("Learning Rate");
	    	txt[2].setText(".05");
		    textPanel.add(txt[2]);
		}
	    else{
	    	tabLabel = new JLabel();
	    }
		
	    tabLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    mainPanel.add(tabLabel, BorderLayout.CENTER);
	    

	    JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		

	    btnSetChanges = new JButton("Set Changes");
	    
	    btnSetChanges.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(tabbedPane.getSelectedIndex() == 0){
	    			Main.getFrame().insertElement(txt[tabbedPane.getSelectedIndex()].getText()); //Adds the name typed in to the list on LoadGUI
	    			networkName = txt[tabbedPane.getSelectedIndex()].getText();
	    		}
	    		if(tabbedPane.getSelectedIndex() == 1){
	    			networkSize = Integer.valueOf(txt[tabbedPane.getSelectedIndex()].getText());
	    		}
	    		if(tabbedPane.getSelectedIndex() == 2){
	    			learningRate = BigDecimal.valueOf(Double.valueOf(txt[tabbedPane.getSelectedIndex()].getText()));
	    		}
	    		if(tabbedPane.getSelectedIndex() < 3){
	    			tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
	    		}
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

