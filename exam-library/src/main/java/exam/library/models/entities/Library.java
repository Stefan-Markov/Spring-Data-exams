package exam.library.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "libraries")
public class Library extends BaseEntity {

    @Column(unique = true)
    private String name;
    @Column
    private String location;
    @Column(name = "rating")
    private Integer rating;
    private Set<Book> books;

    public Library() {
    }

    @ManyToMany
    @JoinTable(name = "libraries_books",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @NotNull
    @Length(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(min = 5)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Min(value = 1)
    @Max(value = 10)
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer reading) {
        this.rating = reading;
    }
}
