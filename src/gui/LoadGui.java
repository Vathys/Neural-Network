package gui;

import javax.swing.*;
import java.awt.*;

public class LoadGui {
	
	
	public LoadGui(String frameName){
		JFrame frame = new JFrame(frameName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setPreferredSize(new Dimension(500,500));
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		
		String[] data = {"test", "Deeps Fault", "dab"};
		JList list = new JList(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		frame.getContentPane().add(listScroller, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}
	

}
