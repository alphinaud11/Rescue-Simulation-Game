package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.Chromaticity;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import exceptions.SimulationException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.ExceptionWindow;
import view.GameIntro;
import view.GameOver;
import view.SimulationGame;

@SuppressWarnings("unused")
public class CommandCenter implements SOSListener,MouseListener,ActionListener {

	private Simulator engine;
	private GameIntro intro;
	private SimulationGame game;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<ResidentialBuilding> visibleBuildingsCopied;
	private ArrayList<Citizen> visibleCitizensCopied;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Citizen> citizensInTrouble;
	private String log = "";
	private Boolean buildingChecked = false;
	private Unit selectedUnit;
	private Rescuable selectedTarget;
	@SuppressWarnings("unused")
	private ExceptionWindow expWindow;
	private GameOver gameOverWindow;
	private ArrayList<Citizen> occupantsCopied;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		intro = new GameIntro();
		intro.getStart().addMouseListener(this);
		intro.getExit().addMouseListener(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		visibleBuildingsCopied = new ArrayList<ResidentialBuilding>();
		visibleCitizensCopied = new ArrayList<Citizen>();
		citizensInTrouble = new ArrayList<Citizen>();
		occupantsCopied = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();

	}
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("unused")
		CommandCenter command = new CommandCenter();
		File music = new File("Oniku Loop2.WAV");
		playMusic(music);
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	
	public void refresh() {
		String cycle = Integer.toString(Integer.parseInt(game.getCurrentCycleNum().getText()));
		log = log + "Cycle : " + cycle + "\n";
		ImageIcon building = new ImageIcon("iconfinder_skyscraper-01_1989012.png");
		ImageIcon man = new ImageIcon("iconfinder_basicman05_1934254.png");
		ImageIcon skull = new ImageIcon("iconfinder_skull_744557.png");
		ArrayList<ResidentialBuilding> buildingsRemoved = new ArrayList<ResidentialBuilding>();
		ArrayList<Citizen> citizensRemoved = new ArrayList<Citizen>();
		ArrayList<Citizen> citizensRemoved1 = new ArrayList<Citizen>();
		ArrayList<Citizen> citizensRemoved11 = new ArrayList<Citizen>();
		for(ResidentialBuilding rb : visibleBuildings) {
			Boolean flag = true;
			for(Citizen c : rb.getOccupants()) {
				for(ResidentialBuilding rb1 : visibleBuildingsCopied) {
					int x1 = c.getLocation().getX();
					int y1 = c.getLocation().getY();
					int x11 = rb1.getLocation().getX();
					int y11 = rb1.getLocation().getY();
					if((x1==x11) && (y1==y11))
						flag = false;
				}
				if(flag)
					occupantsCopied.add(c);
			}
			boolean flag1 = true;
			for(ResidentialBuilding rb1 : visibleBuildingsCopied) {
				int x1 = rb.getLocation().getX();
				int y1 = rb.getLocation().getY();
				int x11 = rb1.getLocation().getX();
				int y11 = rb1.getLocation().getY();
				if((x1==x11) && (y1==y11))
					flag = false;
			}
			if(flag)
				visibleBuildingsCopied.add(rb);
			buildingsRemoved.add(rb);
			int x = rb.getLocation().getX();
			int y = rb.getLocation().getY();
			game.getGrid()[x][y].setIcon(building);
			Disaster disaster = rb.getDisaster();
			if(disaster instanceof Collapse)
				log = log + "Building at (" + x + "," + y + ") struck by collapse\n";
			else if(disaster instanceof Fire)
				log = log + "Building at (" + x + "," + y + ") struck by fire\n";
			else if(disaster instanceof GasLeak)
				log = log + "Building at (" + x + "," + y + ") struck by gasleak\n";
			
		}
		for(Citizen c : visibleCitizens) {
			visibleCitizensCopied.add(c);
			citizensRemoved.add(c);
			citizensInTrouble.add(c);
			int x = c.getLocation().getX();
			int y = c.getLocation().getY();
			game.getGrid()[x][y].setIcon(man);
			Disaster disaster = c.getDisaster();
			if(disaster instanceof Injury)
				log = log + "Citizen " + c.getName() + " at (" + x + "," + y + ") is injured\n";
			else if(disaster instanceof Infection)
				log = log + "Citizen " + c.getName() + " at (" + x + "," + y + ") is infected\n";
		}
		
		for(Citizen c : citizensInTrouble) { 
			int x = c.getLocation().getX();
		    int y = c.getLocation().getY();
			if(c.getState() == CitizenState.DECEASED) {
				game.getGrid()[x][y].setIcon(skull);
				log = log + "Citizen " + c.getName() + " at (" + x + "," + y + ") died\n";
				citizensRemoved1.add(c);
			}
		}
		
		for(Citizen c : occupantsCopied) {
			if(c.getState() == CitizenState.DECEASED) {
				int x = c.getLocation().getX();
				int y = c.getLocation().getY();
				log = log + "Citizen " + c.getName() + " at (" + x + "," + y + ") died\n";
				citizensRemoved11.add(c);
			}
		}
		ImageIcon buildingDestroyed = new ImageIcon("icoon.png");
		for(ResidentialBuilding rb3 : visibleBuildingsCopied) {
			if(rb3.getStructuralIntegrity() == 0) {
				int x = rb3.getLocation().getX();
				int y = rb3.getLocation().getY();
				game.getGrid()[x][y].setIcon(buildingDestroyed);
			}
		}
		visibleBuildings.removeAll(buildingsRemoved);
		visibleCitizens.removeAll(citizensRemoved);
		citizensInTrouble.removeAll(citizensRemoved1);
		occupantsCopied.removeAll(citizensRemoved11);
		log = log + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
		game.updateLog(log);
		updateUnitCount();
	}
	
