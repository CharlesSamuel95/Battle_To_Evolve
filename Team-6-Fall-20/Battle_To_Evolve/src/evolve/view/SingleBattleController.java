package evolve.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import evolve.MainApp;
import evolve.model.Character;
import evolve.model.BattleStats;
import evolve.model.BattleThread;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SingleBattleController {
	
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private Character userCharacter;
	private Character enemyCharacter;
	private int speed;
	// These must stay public
	public Character userClone;
	public Character enemyClone;
	public BattleThread userThread = new BattleThread();
	public BattleThread enemyThread = new BattleThread();
	public BattleStats stats = new BattleStats();
	
	
	private Boolean surrendorEnd = false; // To show that threads need to end
	
	@FXML
	public TextArea battleLog; // This must be public
	@FXML
	private Label userName;
	@FXML
	private Label enemyName;
	@FXML
	private ImageView userImage;
	@FXML
	private ImageView enemyImage;
	@FXML
	private Label attackerLabel;
	@FXML
	public Label victim;
	@FXML
	private Label roundEvent;
	@FXML
	private Button statsButton;
	
	/*
	 * This is the same as the initialize method except in can take an input
	 * */
	public void initData(Character character, int speed) {
		this.userCharacter = character;
		this.speed = speed;
		statsButton.setVisible(false);
		setBattle();
	}
	public void setBattle() {
		getRandomEnemy();
		cloneCharacters();
		setLabels();
		setImages();
		makeThreads();
		startThreads();
	}
	
	// Creates random enemy character
	public void getRandomEnemy() {
		Random rand = new Random();
		selectRandom(rand);
		buildEnemy(rand);
	}
	// Clone characters
	public void cloneCharacters() {
		userClone 	= new Character(userCharacter);
		userClone.setLevel(userCharacter.getLevel());
		enemyClone 	= new Character(enemyCharacter);
		enemyClone.setLevel(userCharacter.getLevel());
	}
	public void setLabels() {
		userName.setText(userClone.getName());
		enemyName.setText(enemyClone.getName());
	}
	private void setImages() {
		File userPortrait = userCharacter.getPortrait();
		File enemyPortrait = enemyCharacter.getPortrait();
		//System.out.println(enemyPortrait.getPath());
		
		if(userPortrait != null) {
			try {
				BufferedImage img = ImageIO.read(userPortrait);
				Image newImage = SwingFXUtils.toFXImage(img, null);
				userImage.setImage(newImage);
				userImage.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {			
			userImage.setVisible(false);
		}
		if(enemyPortrait != null) {
			try {
				BufferedImage img = ImageIO.read(enemyPortrait);
				Image newImage = SwingFXUtils.toFXImage(img, null);
				enemyImage.setImage(newImage);
				enemyImage.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void makeThreads() {
		userThread.setInfo(userClone, enemyClone, stats, this, speed);
		enemyThread.setInfo(enemyClone, userClone, stats, this, speed);
	}
	/*
	 * Starts threads based on character speeds, faster speeds go first
	 * */
	public void startThreads() {
		stats.setStart(System.nanoTime());
		if(userCharacter.getSpeed() > enemyCharacter.getSpeed()) {
			userThread.start();
			enemyThread.start();		
		} else {
			enemyThread.start();
			userThread.start();
		}
	}
	public void addToBattleLog(String input) {
		battleLog.appendText(input + "\n");
	}
	
	
	
	public void buildEnemy(Random rand) {
		enemyCharacter.setArmor(getRandom(25, 30));
		enemyCharacter.setAttack(getRandom(25 ,30));
		enemyCharacter.setSpeed(getRandom(15, 20));
		enemyCharacter.setStamina(getRandom(15,20));
		enemyCharacter.setLuck(getRandom(1,20));
	}
	public int getRandom(int min, int max) {
		return (min + (int)(Math.random()*(max - min +1))) * userCharacter.getLevel();
	}
	public void selectRandom(Random rand) {
		switch(rand.nextInt(5)+1) {
			case 1: 
				enemyCharacter = new Character("Voldermort");
				enemyCharacter.setPortrait(new File("resources\\defaultPortraits\\voldermort.jpg"));
				break;
			case 2: 
				enemyCharacter = new Character("Scar");
				enemyCharacter.setPortrait(new File("resources\\defaultPortraits\\scar.jpg"));
				break;	
			case 3: 
				enemyCharacter = new Character("Thanos");
				enemyCharacter.setPortrait(new File("resources\\defaultPortraits\\thanos.jpg"));
				break;
			case 4: 
				enemyCharacter = new Character("Darth Vader");
				enemyCharacter.setPortrait(new File("resources\\defaultPortraits\\vader.png"));
				break;
			case 5: 
				enemyCharacter = new Character("Joker");
				enemyCharacter.setPortrait(new File("resources\\defaultPortraits\\joker.jpg"));
				break;	
		}
	}	
	// Handle Buttons
	public void handleCancel() {
		mainApp.showManagerOverView();
	}
	public void handleSurrender() {
		if(userClone.getHealth() != 0 && enemyClone.getHealth() != 0) {
			this.surrendorEnd = true; // Kills threads if user surrendors
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Surredner");
	        alert.setHeaderText("User Surrendered");
	        alert.setContentText("You have chossen the easy way and will not get to enjoy the riches of defeating your enemy.");
	        
	        alert.showAndWait();
			mainApp.showManagerOverView();
		}
	}
	
	public void getStats() {
		stats.setUserHealth(userClone.getHealth());
		stats.setEnemyHealth(enemyClone.getHealth());
		mainApp.setStats(stats);
		mainApp.setEnemy(enemyCharacter);
		mainApp.showBattleStatsOverView();
	}
	
	// Get and set methods
	public Boolean getStatus() {
		return surrendorEnd;
	}
	
	public Character getUserCharacterClone() {
		return userClone;
	}
	
	public Character getEnemyCharacterClone() {
		return enemyClone;
	}
	
	public Character getUserCharacter() {
		return userCharacter;
	}
	
	public Character getEnemyCharacter() {
		return enemyCharacter;
	}
	
	public BattleStats getBattleStats() {
		return stats;
	}
	public void setEnd() {
		stats.setEnd(System.nanoTime());
		this.surrendorEnd = true;
	}
	public void seeStats() {
		statsButton.setVisible(true);
	}
}
