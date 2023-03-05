package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.people.Citizen;
import simulation.Address;

@SuppressWarnings({ "serial", "unused" })
public class SimulationGame extends JFrame implements MouseListener {
	
	private JPanel background;
	private JButton[][] grid;
	private JTextArea log;
	private JTextArea info;
	private JLabel casualitiesNum;
	private JLabel currentCycleNum;
	private JLabel endCycle;
	private JLabel respond;
	private JLabel back;
	private JComboBox<Citizen> box;
	private JButton resAMB;
	private JButton resDCU;
	private JButton resFTK;
	private JButton resGCU;
	private JButton resEVC;
	private JButton avAMB;
	private JButton avDCU;
	private JButton avFTK;
	private JButton avGCU;
	private JButton avEVC;
	private JButton treatingAMB;
	private JButton treatingDCU;
	private JButton treatingFTK;
	private JButton treatingGCU;
	private JButton treatingEVC;
	private JLabel showInfo;
	private JLabel unitSelected;
	private JLabel targetSelected;
	
	public void updateLog(String text) {
		log.setText(text);
	}
	
	public void updateInfo(String text) {
		info.setText(text);
	}
	
	public JComboBox<Citizen> getBox() {
		return box;
	}

	public JLabel getBack() {
		return back;
	}

	public JButton getResAMB() {
		return resAMB;
	}

	public JButton getResDCU() {
		return resDCU;
	}

	public JButton getResFTK() {
		return resFTK;
	}

	public JButton getResGCU() {
		return resGCU;
	}

	public JButton getResEVC() {
		return resEVC;
	}

	public JButton getAvAMB() {
		return avAMB;
	}

	public JButton getAvDCU() {
		return avDCU;
	}

	public JButton getAvFTK() {
		return avFTK;
	}

	public JButton getAvGCU() {
		return avGCU;
	}

	public JButton getAvEVC() {
		return avEVC;
	}

	public JButton getTreatingAMB() {
		return treatingAMB;
	}

	public JButton getTreatingDCU() {
		return treatingDCU;
	}

	public JButton getTreatingFTK() {
		return treatingFTK;
	}

	public JButton getTreatingGCU() {
		return treatingGCU;
	}

	public JButton getTreatingEVC() {
		return treatingEVC;
	}

	public JLabel getCurrentCycleNum() {
		return currentCycleNum;
	}

	public JLabel getCasualitiesNum() {
		return casualitiesNum;
	}

	public JButton[][] getGrid() {
		return grid;
	}

	public JLabel getEndCycle() {
		return endCycle;
	}
	
	public JLabel getRespond() {
		return respond;
	}

	public JLabel getUnitSelected() {
		return unitSelected;
	}

	public JLabel getTargetSelected() {
		return targetSelected;
	}

	public JLabel getShowInfo() {
		return showInfo;
	}

	public SimulationGame() {
		setTitle("Command Center");
		setSize(1400, 788);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel theGrid = new JPanel();
		theGrid.setLayout(new GridLayout(10,10));
		grid = new JButton[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = new JButton();
	            grid[i][j].setBackground(Color.ORANGE);
	            grid[i][j].setContentAreaFilled(false);
	            grid[i][j].setOpaque(true);
				theGrid.add(grid[i][j]);
			}
		}
		
