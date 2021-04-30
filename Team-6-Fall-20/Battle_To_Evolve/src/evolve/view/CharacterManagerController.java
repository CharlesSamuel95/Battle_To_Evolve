package evolve.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import evolve.MainApp;
import evolve.model.Character;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class CharacterManagerController {
	
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	@FXML
	private TableView<Character> characterTable;
	@FXML
	private TableColumn<Character, String> nameColumn;
	@FXML
	private TableColumn<Character, Number> levelColumn;
	// Labels
	@FXML
	Label lblName;
	@FXML
	Label lblHealth;
	@FXML
	Label lblArmor;
	@FXML
	Label lblAttack;
	@FXML
	Label lblStamina;
	@FXML
	Label lblSpeed;
	@FXML
	Label lblLuck;
	@FXML
	ImageView imageView;
	Character selectedCharacter;
	@FXML
	private ChoiceBox<String> battleSpeed;
	@FXML
	private Button btnEvolution;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		characterTable.setItems(mainApp.getCharacters()); // Sets character table with list from main app
		//characterTable.getSelectionModel().selectFirst(); // Sets to automatically select first character
	}
	@FXML
	public void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		levelColumn.setCellValueFactory(cellData -> cellData.getValue().getLevelProperty());
		btnEvolution.setVisible(false);
		fillBattleSpeed();
		initializeTableListeners();
	}		
	// Update GUI with information from selected character
	private void updateManager(Character selectedCharacter) {
		if(!mainApp.getCharacters().isEmpty()) {
			this.selectedCharacter = mainApp.getCharacters().get(mainApp.getCharacters().indexOf(selectedCharacter));
			mainApp.setCharacter(selectedCharacter);
			updateLabels(selectedCharacter);
			setLabelVisibility(true);
		}
	}
	private void deleteCharacter(int index) {
		mainApp.getCharacters().remove(index);
	}	
	/*
	 * Following methods handle saving and opening a character list
	 * */
	// Save characters to file, if there is no file than prompts user to select a file
	private void save() {
		File characterFile = mainApp.getCharacterFilePath();
		if(characterFile != null) {
			try {
				mainApp.saveCharacterDataToFile(characterFile);
				mainApp.getPrimaryStage().setTitle("Battle To Evolve - Saved successfully to " + characterFile.getName());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Could not save data");
		        alert.setContentText("Could not save data to file:\n");
		        alert.showAndWait();
			}
		} else {
			saveAs();
		}
	}
	// Save character list into a file
	private void saveAs() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File inFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if(inFile != null) {
			if(!inFile.getPath().endsWith(".xml")) {
				inFile = new File(inFile.getPath() + ".xml");
			}
			try {
				mainApp.saveCharacterDataToFile(inFile);
				mainApp.getPrimaryStage().setTitle("Battle To Evolve - Saved successfully to " + inFile.getName());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Could not save data");
		        alert.setContentText("Could not save data to file:\n" + inFile.getPath());
		        alert.showAndWait();
			}
		}
	}
	// Open a character list from a file
	private void open() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File inFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(inFile != null) {
			try {
				mainApp.loadCharacterDataFromFile(inFile);
				mainApp.getPrimaryStage().setTitle("Battle To Evolve - Loaded successfully from " + inFile.getName());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
			     alert.setTitle("Error");
			     alert.setHeaderText("Could not load data");
			     alert.setContentText("Could not load data from file:\n" + inFile.getPath());

			     alert.showAndWait();
			}
		}
	}	
	
	// Updates labels with selectedCharacter and sets characters portrait if available
	private void updateLabels(Character selectedCharacter) {
		lblName.setText(selectedCharacter.getName());
		lblHealth.setText(Integer.toString(selectedCharacter.getHealth()));
		lblArmor.setText(Integer.toString(selectedCharacter.getArmor()));
		lblAttack.setText(Integer.toString(selectedCharacter.getAttack()));
		lblStamina.setText(Integer.toString(selectedCharacter.getStamina()));
		lblSpeed.setText(Integer.toString(selectedCharacter.getSpeed()));
		lblLuck.setText(Integer.toString(selectedCharacter.getLuck()));
		
		File file = selectedCharacter.getPortrait();
		
		if(file != null) {
			
			try {
				BufferedImage img = ImageIO.read(file);
				Image newImage = SwingFXUtils.toFXImage(img, null);
				imageView.setImage(newImage);
				imageView.setVisible(true);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		else {
						
			imageView.setVisible(false);
		}
	}
	// Determine whether character traits are or are not visible to user
	private void setLabelVisibility(boolean isVisible) {
		lblName.setVisible(isVisible);
		lblHealth.setVisible(isVisible);
		lblArmor.setVisible(isVisible);
		lblAttack.setVisible(isVisible);
		lblStamina.setVisible(isVisible);
		lblSpeed.setVisible(isVisible);
		lblLuck.setVisible(isVisible);
	}
	
	public void fillBattleSpeed() {
		battleSpeed.getItems().add("Super Slow");
		battleSpeed.getItems().add("Slow");
		battleSpeed.getItems().add("Normal");
		battleSpeed.getItems().add("Fast");
		battleSpeed.getItems().add("Super Fast");
		battleSpeed.setValue("Normal");
	}
	// Button Handlers
	@FXML
	public void handleCreate() {
		mainApp.showCreatorOverView();
	}
	@FXML
	public void handleDelete() {
		deleteCharacter(characterTable.getSelectionModel().getSelectedIndex());
		setLabelVisibility(false);
	}
	@FXML
	public void handleSave() {
		save();
	}
	@FXML
	public void handleSaveAs() {
		saveAs();
	}
	@FXML
	public void handleOpen() {
		open();
	}
	
	@FXML
	public void handleEvolution() {
		mainApp.showEvolutionManagerOverView();
	}
	
	@FXML
	public void handleRules() {
		mainApp.showRuleOverview();
	}
	@FXML
	public void handleExit() {
		System.exit(0);
	}
	@FXML
	public void handleSingleBattle() {
		if(selectedCharacter == null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Single Battle: No character selected");
	        alert.setContentText("You have not selected a character for single battle.");
	        
	        alert.showAndWait();
		}
		
		else {
			mainApp.setSpeed(getSpeed());
			mainApp.showSingleBattleOverView();
		}
	}	
	// Sets speed based on user selection
	public int getSpeed() {
		int speed = 0;
		String selection = (String) battleSpeed.getValue();
		if(selection.equals("Super Slow")) {
			speed = 200;
		}
		if(selection.equals("Slow")) {
			speed = 150;
		}
		if(selection.equals("Normal")) {
			speed = 100;
		}
		if(selection.equals("Fast")) {
			speed = 50;
		}
		if(selection.equals("Super Fast")) {
			speed = 10;
		}
		return speed;
	}
	// Add listener to get selected index
	private void initializeTableListeners() {
		characterTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateManager(newValue));
	}
}
