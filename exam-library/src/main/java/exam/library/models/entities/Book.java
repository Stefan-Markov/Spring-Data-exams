package exam.library.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column
    private String name;
    @Column
    private int edition;
    @Column
    private LocalDate written;
    @Column
    private String description;
    private Author author;
    private Set<Library> libraries;

    public Book() {
    }

    @ManyToMany(mappedBy = "books")
    public Set<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }

    @ManyToOne
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @NotNull
    @Length(min = 5)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(value = 1)
    @Max(value = 5)
    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @NotNull
    public LocalDate getWritten() {
        return written;
    }

    public void setWritten(LocalDate written) {
        this.written = written;
    }

    @Length(min = 100)
    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
