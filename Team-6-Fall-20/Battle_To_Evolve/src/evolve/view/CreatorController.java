package evolve.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import evolve.MainApp;
import evolve.model.Character;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class CreatorController {
	
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	@FXML
	private TextField nameField;
	@FXML
	private TextField armorField;
	@FXML
	private TextField attackField;
	@FXML
	private TextField staminaField;
	@FXML
	private TextField speedField;
	@FXML
	private TextField luckField;
	@FXML
	private Slider armorSlider;
	@FXML
	private Slider attackSlider;
	@FXML
	private Slider staminaSlider;
	@FXML
	private Slider speedSlider;
	@FXML
	private Slider luckSlider;
	@FXML
	private Label statPoints;
	@FXML
	private ImageView imageView;
	
	private File image;
	private boolean allocatePointsWithSliders = true;
	private boolean randomizeSingleStat = true;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	@FXML
	private void initialize() {
		initializeListenors();
	}
	
	
	public void handleCreate() {
		if(nameField.getLength() != 0) {
			Character inputCharacter = getCharacterInfo();
			addCharacter(inputCharacter);
			saveCharacter();
			mainApp.showManagerOverView();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Charater name: Empty");
	        alert.setContentText("You need to provide a name for this character");

	        alert.showAndWait();
		
		}
	}

	public void handleImportImage() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File inFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(inFile != null) {
		  if(inFile.getPath().endsWith(".png") || inFile.getPath().endsWith(".jpg") || 
			 inFile.getPath().endsWith(".gif") ) {
			  try {
				BufferedImage img = ImageIO.read(inFile);
				Image newImage = SwingFXUtils.toFXImage(img, null);
				imageView.setImage(newImage);
				
				image = new File(inFile.getPath()); 
				
			} catch (IOException e) {
				e.printStackTrace();
			}	  
		  }	
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Image format error");
	        alert.setContentText("The file you provided does not conform to image format:\n");

	        alert.showAndWait();
		}
		
	}

	public void handleCancel() {
		mainApp.showManagerOverView();
	}
	public void handleRandomAll() {
		statPoints.setText("100");
		randomizeSingleStat = false;
		handleArmorRandom();
		handleAttackRandom();
		handleStaminaRandom();
		handleSpeedRandom();
		handleLuckRandom();
		randomizeSingleStat = true;
	}
	private Character getCharacterInfo() {
		Character temp = new Character(nameField.getText(), 
				Integer.parseInt(armorField.getText()), 
				Integer.parseInt(attackField.getText()),
				Integer.parseInt(staminaField.getText()),
				Integer.parseInt(speedField.getText()),
				Integer.parseInt(luckField.getText()));
		
		if(image != null) {
			System.out.println(image.getPath());
			temp.setPortrait(image);
		} else {
			image = new File("resources\\defaultPortraits\\superMan.png");
			temp.setPortrait(image);
		}
		
		
		return temp;
	}
	public void initializeListenors() {
		setSliders();
		setTextBoxes();
	}
	
	
	
	// Set slider to auto update (sliders and text boxes)
	public void setSliders() {
		armorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(armorSlider.isValueChanging() && allocatePointsWithSliders) {
					updateStatField(oldValue.intValue(), newValue.intValue(),armorField);
				}
				else {
					allocatePointsWithSliders = true;
				}
			}
		});
		
		
		attackSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(attackSlider.isValueChanging() && allocatePointsWithSliders ) {
					updateStatField(oldValue.intValue(), newValue.intValue(),attackField);
				}
				else {
					allocatePointsWithSliders = true;
				}
			}
		});
		staminaSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(staminaSlider.isValueChanging() && allocatePointsWithSliders) {
					updateStatField(oldValue.intValue(), newValue.intValue(),staminaField);
				}
				else {
					allocatePointsWithSliders = true;
				}
			}
		});
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(speedSlider.isValueChanging() && allocatePointsWithSliders) {
					updateStatField(oldValue.intValue(), newValue.intValue(),speedField);
				}
				else {
					allocatePointsWithSliders = true;
				}
			}
		});
		luckSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(luckSlider.isValueChanging() && allocatePointsWithSliders) {
					updateStatField(oldValue.intValue(), newValue.intValue(),luckField);
				}
				else {
					allocatePointsWithSliders = true;
				}
			}
		});
	}
	// Set text boxes to update automatically (sliders and text boxes)
	public void setTextBoxes() {
		armorField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(!armorField.isFocused() && armorField.getLength() != 0) {
					allocatePoints(armorField,armorSlider);
				}	
				else {
					int fieldNum = cancelTextFieldEdit(armorField);
					armorSlider.setValue(fieldNum);
				}
				
			}});
		
		attackField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(!attackField.isFocused() && attackField.getLength() != 0) {
					allocatePoints(attackField,attackSlider);
				}	
				else {
					int fieldNum = cancelTextFieldEdit(attackField);
					attackSlider.setValue(fieldNum);
				}
				
			}});
		
		staminaField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(!staminaField.isFocused() && staminaField.getLength() != 0) {
					allocatePoints(staminaField,staminaSlider);
				}	
				else {
					int fieldNum = cancelTextFieldEdit(staminaField);
					staminaSlider.setValue(fieldNum);
				}
				
			}});
		
		speedField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(!speedField.isFocused() && speedField.getLength() != 0) {
					allocatePoints(speedField,speedSlider);
				}	
				else {
					int fieldNum = cancelTextFieldEdit(speedField);
					speedSlider.setValue(fieldNum);
				}
				
			}});
		
		luckField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(!luckField.isFocused() && luckField.getLength() != 0) {
					allocatePoints(luckField,luckSlider);
				}	
				else {
					int fieldNum = cancelTextFieldEdit(luckField);
					luckSlider.setValue(fieldNum);
				}
				
			}});
	}

	public void addCharacter(Character inputCharacter) {
		mainApp.getCharacters().add(inputCharacter);
	}
	
	//Adjust sliders if needed after mouse click release
	public void adjustArmorSlider() {
		autoAdjustSliderToTextField(armorSlider,armorField);
	}
	public void adjustAttackSlider() {
		autoAdjustSliderToTextField(attackSlider,attackField);
	}
	public void adjustStaminaSlider() {
		autoAdjustSliderToTextField(staminaSlider,staminaField);
	}
	public void adjustSpeedSlider() {
		autoAdjustSliderToTextField(speedSlider,speedField);
	}
	public void adjustLuckSlider() {
		autoAdjustSliderToTextField(luckSlider,luckField);
	}
	
	//Adjust sliders when field and slider value differ when user release mouse button
	public void autoAdjustSliderToTextField(Slider slider, TextField statField) {
		if((int)slider.getValue() != Integer.parseInt(statField.getText())){
			allocatePointsWithSliders = false;
			slider.setValue(Integer.parseInt(statField.getText()));
		}
	}

	// Random Buttons
	public void handleArmorRandom() {
		if(randomizeSingleStat) {
			statPoints.setText(addPointsToLabel(armorField));
		}
		getRandom(armorSlider,armorField);
	} 
	
	public void handleAttackRandom() {
		if(randomizeSingleStat) {
			statPoints.setText(addPointsToLabel(attackField));
		}
		getRandom(attackSlider,attackField);
	}
	public void handleStaminaRandom() {
		if(randomizeSingleStat) {
			statPoints.setText(addPointsToLabel(staminaField));
		}
		getRandom(staminaSlider,staminaField);
	}
	public void handleSpeedRandom() {
		if(randomizeSingleStat) {
			statPoints.setText(addPointsToLabel(speedField));
		}
		getRandom(speedSlider,speedField);
	}
	public void handleLuckRandom() {
		if(randomizeSingleStat) {
			statPoints.setText(addPointsToLabel(luckField));
		}
		getRandom(luckSlider,luckField);
	}
	
	private void getRandom(Slider slider, TextField statField) {
		allocatePointsWithSliders = false;
		
		if(!reachPointLimit()) {
			int min = (int)slider.getMin();
			int max = (int)slider.getMax();
			int points = Integer.parseInt(statPoints.getText());
			
			
			if( max > points) {
				max = points;
			}
			
			int range = max - min + 1;
			int rand = (int)(Math.random() * range) + min; 
		
			statField.setText(Integer.toString(rand));
			slider.setValue(rand);
			statPoints.setText(Integer.toString(points - rand));
		}
		else {
			statField.setText("0");
			slider.setValue(0);
		}
	}
	
	private String addPointsToLabel(TextField field) {
		int points = Integer.parseInt(statPoints.getText());
		int fieldNum = Integer.parseInt(field.getText());
		return Integer.toString(points + fieldNum);	
	}
	
	
	//Allocate points using sliders
	private void allocatePoints(int oldValue, int newValue,  TextField statField) {
		int points = Integer.parseInt(statPoints.getText());
		int fieldPoints = Integer.parseInt(statField.getText());
		int distance =  Math.abs(oldValue - newValue);
		
		
		if(oldValue != newValue) {
			if(oldValue < newValue && (points - distance) >= 0 && (fieldPoints + distance) <= 100) {
				points -= distance;
				fieldPoints += distance;
				
				statPoints.setText(Integer.toString(points));
				statField.setText(Integer.toString(fieldPoints));
			}
			else if(oldValue > newValue && (points + distance) <= 100 && (fieldPoints - distance) >= 0 ) {
				points +=  distance;
				fieldPoints -=  distance;
				
				statPoints.setText(Integer.toString(points));
				statField.setText(Integer.toString(fieldPoints));
			}
		}
	}
	
	//Allocate points using textfields
	private void allocatePoints(TextField statField, Slider slider) {
		int fieldNum = Integer.parseInt(statField.getText());
		int points =  Integer.parseInt(statPoints.getText());
		
		//if fieldNum is between min and max and points is greater then or equal to zero
		if(fieldNum > (int)armorSlider.getMin() && fieldNum <= (int)armorSlider.getMax() && points >= 0) {
			allocatePointsWithSliders = false;
			
			int distance = Math.abs(fieldNum - (int)slider.getValue());
			
			if((int)slider.getValue() > fieldNum && ((points + distance) <= 100 )) {
				points += distance;
			}
			else if((int)slider.getValue() < fieldNum && ((points - distance) >= 0 )) {
				points -= distance;
			}
			else {
				fieldNum = cancelTextFieldEdit(statField);
			}
		}
		
		else {
			fieldNum = cancelTextFieldEdit(statField);
		}
		
		statPoints.setText(Integer.toString(points));
		slider.setValue(fieldNum);
	}
	
	private int cancelTextFieldEdit(TextField statField) {
		//undo last field edit
		statField.undo();
		
		//if undoing the change causes the field to be empty reinstate the previous value
		if(statField.getLength() == 0) {
			statField.redo();
		}
		
		//Save text value to textfield
		statField.commitValue();
		//return this value
		return Integer.parseInt(statField.getText());
	}
	
	//Checks if the text fields should be updated
	private void updateStatField(int oldValue, int newValue, TextField statField) {
		if(!reachPointLimit() || newValue < Integer.parseInt(statField.getText())) {
			allocatePoints(oldValue, newValue, statField);
		}
	}
	
	private boolean reachPointLimit() {
		return Integer.parseInt(statPoints.getText()) <= 0;
	}
	public void saveCharacter() {
		File inFile = mainApp.getCharacterFilePath();
		
		if(inFile != null) {
			try {
				mainApp.saveCharacterDataToFile(inFile);
			}
			catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Could not save data");
		        alert.setContentText("Could not save Character data to file:\n" + inFile.getPath());
		        alert.showAndWait();
			}
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Warning!");
		    alert.setHeaderText("No Save File!");
		    alert.setContentText("You have not saved your characters!" +
		    		"To prevent losing your characters please save them from the character manager.");
		     alert.showAndWait();
		}
	}
	
}
