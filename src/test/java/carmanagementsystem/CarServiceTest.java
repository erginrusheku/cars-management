package carmanagementsystem;

import carmanagementsystem.dto.CarDTO;
import carmanagementsystem.mapper.CarMapper;
import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import carmanagementsystem.service.CarService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCars() {
        // Arrange
        Car car1 = new Car(1L, "Toyota", "Camry", 2022);
        Car car2 = new Car(2L, "Honda", "Accord", 2023);
        List<Car> cars = Arrays.asList(car1, car2);

        when(carRepository.findAll()).thenReturn(cars);
        when(carMapper.toDto(car1)).thenReturn(new CarDTO(1L, "Toyota", "Camry", 2022));
        when(carMapper.toDto(car2)).thenReturn(new CarDTO(2L, "Honda", "Accord", 2023));

        // Act
        List<CarDTO> result = carService.getAllCars();

        // Assert
        assertEquals(cars.size(), result.size());
    }






    @Test
    public void testAddCar() {
        // Arrange
        CarDTO carDTO = new CarDTO(1L, "Toyota", "Camry", 2022);
        Car car = new Car(1L, "Toyota", "Camry", 2022);

        when(carMapper.toEntity(carDTO)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(carDTO);

        // Act
        CarDTO result = carService.addCar(carDTO);

        // Assert
        assertEquals(carDTO, result);
    }

    @Test
    public void testGetCarById() throws NotFoundException {
        // Arrange
        Long carId = 1L;
        Car car = new Car(carId, "Toyota", "Camry", 2022);
        CarDTO carDTO = new CarDTO(carId, "Toyota", "Camry", 2022);

        when(carRepository.findById(carId)).thenReturn(java.util.Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(carDTO);

        // Act
        CarDTO result = carService.getCarById(carId);

        // Assert
        assertEquals(carDTO, result);
    }

    @Test
    public void testUpdateCar() throws NotFoundException {
        // Arrange
        Long carId = 1L;
        CarDTO updatedCarDTO = new CarDTO(carId, "Honda", "Accord", 2023);
        Car updatedCar = new Car(carId, "Honda", "Accord", 2023);

        when(carRepository.findById(carId)).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);
        when(carMapper.toDto(updatedCar)).thenReturn(updatedCarDTO);

        // Act
        CarDTO result = carService.updateCar(carId, updatedCarDTO);

        // Assert
        assertEquals(updatedCarDTO, result);
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




