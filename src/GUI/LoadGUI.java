package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadGUI extends JFrame implements ActionListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public LoadGUI() {
		setTitle("Neural Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"test", "lol", "neural", "network"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new TitledBorder(null, "TestList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(list);
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		contentPane.add(ButtonPanel, BorderLayout.SOUTH);
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		JButton LoadButton = new JButton("Load");
		LoadButton.setPreferredSize(new Dimension(150, 40));
		LoadButton.setActionCommand("Load");
		ButtonPanel.add(LoadButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(100);
		ButtonPanel.add(horizontalStrut);
		
		JButton CreateButton = new JButton("Create");
		CreateButton.addActionListener(this);
		CreateButton.setPreferredSize(new Dimension(150, 40));
		CreateButton.setActionCommand("Create");
		ButtonPanel.add(CreateButton);
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Create")){
			
		}
		if(e.getActionCommand().equals("Load")){
			
		}
	}
}
