package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import network.main.Main;
import network.main.NeuralNetwork;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;

public class LoadGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> values = new ArrayList<String>();

	private static JList<String> list = new JList<String>();
	

	private static JButton CreateButton = new JButton("Create");
	private static JButton LoadButton = new JButton("Load");
	/**
	 * Create the frame.
	 */
	public LoadGUI() {
		setTitle("Neural Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		File folder = new File("Saved NN");
		File[] listOfFiles = folder.listFiles();
		
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].exists() && !listOfFiles[i].getName().equals(".DS_Store")){
				values.add(listOfFiles[i].getName());
			}
		}
		ArrayList<String> temp = new ArrayList<String>();
		for(int j = 0; j < values.size(); j++){
			if(!(values.get(j).length() > 9 && values.get(j).substring(0,9).equals("New File "))){ 
				temp.add(values.get(j));
				values.remove(j);
				j--;
			}
		}
		int n = 0;
		while(values.size() > 0){
			int initSize = values.size();
			for(int j = 0; j < values.size(); j++){
				if(values.get(j).substring(9).equals(String.valueOf(n))){
					temp.add(values.get(j));
					values.remove(j);
					j = 0;
				}
			}
			if(initSize >= values.size()){
				n++;
			}
		}
		values = temp;
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
		
		list.setBounds(5, 7, 474, 335);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.add(new JScrollBar(JScrollBar.VERTICAL));
		scrollPane.add(new JScrollBar(JScrollBar.HORIZONTAL));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBounds(5, 342, 474, 115);
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		contentPane.add(ButtonPanel, BorderLayout.SOUTH);
		
		LoadButton.addActionListener(this);
		LoadButton.setPreferredSize(new Dimension(150, 40));
		LoadButton.setActionCommand("Load");
		ButtonPanel.add(LoadButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(100);
		ButtonPanel.add(horizontalStrut);
		
		CreateButton.addActionListener(this);
		CreateButton.setPreferredSize(new Dimension(150, 40));
		CreateButton.setActionCommand("Create");
		ButtonPanel.add(CreateButton);
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Create")){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CreateButton.setEnabled(false); // Disables the create Button when a CreateGUI is already open
						CreateGUI frame = new CreateGUI();
						frame.setVisible(true);
						Main.frame.setVisible(false);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		if(e.getActionCommand().equals("Load")){
			list.getSelectedValue();
			try {
				NeuralNetwork n = new NeuralNetwork(new File("Saved NN/" + list.getSelectedValue()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void insertElement(String value){
		values.add(value);
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
	}

	public static JButton getCreateButton() {
		return CreateButton;
	}

	public static JButton getLoadButton() {
		return LoadButton;
	}

	public static JList<String> getList() {
		return list;
	}
	
	
}