		JPanel left = new JPanel();
		left.setBackground(Color.DARK_GRAY);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("LOG");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("alien encounters", Font.BOLD, 50));
		title.setForeground(Color.YELLOW);
		left.add(Box.createRigidArea(new Dimension(0, 30)));
		left.add(title);
		log = new JTextArea();
		log.setEditable(false);
		log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		log.setForeground(Color.RED);
		left.add(Box.createRigidArea(new Dimension(0, 10)));
		JScrollPane sp = new JScrollPane(log,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300,200));
		left.add(sp);
		JLabel title1 = new JLabel("INFO");
		title1.setAlignmentX(Component.CENTER_ALIGNMENT);
		title1.setFont(new Font("alien encounters", Font.BOLD, 50));
		title1.setForeground(Color.YELLOW);
		left.add(Box.createRigidArea(new Dimension(0, 40)));
		left.add(title1);
		info = new JTextArea();
		info.setEditable(false);
		info.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		info.setForeground(Color.BLUE);
		left.add(Box.createRigidArea(new Dimension(0, 10)));
		JScrollPane sp1 = new JScrollPane(info,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300,200));
		left.add(sp1);
		
		JPanel right = new JPanel();
		right.setBackground(Color.DARK_GRAY);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		box = new JComboBox<Citizen>();
		box.setBackground(Color.WHITE);
		right.add(box);
		right.add(Box.createRigidArea(new Dimension(0, 20)));
		ImageIcon Icon23 = new ImageIcon("red_button11.png");
		showInfo = new JLabel("SELECT CITIZEN");
		showInfo.setFont(new Font("alien encounters", Font.BOLD, 20));
		showInfo.setForeground(Color.YELLOW);
		showInfo.setIcon(Icon23);
		showInfo.setHorizontalTextPosition(JLabel.CENTER);
		showInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		right.add(showInfo);
		right.add(Box.createRigidArea(new Dimension(0, 20)));
		ImageIcon Icon11 = new ImageIcon("green_button00.png");
		respond = new JLabel("RESPOND");
		respond.setFont(new Font("alien encounters", Font.BOLD, 30));
		respond.setForeground(Color.WHITE);
		respond.setIcon(Icon11);
		respond.setHorizontalTextPosition(JLabel.CENTER);
		respond.setAlignmentX(Component.CENTER_ALIGNMENT);
		right.add(respond);
		right.add(Box.createRigidArea(new Dimension(0, 20)));
		JLabel title11 = new JLabel("Available Units");
		title11.setAlignmentX(Component.CENTER_ALIGNMENT);
		title11.setFont(new Font("alien encounters", Font.BOLD, 20));
		title11.setForeground(Color.YELLOW);
		right.add(title11);
		right.add(Box.createRigidArea(new Dimension(0, 10)));
		JPanel availableUnits = new JPanel();
		availableUnits.setBackground(Color.DARK_GRAY);
		availableUnits.setLayout(new GridLayout(0,3));
		avAMB = new JButton("AMB x0");
		avAMB.setBackground(Color.YELLOW);
		avAMB.setContentAreaFilled(false);
		avAMB.setOpaque(true);
		avDCU = new JButton("DCU x0");
		avDCU.setBackground(Color.YELLOW);
		avDCU.setContentAreaFilled(false);
		avDCU.setOpaque(true);
		avEVC = new JButton("EVC x0");
		avEVC.setBackground(Color.YELLOW);
		avEVC.setContentAreaFilled(false);
		avEVC.setOpaque(true);
		avFTK = new JButton("FTK x0");
		avFTK.setBackground(Color.YELLOW);
		avFTK.setContentAreaFilled(false);
		avFTK.setOpaque(true);
		avGCU = new JButton("GCU x0");
		avGCU.setBackground(Color.YELLOW);
		avGCU.setContentAreaFilled(false);
		avGCU.setOpaque(true);
		availableUnits.add(avAMB);
		availableUnits.add(avDCU);
		availableUnits.add(avEVC);
		availableUnits.add(avFTK);
		availableUnits.add(avGCU);
		right.add(availableUnits);
		right.add(Box.createRigidArea(new Dimension(0, 30)));
		JLabel title111 = new JLabel("Responding Units");
		title111.setAlignmentX(Component.CENTER_ALIGNMENT);
		title111.setFont(new Font("alien encounters", Font.BOLD, 20));
		title111.setForeground(Color.YELLOW);
		right.add(title111);
		right.add(Box.createRigidArea(new Dimension(0, 10)));
		JPanel respondingUnits = new JPanel();
		respondingUnits.setBackground(Color.DARK_GRAY);
		respondingUnits.setLayout(new GridLayout(0,3));
		resAMB = new JButton();
		resAMB.setBackground(Color.GREEN);
		resAMB.setContentAreaFilled(false);
		resAMB.setOpaque(true);
		resDCU = new JButton();
		resDCU.setBackground(Color.GREEN);
		resDCU.setContentAreaFilled(false);
		resDCU.setOpaque(true);
		resEVC = new JButton();
		resEVC.setBackground(Color.GREEN);
		resEVC.setContentAreaFilled(false);
		resEVC.setOpaque(true);
		resFTK = new JButton();
		resFTK.setBackground(Color.GREEN);
		resFTK.setContentAreaFilled(false);
		resFTK.setOpaque(true);
		resGCU = new JButton();
		resGCU.setBackground(Color.GREEN);
		resGCU.setContentAreaFilled(false);
		resGCU.setOpaque(true);
		respondingUnits.add(resAMB);
		respondingUnits.add(resDCU);
		respondingUnits.add(resEVC);
		respondingUnits.add(resFTK);
		respondingUnits.add(resGCU);
		right.add(respondingUnits);
		right.add(Box.createRigidArea(new Dimension(0, 30)));
		JLabel title1111 = new JLabel("Treating Units");
		title1111.setAlignmentX(Component.CENTER_ALIGNMENT);
		title1111.setFont(new Font("alien encounters", Font.BOLD, 20));
		title1111.setForeground(Color.YELLOW);
		right.add(title1111);
		right.add(Box.createRigidArea(new Dimension(0, 10)));
		JPanel treatingUnits = new JPanel();
		treatingUnits.setBackground(Color.DARK_GRAY);
		treatingUnits.setLayout(new GridLayout(0,3));
		treatingAMB = new JButton("AMB x0");
		treatingAMB.setBackground(Color.RED);
		treatingAMB.setContentAreaFilled(false);
		treatingAMB.setOpaque(true);
		treatingDCU = new JButton("DCU x0");
		treatingDCU.setBackground(Color.RED);
		treatingDCU.setContentAreaFilled(false);
		treatingDCU.setOpaque(true);
		treatingEVC = new JButton("EVC x0");
		treatingEVC.setBackground(Color.RED);
		treatingEVC.setContentAreaFilled(false);
		treatingEVC.setOpaque(true);
		treatingFTK = new JButton("FTK x0");
		treatingFTK.setBackground(Color.RED);
		treatingFTK.setContentAreaFilled(false);
		treatingFTK.setOpaque(true);
		treatingGCU = new JButton("GCU x0");
		treatingGCU.setBackground(Color.RED);
		treatingGCU.setContentAreaFilled(false);
		treatingGCU.setOpaque(true);
		treatingUnits.add(treatingAMB);
		treatingUnits.add(treatingDCU);
		treatingUnits.add(treatingEVC);
		treatingUnits.add(treatingFTK);
		treatingUnits.add(treatingGCU);
		right.add(treatingUnits);
		
		JPanel north = new JPanel();
		north.setBackground(Color.DARK_GRAY);
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		ImageIcon iconn = new ImageIcon("iconfinder_MB__back_81161.png");
		back = new JLabel(iconn);
		back.setAlignmentY(Component.CENTER_ALIGNMENT);
		north.add(back);
		JLabel casualities = new JLabel("CASUALTIES :");
		casualities.setAlignmentY(Component.CENTER_ALIGNMENT);
		casualities.setFont(new Font("alien encounters", Font.BOLD, 35));
		casualities.setForeground(Color.YELLOW);
		north.add(Box.createRigidArea(new Dimension(100, 0)));
		north.add(casualities);
		casualitiesNum = new JLabel("0");
		casualitiesNum.setAlignmentY(Component.CENTER_ALIGNMENT);
		casualitiesNum.setFont(new Font("alien encounters", Font.BOLD, 35));
		casualitiesNum.setForeground(Color.YELLOW);
		north.add(Box.createRigidArea(new Dimension(10, 0)));
		north.add(casualitiesNum);
		JLabel currentCycle = new JLabel("CURRENT CYCLE :");
		currentCycle.setAlignmentY(Component.CENTER_ALIGNMENT);
		currentCycle.setFont(new Font("alien encounters", Font.BOLD, 35));
		currentCycle.setForeground(Color.YELLOW);
		north.add(Box.createRigidArea(new Dimension(50, 0)));
		north.add(currentCycle);
		currentCycleNum = new JLabel("0");
		currentCycleNum.setAlignmentY(Component.CENTER_ALIGNMENT);
		currentCycleNum.setFont(new Font("alien encounters", Font.BOLD, 35));
		currentCycleNum.setForeground(Color.YELLOW);
		north.add(Box.createRigidArea(new Dimension(10, 0)));
		north.add(currentCycleNum);
		
		JPanel itemsSelected = new JPanel();
		itemsSelected.setBackground(Color.DARK_GRAY);
		itemsSelected.setLayout(new BorderLayout());
		JPanel unit = new JPanel();
		unit.setBackground(Color.DARK_GRAY);
		unit.setLayout(new BoxLayout(unit, BoxLayout.X_AXIS));
		JLabel unitt = new JLabel("UNIT :");
		unitt.setAlignmentY(Component.CENTER_ALIGNMENT);
		unitt.setFont(new Font("alien encounters", Font.BOLD, 20));
		unitt.setForeground(Color.YELLOW);
		//unit.add(Box.createRigidArea(new Dimension(10, 0)));
		unit.add(unitt);
		unitSelected = new JLabel("EMPTY");
		unitSelected.setAlignmentY(Component.CENTER_ALIGNMENT);
		unitSelected.setFont(new Font("alien encounters", Font.BOLD, 20));
		unitSelected.setForeground(Color.YELLOW);
		unit.add(Box.createRigidArea(new Dimension(10, 0)));
		unit.add(unitSelected);
		JPanel target = new JPanel();
		target.setBackground(Color.DARK_GRAY);
		target.setLayout(new BoxLayout(target, BoxLayout.X_AXIS));
		JLabel targett = new JLabel("TARGET :");
		targett.setAlignmentY(Component.CENTER_ALIGNMENT);
		targett.setFont(new Font("alien encounters", Font.BOLD, 20));
		targett.setForeground(Color.YELLOW);
		target.add(targett);
		targetSelected= new JLabel("EMPTY");
		targetSelected.setAlignmentY(Component.CENTER_ALIGNMENT);
		targetSelected.setFont(new Font("alien encounters", Font.BOLD, 20));
		targetSelected.setForeground(Color.YELLOW);
		target.add(Box.createRigidArea(new Dimension(10, 0)));
		target.add(targetSelected);
		itemsSelected.add(unit,BorderLayout.NORTH);
		itemsSelected.add(target,BorderLayout.SOUTH);
		north.add(Box.createRigidArea(new Dimension(70, 0)));
		north.add(itemsSelected);
		
		ImageIcon Icon1 = new ImageIcon("red_button11.png");
		endCycle = new JLabel("END CYCLE");
		endCycle.setFont(new Font("alien encounters", Font.BOLD, 30));
		endCycle.setForeground(Color.YELLOW);
		endCycle.setIcon(Icon1);
		endCycle.setHorizontalTextPosition(JLabel.CENTER);
		north.add(Box.createRigidArea(new Dimension(30, 0)));
		north.add(endCycle);
		
		
		
		background = new ImgPanel("City2.png");
		background.setLayout(new BorderLayout(100,70));
		background.add(north,BorderLayout.NORTH);
		background.add(theGrid);
		background.add(left,BorderLayout.WEST);
		background.add(right,BorderLayout.EAST);
		
		add(background);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*public static void main(String[]args) {
		SimulationGame sim = new SimulationGame();
	}*/

	
	
	
}
