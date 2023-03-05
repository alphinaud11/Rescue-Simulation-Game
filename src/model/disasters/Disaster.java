package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.SimulationException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws SimulationException {
		
		if (target instanceof Citizen) {
			if(((Citizen) target).getHp() == 0) 
				throw new CitizenAlreadyDeadException(this,"Citizen is already dead");
			else {
				target.struckBy(this);
				active=true;
			}
		}
		
		else if(target instanceof ResidentialBuilding) {
			if(((ResidentialBuilding) target).getStructuralIntegrity()==0)
				throw new BuildingAlreadyCollapsedException(this,"Building is already collapsed");
			else {
				target.struckBy(this);
				active=true;
			}
		}
		
	}
}
