package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedRootDtos {

    @XmlElement(name = "team")
    private List<TeamSeedDtos> teams;

    public TeamSeedRootDtos() {
    }

    public List<TeamSeedDtos> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamSeedDtos> teams) {
        this.teams = teams;
    }
}
