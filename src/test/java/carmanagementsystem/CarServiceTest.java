package carmanagementsystem;

import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import carmanagementsystem.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carService = new CarService(carRepository);
    }

    @Test
    void getAllCars() {
        List<Car> cars = Arrays.asList(
                new Car(1L, "Toyota", "Camry", 2020),
                new Car(2L, "Honda", "Accord", 2019)
        );
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Toyota", result.get(0).getMake());
        Assertions.assertEquals("Camry", result.get(0).getModel());
        Assertions.assertEquals(2020, result.get(0).getYear());
        Assertions.assertEquals("Honda", result.get(1).getMake());
        Assertions.assertEquals("Accord", result.get(1).getModel());
        Assertions.assertEquals(2019, result.get(1).getYear());
    }

    @Test
    void getCarById() {
        Long id = 1L;
        Car car = new Car(id, "Toyota", "Camry", 2020);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        Car result = carService.getCarById(id);

        Assertions.assertEquals("Toyota", result.getMake());
        Assertions.assertEquals("Camry", result.getModel());
        Assertions.assertEquals(2020, result.getYear());
    }

    @Test
    void addCar() {
        Car car = new Car(null, "Toyota", "Camry", 2020);
        Car savedCar = new Car(1L, "Toyota", "Camry", 2020);
        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        Car result = carService.addCar(car);

        Assertions.assertEquals("Toyota", result.getMake());
        Assertions.assertEquals("Camry", result.getModel());
        Assertions.assertEquals(2020, result.getYear());
    }

    @Test
    void updateCar() {
        Long id = 1L;
        Car existingCar = new Car(id, "Toyota", "Camry", 2020);
        Car updatedCar = new Car(id, "Toyota", "Corolla", 2021);
        when(carRepository.findById(id)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        Car result = carService.updateCar(id, updatedCar);

        Assertions.assertEquals("Toyota", result.getMake());
        Assertions.assertEquals("Corolla", result.getModel());
        Assertions.assertEquals(2021, result.getYear());
    }

    @Test
    void deleteCar() {
        Long id = 1L;

        Assertions.assertDoesNotThrow(() -> carService.deleteCar(id));

        verify(carRepository, times(1)).deleteById(id);
    }
}
