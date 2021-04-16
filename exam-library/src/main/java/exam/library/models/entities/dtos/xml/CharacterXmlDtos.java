package exam.library.models.entities.dtos.xml;

import exam.library.config.LocalDateTimeAdapter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "character")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterXmlDtos {

    @XmlElement
    private int age;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDate birthday;
    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "middle-name")
    private String middleName;
    @XmlElement
    private String role;
    @XmlElement(name = "book")
    private BookDto bookDto;

    public CharacterXmlDtos() {
    }
    @Min(value = 10)
    @Max(value = 66)
    @NotNull
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @NotNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    @Length(min = 3)
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Length(min = 3)
    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @NotNull
    @Length(min = 1)
    @Length(max = 2)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @NotNull
    @Length(min = 5)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }
}
