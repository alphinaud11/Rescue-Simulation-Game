package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.SimulationException;
import model.infrastructure.ResidentialBuilding;


public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	@Override
	public void strike() throws SimulationException{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		if(((ResidentialBuilding) target).getStructuralIntegrity()==0)
			throw new BuildingAlreadyCollapsedException(this,"Building is already collapsed");
		else {
			target.setFireDamage(target.getFireDamage()+10);
			super.strike();
		}
	}

	@Override
	public void cycleStep() {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFireDamage(target.getFireDamage()+10);
		
	}

}
