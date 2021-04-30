package evolve.model;

import java.io.File;

import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
@XmlType(propOrder= {"name", "health", "armor", "attack", "stamina", "speed", "luck", "level", "portrait"})
public class Character{
	
	// Character Attributes
	private final StringProperty  name		= new SimpleStringProperty(""); 	// Name of character ** Name cannot be changed **
	private final IntegerProperty health	= new SimpleIntegerProperty(100);	// Amount of default damage a character can take before death
	private final IntegerProperty armor		= new SimpleIntegerProperty(0); 	// Amount of armor (extra health) a character gets
	private final IntegerProperty attack	= new SimpleIntegerProperty(0); 	// Amount of damage possibly dealt every attack
	private final IntegerProperty stamina	= new SimpleIntegerProperty(0);		// How quickly a character can follow up an attack
	private final IntegerProperty speed		= new SimpleIntegerProperty(0);		// How quickly a character can attack after the start of a round
	private final IntegerProperty luck 		= new SimpleIntegerProperty(0);		// Chance a character has to dodge or get a critical hit
	private final IntegerProperty level		= new SimpleIntegerProperty(0);     // Holds characters level
	private File portrait                   =  null;							// Stores the file path of the characters image file
	// Full Constructor
	public Character(String name, int health, int armor, int attack, int stamina, int speed, int luck, File imagePath) {
		this.name.set(name);
		this.health.set(health);
		this.armor.set(armor);
		this.attack.set(attack);
		this.stamina.set(stamina);
		this.speed.set(speed);
		this.luck.set(luck);
		portrait = imagePath;
		this.level.set((armor + attack + stamina + speed + luck) / 100);
	}
	// Constructor, takes name, armor, attack, stamina, speed, and luck
	public Character(String name, int armor, int attack, int stamina, int speed, int luck) {
		this.name.set(name);
		this.armor.set(armor);
		this.attack.set(attack);
		this.stamina.set(stamina);
		this.speed.set(speed);
		this.luck.set(luck);
		this.level.set((armor + attack + stamina + speed + luck) / 100);
	}
	public Character(String name) {
		this.name.set(name);
	}
	// Character copy
	public Character(Character c) {
		this.name.set(c.getName());
		this.armor.set(c.getArmor());
		this.attack.set(c.getAttack());
		this.stamina.set(c.getStamina());
		this.speed.set(c.getSpeed());
		this.luck.set(c.getLuck());
	}
	
	// Constructor, takes name, armor, attack, stamina, speed, luck, and character image
	public Character(String name, int armor, int attack, int stamina, int speed, int luck, 
	         File imagePath) {
			this.name.set(name);
			this.armor.set(armor);
			this.attack.set(attack);	
			this.stamina.set(stamina);
			this.speed.set(speed);
			this.luck.set(luck);
			portrait = imagePath;
			this.level.set((armor + attack + stamina + speed + luck) / 100);
	}
	// No arguments constructor
	public Character() {};
	
	//Go through and check if each PC stat is lower than the merge stats. If so, update to random num between those stats
	public void updatePlayerCharacterStats(Character mergeCharacter) {
		if (this.getArmor() < mergeCharacter.getArmor()) {
			this.setArmor(generateRandomNumStat(mergeCharacter.getArmor(), this.getArmor()));
		}

		if (this.getAttack() < mergeCharacter.getAttack()) {
			this.setAttack(generateRandomNumStat(mergeCharacter.getAttack(), this.getAttack()));
		}

		if (this.getStamina() < mergeCharacter.getStamina()) {
			this.setStamina(generateRandomNumStat(mergeCharacter.getStamina(), this.getStamina()));
		}

		if (this.getSpeed() < mergeCharacter.getSpeed()) {
			this.setSpeed(generateRandomNumStat(mergeCharacter.getSpeed(), this.getSpeed()));
		}

		if (this.getLuck() < mergeCharacter.getLuck()) {
			this.setLuck(generateRandomNumStat(mergeCharacter.getLuck(), this.getLuck()));
		}
	}

	private static int generateRandomNumStat(int max, int min) {
		int retNum = (int) ((Math.random() * ((max - min) + 1)) + min);
		return retNum;
	}
	/*
	 * Leave these methods at the bottom of class for ease of readability
	 * Simple get and set methods
	 * */
	// Get Methods
	public String getName() {
		return name.get();
	}
	public int getHealth() {
		return health.get();
	}
	public int getArmor() {
		return armor.get();
	}
	public int getAttack() {
		return attack.get();
	}
	public int getStamina() {
		return stamina.get();
	}
	public int getSpeed() {
		return speed.get();
	}
	public int getLuck() {
		return luck.get();
	}
	public int getLevel() {
		return level.get();
	}
	public File getPortrait() {
		return portrait;
	}
	public StringProperty getNameProperty() {
		return name;
	}
	public IntegerProperty getHealthProperty() {
		return health;
	}
	public IntegerProperty getArmorProperty() {
		return armor;
	}
	public IntegerProperty getAttackProperty() {
		return attack;
	}
	public IntegerProperty getStaminaProperty() {
		return stamina;
	}
	public IntegerProperty getSpeedProperty() {
		return speed;
	}
	public IntegerProperty getLuckProperty() {
		return luck;
	}
	public IntegerProperty getLevelProperty() {
		return level;
	}
	
	// Set Methods
	public void setName(String name) {
		this.name.set(name);
	}
	public void setHealth(int health) {
		this.health.set(health);
	}
	public void setArmor(int armor) {
		this.armor.set(armor);
	}
	public void setAttack(int attack) {
		this.attack.set(attack);
	}
	public void setStamina(int stamina) {
		this.stamina.set(stamina);
	}
	public void setSpeed(int speed) {
		this.speed.set(speed);
	}
	public void setLuck(int luck) {
		this.luck.set(luck);
	}
	public void setLevel(int level) {
		this.level.set(level);
	}
	public void setPortrait(File image) {
		portrait = image; 
	}
	public String toString() {
		String output = "";
		output += "\tName " + getName() + " \n" +
				"\t\tLevel: " + getLevel() + "\n" + 
				"\t\tHealth: " + getHealth() + "\n" +
				"\t\tArmor: " + getArmor() + "\n" +
				"\t\tAttack " + getAttack() + "\n" +
				"\t\tStamina: " + getStamina() + "\n" +
				"\t\tSpeed: " + getSpeed() + "\n" +
				"\t\tLuck: " + getLuck() + "\n";
		return output;
	}
}
