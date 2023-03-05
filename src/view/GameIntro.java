package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings({ "serial", "unused" })
public class GameIntro extends JFrame implements MouseListener {
	
	private ImgPanel background;
	private JLabel start;
	private JLabel exit;
	
	public JLabel getStart() {
		return start;
	}

	public JLabel getExit() {
		return exit;
	}


	public GameIntro() throws IOException {
		setTitle("Main Menu");
		setSize(1400, 787);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		background = new ImgPanel("City3.png");
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Rescue Simulation");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("alien encounters", Font.BOLD, 100));
		title.setForeground(Color.WHITE);
		
		ImageIcon Icon = new ImageIcon("red_button11.png");
		start = new JLabel("New Game");
		start.setFont(new Font("alien encounters", Font.BOLD, 30));
		start.setForeground(Color.YELLOW);
		start.setIcon(Icon);
		start.setHorizontalTextPosition(JLabel.CENTER);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		exit = new JLabel("Exit");
		exit.setFont(new Font("alien encounters", Font.BOLD, 30));
		exit.setForeground(Color.YELLOW);
		exit.setIcon(Icon);
		exit.setHorizontalTextPosition(JLabel.CENTER);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		background.add(Box.createRigidArea(new Dimension(0, 100)));
		background.add(title);
		background.add(Box.createRigidArea(new Dimension(0, 100)));
		background.add(start);
		background.add(Box.createRigidArea(new Dimension(0, 50)));
		background.add(exit);
		background.setSize(1400, 787);
		
		add(background);
		
		setVisible(true);
		
	}
	
	
	
	/*public static void main(String[]args) throws IOException {
		File music = new File("Oniku Loop2.WAV");
		GameIntro g = new GameIntro();
		playMusic(music);
	}*/


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
