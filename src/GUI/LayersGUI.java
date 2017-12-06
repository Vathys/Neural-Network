package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import network.main.Main;
import network.main.NeuralNetwork;

public class LayersGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel contentPane;
	private JButton[] btnSetLayerSize;
	private static int[] layer;
	private String name;
	private int networkSize;
	private BigDecimal learningRate;
	private JButton btn;
	private JTextField[] txt;
	private String[][] defaultValue;
	private JLabel[] tabLabel;
	
	public LayersGUI(String name, int networkSize, BigDecimal learningRate) {
		Action action = new AbstractAction("Set Layer Size"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1)
					btn.doClick();
				else
					btnSetLayerSize[tabbedPane.getSelectedIndex()].doClick();
			}
		};
		KeyStroke keyStroke;
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		
		this.name = name;
		this.networkSize = networkSize;
		this.learningRate = learningRate;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Main.frame.setVisible(true);
				close();
			}
		});
		setBounds(600, 100, 450, 300); //x , y , width, height
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		contentPane.getActionMap().put("Set Layer Size", action);
		contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "Set Layer Size");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		
		txt = new JTextField[networkSize];
		btnSetLayerSize = new JButton[networkSize];
		
		for(int i = 0; i < txt.length; i++){
			txt[i] = new JTextField("", 20);
		    txt[i].setHorizontalAlignment(SwingConstants.CENTER);
		    txt[i].setBackground(Color.WHITE);
		}
		
		defaultValue = new String[networkSize][2];
		tabLabel = new JLabel[networkSize];
		for(int i = 0; i < networkSize; i++){
			
			if(i == 0){
				defaultValue[i][0] = "2";
				defaultValue[i][1] = "Input";
				tabbedPane.addTab(defaultValue[i][1], null, makeTextPanel("Panel #" + (i + 1)), null);
			}
			else if(i == networkSize - 1){
				defaultValue[i][0] = "2";
				defaultValue[i][1] = "Output";
				tabbedPane.addTab(defaultValue[i][1], null, makeTextPanel("Panel #" + (i + 1)), null);
			}
			else{
				defaultValue[i][0] = "2";
				defaultValue[i][1] = "Layer " + String.valueOf(i);
				tabbedPane.addTab("Layer " + i, null, makeTextPanel("Panel #" + (i + 1)), null);
			}
			
		}
		
		layer = new int [networkSize];
		
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
	    
	    JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
	    
	    for(int i = 0; i < networkSize; i++){
	    	if(text.equals("Panel #" + (i + 1))){
		    	tabLabel[i] = new JLabel(defaultValue[i][1]);
		    	txt[i].setText(defaultValue[i][0]);
			    textPanel.add(txt[i]);
			    tabLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			    mainPanel.add(tabLabel[i], BorderLayout.CENTER);
			    btnSetLayerSize[i] = new JButton("Set Layer Size"); 
			    btnSetLayerSize[i].addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		for(int i = 0; i < networkSize; i++){
			    			if(text.equals("Panel #" + (i + 1))){
			    				layer[i] = Integer.valueOf(txt[i].getText());
			    			}
			    		}
			    		tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
			    	}
			    });
			    btnSetLayerSize[i].setPreferredSize(new Dimension(150, 40));
				ButtonPanel.add(btnSetLayerSize[i]);
			}
	    }

	    mainPanel.add(textPanel, BorderLayout.SOUTH);
	    
		panel.add(ButtonPanel, BorderLayout.SOUTH);
		
	    return panel;
	}
	
	protected void close(){ //When CreateGUI closes the neural network will stop
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NeuralNetwork nn = new NeuralNetwork(layer, learningRate);
		
		nn.initFile(name);
		
		this.setVisible(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
