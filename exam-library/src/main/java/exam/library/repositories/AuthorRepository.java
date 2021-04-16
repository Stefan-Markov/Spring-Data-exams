package exam.library.repositories;

import exam.library.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findAllByFirstNameAndLastName(String first, String last);
    Author findById(int id);
}
