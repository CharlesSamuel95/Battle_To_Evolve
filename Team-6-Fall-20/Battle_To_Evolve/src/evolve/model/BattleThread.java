package evolve.model;

import java.util.Random;

import evolve.view.SingleBattleController;
import javafx.scene.control.TextArea;

public class BattleThread extends Thread{

	
	Character attacker;
	Character victim;
	BattleStats stats;
	TextArea battleLog;
	int speed = 100;
	Random rand = new Random();
	String output = "";
	SingleBattleController controller;
	
	// Empty Constructor
	public BattleThread() {}
	
	// Sets all information that the thread needs
	public void setInfo(Character attacker, Character victim, BattleStats stats, SingleBattleController controller, int speed) {
		this.attacker = attacker;
		this.victim= victim;
		this.stats = stats;
		this.controller = controller;
		this.speed = speed;
	}
	
	// Override run method for thread
	@Override
	public void run() {
		//System.out.println(attacker.toString());
		// Check to make sure that both attacker and victim have health remaining and that nobody has quit
		while(attacker.getHealth() > 0 &&  victim.getHealth() > 0 && !controller.getStatus()) {
			synchronized(controller){
				if(!controller.getStatus()) {
					stats.addRound();
					output = "";
					if(!dodges()) {
						if(criticalAttack()) {
							int damage = getCritical();
							attack(damage);
							printCritical(damage);
						} else {
							attack(attacker.getAttack());
							if(victim.getHealth() > 0) {
								printAttack(attacker.getAttack());
							}
						}
					} else {
						printDodge();
					}
					controller.addToBattleLog(output);
				}
			}
			// Make thread sleep for given amount of time, (speed)
			try {
				Thread.sleep(speed * ((attacker.getLevel() * 100) - attacker.getStamina()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void attack(int damage) {

		stats.addToTotalDamage(damage);
		// Determines the total damage for each character
		if(victim.equals(controller.getUserCharacterClone())) {
			stats.addEnemyTotalDamageDealt(damage);					
		}
		else {
			stats.addUserTotalDamageDealt(damage);
		}
		

		if(hasArmor()) {
			if(victim.getArmor() >= damage) {				
				victim.setArmor(victim.getArmor() - damage);
			} else {
				damage -= victim.getArmor();
				victim.setArmor(0);
				victim.setHealth(victim.getHealth() - damage);
			}
		} else if(victim.getHealth() > damage){
			victim.setHealth(victim.getHealth() - damage);
		} else {
			killShot(damage);
		}
	}
	public void killShot(int damage) {
		victim.setHealth(0);
		printKill(damage);
	}
	public int getCritical() {

		//stats.addCrit();

		int damage = 0;
		int type = rand.nextInt(4)+1;
		switch(type) {
			case 1:
				damage = (int) Math.round(attacker.getAttack() * 1.25);
				break;
			case 2:
				damage = (int) Math.round(attacker.getAttack() * 1.5);
				break;
			case 3:
				damage = (int) Math.round(attacker.getAttack() * 1.75);
				break;
			case 4:
				damage = (int) Math.round(attacker.getAttack() * 2);
				break;
		}
		return damage;
	}
	// Boolean checks
	// Checks if victim is lucky enough to dodge an attack
	public boolean dodges() {
		if(rand.nextInt(100)+1 <= victim.getLuck()) {
			return true;
		}
		return false;
	}
	// Checks if attacker gets a critical hit
	public boolean criticalAttack() {
		if(rand.nextInt(100)+1 <= attacker.getLuck()) {
			if(victim.equals(controller.getUserCharacterClone())) {
				stats.addEnemyCrit();					
			}
			else {
				stats.addUserCrit();
			}
			return true;
		}
		return false;
	}
	// Check if victim has armor
	public boolean hasArmor() {
		if(victim.getArmor() > 0) {
			return true;
		}
		return false;
	}
	// Print methods
	public void printHead() {
		output += "Round: " + stats.getTotalRounds() + "\n";
	}
	public void printDodge() {
		if(victim.equals(controller.getUserCharacterClone())) {
			stats.addUserDodge();					
		}
		else {
			stats.addEnemyDodge();
		}
		output += victim.getName() + " dodges " + attacker.getName() + "'s attack!\n";
		output += "\tIn round: " + stats.getTotalRounds() + "\n";
	}
	public void printCritical(int damage) {
		printHead();
		output += attacker.getName() + " gets a critical hit on " + victim.getName() + "\n";
		printEnd(damage);
	}
	public void printAttack(int damage) {
		printHead();
		output += attacker.getName() + " attacks " + victim.getName() + "\n";
		printEnd(damage);
	}
	public void printKill(int damage) { 
		controller.setEnd();
		if(victim.equals(controller.getUserCharacterClone())) {
			stats.setEnemyWon();				
		}
		else {
			stats.setUserWon();
		}
		stats.setEnd(System.nanoTime());
		controller.seeStats();
		output += attacker.getName() + " attacks " + victim.getName() + " for " + damage + ", ultimatly killing them.\n";
		output += victim.getName() + " was killed by " + attacker.getName() + " in a total of " + stats.getTotalRounds() + " rounds\n";
		output += attacker.getName() + " remaning health was " + attacker.getHealth() + " hp.\n";
	}
	public void printEnd(int damage) {
		output += "\tTotal damage done: " + damage + "\n";
		output += "\t\tVictim Health/Armour: " + victim.getHealth() + "/" + victim.getArmor() + "\n";
	}
}
