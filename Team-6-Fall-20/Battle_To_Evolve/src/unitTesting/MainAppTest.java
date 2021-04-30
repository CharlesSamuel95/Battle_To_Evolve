package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import evolve.MainApp;
import evolve.model.Character;
import javafx.collections.ObservableList;


class MainAppTest {

	//Testing Save and Load methods
	@Test
	@DisplayName("Test getCharacters() Method: should return true")
	void testGetCharacters() {
		MainApp main = new MainApp();
		ObservableList<Character> chr = main.getCharacters();
		assertNotNull(chr);
	}

	
	
	@Test
	@DisplayName("Test saveCharacterDataToFile() when file path is valid")
	void testSaveCharacterDataToFile_valid() {
		MainApp main = new MainApp();
		ObservableList<Character> chr = main.getCharacters();
		File file = new File("testFile.xml");
		Character character = new Character("JimmyDore",10,10,10,10,10);
		
		chr.add(character);
		assertDoesNotThrow(()-> main.saveCharacterDataToFile(file));
	}

	
	@Test
	@DisplayName("Test saveCharacterDataToFile() when file path is invalid")
	void testSaveCharacterDataToFile_invalid() {
		MainApp main = new MainApp();
		ObservableList<Character> chr = main.getCharacters();
		
		File file = new File("");
		Character character = new Character("Harry",10,10,10,10,10);
		
		chr.add(character);
		assertThrows(JAXBException.class,() -> main.saveCharacterDataToFile(file));
	}
	
	@Test
	@DisplayName("create two save files using save method")
	void testSaveCharacterDataToFile_createTwoSaveFiles() {
		MainApp main = new MainApp();
		ObservableList<Character> chr = main.getCharacters();
		
		File file = new File("save1.xml");
		File file2 = new File("save2.xml");
		
		Character character = new Character("Jonny Bravo",90,9,0,1,0);
		
		try {
			main.saveCharacterDataToFile(file);
			chr.add(character);
			main.saveCharacterDataToFile(file2);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(file.exists() && file2.exists());
	}
	
	@Test
	@DisplayName("Test testLoadCharacterDataFromFile() when file path is valid")
	void testLoadCharacterDataFromFile_valid() {
		//First save character data into a new file and load from that
		MainApp main = new MainApp();
		ObservableList<Character> chr = main.getCharacters();
		
		File file = new File("testFileLoading.xml");
		Character character = new Character("Timpool",10,10,10,10,10);
		
		chr.add(character);
		
		try {
			main.saveCharacterDataToFile(file);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//Now test the load method
		assertDoesNotThrow(()-> main.loadCharacterDataFromFile(file));
		 
	}
	
	@Test
	@DisplayName("Test testLoadCharacterDataFromFile() when file path is invalid")
	void testLoadCharacterDataFromFile_invalid() {
		MainApp main = new MainApp();
		
		File file = new File("");
		
		assertThrows(JAXBException.class,() -> main.loadCharacterDataFromFile(file));
	}
	
	@Test
	@DisplayName("Test both save and load methods together")
	void testSaveAndLoadMethods() {
		MainApp main = new MainApp();
		ObservableList<Character> chrList = main.getCharacters();
		
		File file = new File("testFile_SaveAndLoading.xml");
		Character character = new Character("Elon Musk",1,9,10,10,10);
		
		chrList.add(character);
		
		try {
			main.saveCharacterDataToFile(file);
			main.loadCharacterDataFromFile(file);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		ObservableList<Character> newChrList = main.getCharacters();
		
		assertArrayEquals(chrList.toArray(),newChrList.toArray());
	}
	//End testing Save and Load methods
}//end test class
