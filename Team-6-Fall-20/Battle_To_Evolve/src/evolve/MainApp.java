package evolve;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import evolve.view.BattleStatsController;
import evolve.view.CharacterManagerController;
import evolve.view.CreatorController;
import evolve.view.EvolutionManagerController;
import evolve.view.IntroScreenController;
import evolve.view.RulesController;
import evolve.view.SingleBattleController;
import evolve.model.BattleStats;
import evolve.model.Character;
import evolve.model.CharacterListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<Character> characters = FXCollections.observableArrayList();

	Character selectedCharacter;
	int speed;
	BattleStats stats;
	Character enemy;
	

	@Override // First method called after start of MainApp
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Battle To Evolve");
		this.primaryStage.getIcons().add(new Image("file:resources/images/dna_icon.png"));
		
		// Launch the root GUI
		initRootLayout();
		// Show intro overview
		showIntroOverView();
	}
	// Launches start method
	public static void main(String[] args) {
		launch(args);
	}
	public MainApp() {
		characters.add(new Character("Default", 20, 20, 20, 20, 20));
		characters.add(new Character("Dummy1", 30, 10, 20, 20, 20));
		characters.add(new Character("Dummy2", 30, 10, 40, 20, 0));
		for(Character c : characters) {
			c.setPortrait(new File("resources\\defaultPortraits\\superMan.png"));
		}
	}
	// Returns array list of characters
	public ObservableList<Character> getCharacters(){
		return characters;
	}
	// Sets selected character to mainApp
	public void setCharacter(Character selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}
	// Sets selected battle speed
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setStats(BattleStats stats) {
		this.stats = stats;
	}
	public void setEnemy(Character enemy) {
		this.enemy = enemy;
	}
	//Return primary stage
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	// Initializes the root layout (RootLayout.fxml)
	public void initRootLayout() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	// Shows main intro screen
	public void showIntroOverView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/IntroScreen.fxml"));
			AnchorPane intro = (AnchorPane) loader.load();
			
			rootLayout.setCenter(intro);
			
			IntroScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Shows rules in another window
	public void showRuleOverview() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(MainApp.class.getResource("view/Rules.fxml"));
			 AnchorPane page = (AnchorPane) loader.load();
			 
			 Stage dialogStage = new Stage();
			 dialogStage.setTitle("Rules");
			 dialogStage.initModality(Modality.WINDOW_MODAL);
			 dialogStage.initOwner(primaryStage);
			 Scene scene = new Scene(page);
			 dialogStage.setScene(scene);
			 
			 RulesController controller = loader.getController();
			 controller.setDialogStage(dialogStage);
			 
			 dialogStage.showAndWait();
			 
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}
	// Shows character manager screen
	public void showManagerOverView() {
		try {
			primaryStage.setTitle("Battle To Evolve");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CharacterManager.fxml"));
			AnchorPane manager = (AnchorPane) loader.load();
			
			rootLayout.setCenter(manager);
			
			CharacterManagerController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Shows character creator screen
	public void showCreatorOverView() {
		try {
			primaryStage.setTitle("Battle To Evolve");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CharacterCreator.fxml"));
			AnchorPane creator = (AnchorPane) loader.load();
			
			rootLayout.setCenter(creator);
			
			CreatorController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Show evolution manager GUI
	public void showEvolutionManagerOverView() {
		try {
			primaryStage.setTitle("Battle To Evolve");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EvolutionManager.fxml"));
			AnchorPane evolutionManager = (AnchorPane) loader.load();
			
			rootLayout.setCenter(evolutionManager);
			
			EvolutionManagerController controller = loader.getController();
			controller.setMainApp(this);
			controller.initData(selectedCharacter, enemy);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Show single battle GUI
	public void showSingleBattleOverView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SingleBattle.fxml"));
			AnchorPane singleBattle = (AnchorPane) loader.load();
			
			rootLayout.setCenter(singleBattle);
			
			SingleBattleController controller = loader.getController();
			controller.setMainApp(this);
			controller.initData(selectedCharacter, speed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Show Battle stats GUI	
	public void showBattleStatsOverView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BattleStats.fxml"));
			AnchorPane statsManager = (AnchorPane) loader.load();
			
			rootLayout.setCenter(statsManager);
      
			BattleStatsController controller = loader.getController();
			controller.setMainApp(this);
			controller.initData(stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	// Get character file path
	@SuppressWarnings("unused")
	public File getCharacterFilePath() {
		/*
		 * If you uncomment the following two lines it will automatically get the previous save file if it exists
		 * Uncomment the first two lines and comment out (String filePath = null) to increase persistence
		 * */
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		//String filePath = null; 
		if(filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	// Set character file path
	public void setCharacterFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
		} else {
			prefs.remove("filePath");
		}
	}
	
	// Load character data
	public void loadCharacterDataFromFile(File inFile) throws Exception  {
		JAXBContext context = JAXBContext.newInstance(CharacterListWrapper.class);
		Unmarshaller um = context.createUnmarshaller();
		
		CharacterListWrapper wrapper = (CharacterListWrapper) um.unmarshal(inFile);
		
		characters.clear();
		characters.addAll(wrapper.getCharacters());
		
		setCharacterFilePath(inFile);
	}
	
	// Save character data
	public void saveCharacterDataToFile(File inFile) throws Exception {
		JAXBContext context = JAXBContext.newInstance(CharacterListWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		CharacterListWrapper wrapper = new CharacterListWrapper();
		wrapper.setCharacters(characters);
		
		m.marshal(wrapper, inFile);
		
		setCharacterFilePath(inFile);
	}
}
