package exam.library.repositories;

import exam.library.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findBookByName(String name);
    Book findById(int id);
}
