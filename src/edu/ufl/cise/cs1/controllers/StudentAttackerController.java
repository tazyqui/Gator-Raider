package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;
import game.system._Actor;
import game.system._Attacker;
import game.system._Maze;

import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		List<Node> pillList = game.getPillList();
		List<Node> powerPillList = game.getPowerPillList();
		List<Defender> defenderList = game.getDefenders();

		int action;
		int closestDefenderIndex=0;
		//look for the closest defender
		for(int i = 0 ; i < defenderList.size(); i++) {
			if(game.getAttacker().getLocation().getPathDistance(game.getDefender(i).getLocation())<game.getAttacker().getLocation().getPathDistance(game.getDefender(closestDefenderIndex).getLocation())){
				closestDefenderIndex=i;
			}
		}
		if(!(game.getDefender(closestDefenderIndex).isVulnerable()) && ((game.getAttacker().getLocation().getPathDistance(game.getDefender(closestDefenderIndex).getLocation()))<11)){
			//flee from non-vulnerable defender
			return game.getAttacker().getNextDir(game.getDefender(closestDefenderIndex).getLocation(), false);
		}
		else if((game.getDefender(closestDefenderIndex).isVulnerable())){
			//flee from non-vulnerable defender){
			//attack vulnerable defender near the attacker
			return game.getAttacker().getNextDir(game.getDefender(closestDefenderIndex).getLocation(), true);
		}
		if(powerPillList.size()>0){
			//attacker path to the closest power pill
			action = game.getAttacker().getNextDir(game.getAttacker().getTargetNode(powerPillList,true),true);
		}
		else{
			//attacker path to the closest pill
			action = game.getAttacker().getNextDir(game.getAttacker().getTargetNode(pillList,true),true);
		}


		return action;
	}
}