package exam.library.models.entities.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "characters")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterRootXmlDtos {
    @XmlElement(name = "character")
    List<CharacterXmlDtos> character;

    public CharacterRootXmlDtos() {
    }

    public List<CharacterXmlDtos> getCharacter() {
        return character;
    }

    public void setCharacter(List<CharacterXmlDtos> character) {
        this.character = character;
    }
}
