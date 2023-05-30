package carmanagementsystem.model;


import carmanagementsystem.CarDTO.CarDTO;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int year;

    public Car() {}

    public Car(CarDTO carDTO) {
        this.id = carDTO.getId();
        this.make = carDTO.getMake();
        this.model = carDTO.getModel();
        this.year = carDTO.getYear();
    }

    public Car(long id, String make, String model, int year) {
    }

    public CarDTO toDTO() {
        return new CarDTO(id, make, model, year);
    }
}