	public static void playMusic(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(intro != null) {
			if(arg0.getSource() == intro.getStart()) {
				intro.dispose();
				game = new SimulationGame();
				game.getEndCycle().addMouseListener(this);
				game.getRespond().addMouseListener(this);
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						game.getGrid()[i][j].addMouseListener(this);
					}
				}
				game.getBox().addMouseListener(this);
				game.getBox().addActionListener(this);
				game.getResAMB().addMouseListener(this);
				game.getResDCU().addMouseListener(this);
				game.getResEVC().addMouseListener(this);
				game.getResFTK().addMouseListener(this);
				game.getResGCU().addMouseListener(this);
				game.getAvAMB().addMouseListener(this);
				game.getAvDCU().addMouseListener(this);
				game.getAvEVC().addMouseListener(this);
				game.getAvFTK().addMouseListener(this);
				game.getAvGCU().addMouseListener(this);
				game.getTreatingAMB().addMouseListener(this);
				game.getTreatingDCU().addMouseListener(this);
				game.getTreatingEVC().addMouseListener(this);
				game.getTreatingFTK().addMouseListener(this);
				game.getTreatingGCU().addMouseListener(this);
				game.getBack().addMouseListener(this);
				game.getShowInfo().addMouseListener(this);
				
				updateUnitCount();
			}
			else if(arg0.getSource() == intro.getExit()) {
				System.exit(0);
			}
		}
		
		if(game != null) {
			if(arg0.getSource() == game.getBack()) {
				game.dispose();
				try {
					@SuppressWarnings("unused")
					CommandCenter command = new CommandCenter();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(arg0.getSource() == game.getEndCycle()) {
				game.getCurrentCycleNum().setText(Integer.toString(Integer.parseInt(game.getCurrentCycleNum().getText()) + 1));
				try {
					engine.nextCycle();
					refresh();
					game.getCasualitiesNum().setText(Integer.toString(engine.calculateCasualties()));
					if(engine.checkGameOver()) {
						String casualties1 = game.getCasualitiesNum().getText();
						String cycles = game.getCurrentCycleNum().getText();
						game.dispose();
						try {
							CommandCenter c = new CommandCenter();
						} catch (Exception e) {
							e.printStackTrace();
						}
						gameOverWindow = new GameOver(casualties1, cycles);
					}
				} catch (SimulationException e) {
					expWindow = new ExceptionWindow(e.getMessage());
				}
			}
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if(arg0.getSource() == game.getGrid()[i][j]) {
						game.getTargetSelected().setText("EMPTY");
						selectedTarget = null;
						game.updateInfo("");
						game.getBox().removeAllItems();
						for(ResidentialBuilding rb : visibleBuildingsCopied) {
							int x = rb.getLocation().getX();
							int y = rb.getLocation().getY();
							if((x==i) && (y==j)) {
								String buildingInfo = "Building " + x + "," + y;
								game.getTargetSelected().setText(buildingInfo);
								selectedTarget = rb;
								buildingChecked = true;
								for(Citizen c : rb.getOccupants()) {
									game.getBox().addItem(c);
								}
								game.updateInfo(rb.residentialBuildingToString());
							}
						}
						if(buildingChecked == false) {
							for(Citizen c : visibleCitizensCopied) {
								int x = c.getLocation().getX();
								int y = c.getLocation().getY();
								if((x==i) && (y==j))
									game.getBox().addItem(c);
						}
						}
						buildingChecked = false;
				}
			}
		}
			if(arg0.getSource() == game.getShowInfo()) {
				if(game.getBox().getSelectedItem() != null) {
					game.updateInfo(((Citizen)game.getBox().getSelectedItem()).citizenToString());
					selectedTarget = ((Citizen)game.getBox().getSelectedItem());
					String citizenName = ((Citizen)game.getBox().getSelectedItem()).getName();
					game.getTargetSelected().setText(citizenName);
				}
			}
			if(arg0.getSource()==game.getResAMB()) {
				if(getNum(game.getResAMB().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Ambulance) {
							if(u.getState() == UnitState.RESPONDING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("AMB " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getAvAMB()) {
				if(getNum(game.getAvAMB().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Ambulance) {
							if(u.getState() == UnitState.IDLE)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("AMB " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getTreatingAMB()) {
				if(getNum(game.getTreatingAMB().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Ambulance) {
							if(u.getState() == UnitState.TREATING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("AMB " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getResDCU()) {
				if(getNum(game.getResDCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof DiseaseControlUnit) {
							if(u.getState() == UnitState.RESPONDING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("DCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getAvDCU()) {
				if(getNum(game.getAvDCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof DiseaseControlUnit) {
							if(u.getState() == UnitState.IDLE)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("DCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getTreatingDCU()) {
				if(getNum(game.getTreatingDCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof DiseaseControlUnit) {
							if(u.getState() == UnitState.TREATING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("DCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getResEVC()) {
				if(getNum(game.getResEVC().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Evacuator) {
							if(u.getState() == UnitState.RESPONDING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("EVC " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getAvEVC()) {
				if(getNum(game.getAvEVC().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Evacuator) {
							if(u.getState() == UnitState.IDLE)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("EVC " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getTreatingEVC()) {
				if(getNum(game.getTreatingEVC().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof Evacuator) {
							if(u.getState() == UnitState.TREATING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("EVC " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getResFTK()) {
				if(getNum(game.getResFTK().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof FireTruck) {
							if(u.getState() == UnitState.RESPONDING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("FTK " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getAvFTK()) {
				if(getNum(game.getAvFTK().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof FireTruck) {
							if(u.getState() == UnitState.IDLE)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("FTK " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getTreatingFTK()) {
				if(getNum(game.getTreatingFTK().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof FireTruck) {
							if(u.getState() == UnitState.TREATING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("FTK " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getResGCU()) {
				if(getNum(game.getResGCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof GasControlUnit) {
							if(u.getState() == UnitState.RESPONDING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("GCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getAvGCU()) {
				if(getNum(game.getAvGCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof GasControlUnit) {
							if(u.getState() == UnitState.IDLE)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("GCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource()==game.getTreatingGCU()) {
				if(getNum(game.getTreatingGCU().getText()) != 0) {
					for(Unit u : emergencyUnits) {
						if(u instanceof GasControlUnit) {
							if(u.getState() == UnitState.TREATING)
								selectedUnit = u;
						}
					}
					game.getUnitSelected().setText("GCU " + selectedUnit.getLocation().getX() + "," + selectedUnit.getLocation().getY());
				}
				else {
					game.getUnitSelected().setText("EMPTY");
					selectedUnit = null;
				}
			}
			if(arg0.getSource() == game.getRespond()) {
				if((selectedUnit != null) && (selectedTarget != null) && (!game.getUnitSelected().getText().equals("EMPTY")) && (!game.getTargetSelected().getText().equals("EMPTY"))) {
					try {
						selectedUnit.respond(selectedTarget);
						updateUnitCount();
					} catch (SimulationException e) {
						expWindow = new ExceptionWindow(e.getMessage());
					}
				}
			}
			}
		}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		ImageIcon Icon1 = new ImageIcon("red_button12.png");
		ImageIcon Icon11 = new ImageIcon("green_button01.png");
		if(game != null) {
			if(arg0.getSource() == game.getEndCycle()) {
				game.getEndCycle().setIcon(Icon1);
			}
			else if(arg0.getSource() == game.getRespond()) {
				game.getRespond().setIcon(Icon11);
			}
			else if(arg0.getSource() == game.getShowInfo()) {
				game.getShowInfo().setIcon(Icon1);
			}
			
		}
		if(intro != null) {
			if(arg0.getSource() == intro.getStart()) {
				intro.getStart().setIcon(Icon1);
			}
			else if(arg0.getSource() == intro.getExit()) {
				intro.getExit().setIcon(Icon1);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		ImageIcon Icon1 = new ImageIcon("red_button11.png");
		ImageIcon Icon11 = new ImageIcon("green_button00.png");
		if(game != null) {
			if(arg0.getSource() == game.getEndCycle()) {
				game.getEndCycle().setIcon(Icon1);
			}
			else if(arg0.getSource() == game.getRespond()) {
				game.getRespond().setIcon(Icon11);
			}
			else if(arg0.getSource() == game.getShowInfo()) {
				game.getShowInfo().setIcon(Icon1);
			}
		}
		if(intro != null) {
			if(arg0.getSource() == intro.getStart()) {
				intro.getStart().setIcon(Icon1);
			}
			else if(arg0.getSource() == intro.getExit()) {
				intro.getExit().setIcon(Icon1);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static int getNum(String s) {
		int indexOfX = 0;
		for(int i = 0; i<s.length(); i++) {
			if(s.charAt(i) == 'x')
				indexOfX = i;
		}
		return (Integer.parseInt(s.substring(indexOfX + 1)));
	}
	
	public static String updateString(String s, int x) {
		String x1 = Integer.toString(x);
		int index = 0;
		for(int i = 0; i<s.length(); i++) {
			if(s.charAt(i) == 'x')
				index = i+1;
		}
		String k = s.substring(0,index);
		String result = k + x1;
		return result;
	}
	
	public void updateUnitCount() {
		int ambResCount = 0;
		int dcuResCount = 0;
		int evcResCount = 0;
		int ftkResCount = 0;
		int gcuResCount = 0;
		int ambAvCount = 0;
		int dcuAvCount = 0;
		int evcAvCount = 0;
		int ftkAvCount = 0;
		int gcuAvCount = 0;
		int ambTreatingCount = 0;
		int dcuTreatingCount = 0;
		int evcTreatingCount = 0;
		int ftkTreatingCount = 0;
		int gcuTreatingCount = 0;
		for(Unit u : emergencyUnits) {
			if(u instanceof Ambulance) {
				if(u.getState() == UnitState.IDLE)
					ambAvCount++;
				if(u.getState() == UnitState.RESPONDING)
					ambResCount++;
				if(u.getState() == UnitState.TREATING)
					ambTreatingCount++;
			}
			else if (u instanceof DiseaseControlUnit) {
				if(u.getState() == UnitState.IDLE)
					dcuAvCount++;
				if(u.getState() == UnitState.RESPONDING)
					dcuResCount++;
				if(u.getState() == UnitState.TREATING)
					dcuTreatingCount++;
			}
			else if (u instanceof Evacuator) {
				if(u.getState() == UnitState.IDLE)
					evcAvCount++;
				if(u.getState() == UnitState.RESPONDING)
					evcResCount++;
				if(u.getState() == UnitState.TREATING)
					evcTreatingCount++;
			}
			else if (u instanceof FireTruck) {
				if(u.getState() == UnitState.IDLE)
					ftkAvCount++;
				if(u.getState() == UnitState.RESPONDING)
					ftkResCount++;
				if(u.getState() == UnitState.TREATING)
					ftkTreatingCount++;
			}
			else if (u instanceof GasControlUnit) {
				if(u.getState() == UnitState.IDLE)
					gcuAvCount++;
				if(u.getState() == UnitState.RESPONDING)
					gcuResCount++;
				if(u.getState() == UnitState.TREATING)
					gcuTreatingCount++;
			}
		}
		game.getResAMB().setText("AMB x" + ambResCount);
		game.getResDCU().setText("DCU x" + dcuResCount);
		game.getResEVC().setText("EVC x" + evcResCount);
		game.getResFTK().setText("FTK x" + ftkResCount);
		game.getResGCU().setText("GCU x" + gcuResCount);
		game.getAvAMB().setText("AMB x" + ambAvCount);
		game.getAvDCU().setText("DCU x" + dcuAvCount);
		game.getAvEVC().setText("EVC x" + evcAvCount);
		game.getAvFTK().setText("FTK x" + ftkAvCount);
		game.getAvGCU().setText("GCU x" + gcuAvCount);
		game.getTreatingAMB().setText("AMB x" + ambTreatingCount);
		game.getTreatingDCU().setText("DCU x" + dcuTreatingCount);
		game.getTreatingEVC().setText("EVC x" + evcTreatingCount);
		game.getTreatingFTK().setText("FTK x" + ftkTreatingCount);
		game.getTreatingGCU().setText("GCU x" + gcuTreatingCount);
	}

}
