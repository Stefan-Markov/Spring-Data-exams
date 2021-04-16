package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureSeedRootDtos {
    @XmlElement(name = "picture")
    List<PictureSeedDtos> pictures;

    public PictureSeedRootDtos() {
    }

    public List<PictureSeedDtos> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureSeedDtos> pictures) {
        this.pictures = pictures;
    }
}
