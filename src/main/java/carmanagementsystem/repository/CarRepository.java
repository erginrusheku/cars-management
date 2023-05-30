package carmanagementsystem.repository;

import carmanagementsystem.CarDTO.CarDTO;
import carmanagementsystem.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // additional custom queries or methods
    CarDTO save(CarDTO carDTO);
}
