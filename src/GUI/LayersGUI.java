package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
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
	private JButton btnSetLayerSize;
	private int[] layer;
	private String name;
	private int networkSize;
	private BigDecimal learningRate;
	
	public LayersGUI(String name, int networkSize, BigDecimal learningRate) {
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel genPanel;
		
		for(int i = 0; i < networkSize; i++){
			
			genPanel = makeTextPanel("Panel #" + (i + 1));
			
			if(i == 0){
				tabbedPane.addTab("Input", null, genPanel, null);
			}
			else if(i == networkSize - 1){
				tabbedPane.addTab("Output", null, genPanel, null);
			}
			else{
				tabbedPane.addTab("Layer " + i, null, genPanel, null);
			}
			
		}
		
		layer = new int [networkSize];
		
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
	    
	    JTextField txt = new JTextField("", 20);
	    textPanel.add(txt);
	    txt.setHorizontalAlignment(SwingConstants.CENTER);
	    txt.setBackground(Color.WHITE);

	    JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		

	    btnSetLayerSize = new JButton("Set Layer Size");
	    
	    btnSetLayerSize.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		for(int i = 0; i < networkSize; i++){
	    			if(text.equals("Panel #" + (i + 1))){
	    				layer[i] = Integer.valueOf(txt.getText());
	    			}
	    		}
	    		tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
	    	}
	    });
	    Main.setLayerSize(layer);
		btnSetLayerSize.setPreferredSize(new Dimension(150, 40));
		ButtonPanel.add(btnSetLayerSize);
		
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
