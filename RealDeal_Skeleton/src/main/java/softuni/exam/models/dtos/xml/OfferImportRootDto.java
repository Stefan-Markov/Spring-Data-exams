package softuni.exam.models.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportRootDto {
    @XmlElement(name = "offer")
    private List<OfferImportDto> offerdtos;

    public OfferImportRootDto() {
    }

    public List<OfferImportDto> getOfferdtos() {
        return offerdtos;
    }

    public void setOfferdtos(List<OfferImportDto> offerdtos) {
        this.offerdtos = offerdtos;
    }
}
