package exam.library.models.entities.dtos.xml.libraryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "libraries")
@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryRootSeedDtos {
    @XmlElement(name = "library")
    List< LibrariesDtos> libraries;

    public LibraryRootSeedDtos() {
    }

    public List<LibrariesDtos> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<LibrariesDtos> libraries) {
        this.libraries = libraries;
    }
}
