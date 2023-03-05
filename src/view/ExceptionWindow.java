package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ExceptionWindow extends JFrame{
		
	public ExceptionWindow(String s) {
		setTitle("Exception");
		setSize(1300, 300);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(Color.DARK_GRAY);
		JLabel title = new JLabel(s);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("alien encounters", Font.BOLD, 30));
		title.setForeground(Color.YELLOW);
		panel.add(title);
		add(panel);
		setVisible(true);
	}
	
}
