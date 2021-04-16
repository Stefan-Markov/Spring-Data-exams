package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDtos {
    @XmlElement
    @Expose
    private String name;

    @XmlElement(name = "picture")
    @Expose
    private PictureSeedDtos picture;

    public TeamSeedDtos() {
    }

    @Length(min = 3,max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PictureSeedDtos getPicture() {
        return picture;
    }

    public void setPicture(PictureSeedDtos picture) {
        this.picture = picture;
    }
}
