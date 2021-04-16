package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("select  c from Car  c order by c.pictures.size desc, c.make")
    Set<Car> exportCars();
}
