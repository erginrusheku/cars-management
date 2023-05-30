package carmanagementsystem;

import carmanagementsystem.CarDTO.CarDTO;
import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import carmanagementsystem.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void testGetAllCars() {
        // Arrange
        Car car1 = new Car(1L, "Toyota", "Camry", 2022);
        Car car2 = new Car(2L, "Honda", "Accord", 2023);
        List<Car> cars = Arrays.asList(car1, car2);

        when(carRepository.findAll()).thenReturn(cars);

        // Act
        List<CarDTO> result = carService.getAllCars();

        // Assert
        assertEquals(cars.size(), result.size());
        // You can add more assertions to verify the correctness of the result
    }

    @Test
    public void testAddCar() {
        // Arrange
        CarDTO carDTO = new CarDTO(null, "Toyota", "Camry", 2022);
        Car savedCar = new Car(1L, "Toyota", "Camry", 2022);

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        // Act
        CarDTO result = carService.addCar(carDTO);

        // Assert
        assertEquals(savedCar.getId(), result.getId());
        assertEquals(savedCar.getMake(), result.getMake());
        assertEquals(savedCar.getModel(), result.getModel());
        assertEquals(savedCar.getYear(), result.getYear());
    }

    @Test
    public void testGetCarById() {
        // Arrange
        Long carId = 1L;
        Car car = new Car(carId, "Toyota", "Camry", 2022);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        // Act
        CarDTO result = carService.getCarById(carId);

        // Assert
        assertEquals(car.getId(), result.getId());
        assertEquals(car.getMake(), result.getMake());
        assertEquals(car.getModel(), result.getModel());
        assertEquals(car.getYear(), result.getYear());
    }

    @Test
    public void testUpdateCar() {
        // Arrange
        Long carId = 1L;
        Car existingCar = new Car(carId, "Toyota", "Camry", 2022);
        CarDTO updatedCarDTO = new CarDTO(carId, "Honda", "Accord", 2023);
        Car updatedCar = new Car(carId, "Honda", "Accord", 2023);

        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        // Act
        CarDTO result = carService.updateCar(carId, updatedCarDTO);

        // Assert
        assertEquals(updatedCar.getId(), result.getId());
        assertEquals(updatedCar.getMake(), result.getMake());
        assertEquals(updatedCar.getModel(), result.getModel());
        assertEquals(updatedCar.getYear(), result.getYear());
    }

    @Test
    public void testDeleteCar() {
        // Arrange
        Long carId = 1L;

        // Act
        carService.deleteCar(carId);

        // Assert
        verify(carRepository, times(1)).deleteById(carId);
    }
}
