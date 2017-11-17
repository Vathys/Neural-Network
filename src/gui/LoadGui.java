package gui;

import javax.swing.*;
import java.awt.*;

public class LoadGui {
	
	
	public LoadGui(String frameName){
		JFrame frame = new JFrame(frameName);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JLabel label = new JLabel();
		label.setSize(500,100);
		
		final DefaultListModel<String> l1 = new DefaultListModel<>();
		l1.addElement("asdf");
		
		JList<String> list = new JList<>();
		list.setModel(l1);
		list.setBounds(frame.getWidth()/2 - 50, frame.getHeight()/2 - 50, 100, 100);
		frame.add(list);
		frame.setLayout(null);
		frame.setVisible(true);
	}

}
