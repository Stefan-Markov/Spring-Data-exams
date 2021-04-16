package exam.library.repositories;


import exam.library.models.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Character findByFirstNameAndLastName(String first, String last);

    @Query("select c from Character c where c.age >=32 order by " +
            " c.book.name asc , c.lastName desc , c.age asc ")
    List<Character> findAllByAge();
}
