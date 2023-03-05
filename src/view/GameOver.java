package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOver extends JFrame{
		
	public GameOver(String casualties , String cycles) {
		setTitle("Game Over");
		setSize(800, 300);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.DARK_GRAY);
		JLabel title = new JLabel("GAME OVER");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("alien encounters", Font.BOLD, 30));
		title.setForeground(Color.YELLOW);
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 70)));
		JLabel title1 = new JLabel("CASUALTIES : " + casualties);
		title1.setAlignmentX(Component.CENTER_ALIGNMENT);
		title1.setFont(new Font("alien encounters", Font.BOLD, 30));
		title1.setForeground(Color.YELLOW);
		panel.add(title1);
		panel.add(Box.createRigidArea(new Dimension(0, 70)));
		JLabel title11 = new JLabel("CYCLES : " + cycles);
		title11.setAlignmentX(Component.CENTER_ALIGNMENT);
		title11.setFont(new Font("alien encounters", Font.BOLD, 30));
		title11.setForeground(Color.YELLOW);
		panel.add(title11);
		add(panel);
		setVisible(true);
	}
	
}

