package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class LoadGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private ArrayList<String> values = new ArrayList<String>();

	private JList<String> list = new JList<String>();
	

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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
		list.setBorder(new TitledBorder(null, "TestList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(list);
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setBorder(new EmptyBorder(25, 10, 40, 25));
		contentPane.add(ButtonPanel, BorderLayout.SOUTH);
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
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
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		if(e.getActionCommand().equals("Load")){
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
	
	
	
	
}
