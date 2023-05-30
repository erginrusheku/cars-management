package carmanagementsystem.service;

import carmanagementsystem.CarDTO.CarDTO;
import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(Car::toDTO)
                .collect(Collectors.toList());
    }

    public CarDTO addCar(CarDTO carDTO) {
        Car car = new Car(carDTO);
        Car savedCar = carRepository.save(car);
        return savedCar.toDTO();
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return car.toDTO();
    }

    public CarDTO updateCar(Long id, CarDTO updatedCar) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());
        car.setYear(updatedCar.getYear());
        Car savedCar = carRepository.save(car);
        return savedCar.toDTO();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
