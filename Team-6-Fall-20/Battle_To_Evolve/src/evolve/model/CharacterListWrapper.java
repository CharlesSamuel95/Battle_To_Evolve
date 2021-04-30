package evolve.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "characters")
public class CharacterListWrapper {
	
	private List<Character> characters;
	
	@XmlElement(name = "character")
	public List<Character> getCharacters(){
		return characters;
	}
	
	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
	
}
