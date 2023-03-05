package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.SimulationException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}

	public void respond(Rescuable r) throws SimulationException {
		
		if(r instanceof ResidentialBuilding) {
			throw new IncompatibleTargetException(this,r,"Not the suitable unit for the target");
		}
		
		else if(r instanceof Citizen) {
		    if(((Citizen)r).getState()==CitizenState.SAFE || ((Citizen) r).getBloodLoss()==0) { 
			throw new CannotTreatException(this,r,"Citizen is Safe");
			}
		    else {
		    	if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
						&& getState() == UnitState.TREATING)
					reactivateDisaster();
				finishRespond(r);
		    }
		}
	}

}
