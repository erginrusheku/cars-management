package carmanagementsystem.service;

import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    public Car addCar(@RequestBody Car car) {
        return carRepository.save(car);
    }


    public Car getCarById(@PathVariable Long id) {
        return carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("\"Car not found with id: \" + id"));
    }

    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return carRepository.findById(id).map(car -> {
            car.setMake(updatedCar.getMake());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            return carRepository.save(car);
        }).orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
    }

    public void deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
    }
}
