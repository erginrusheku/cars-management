package CarManagementSystem.Service;

import CarManagementSystem.Model.Car;
import CarManagementSystem.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService (CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    @PostMapping
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id){
        return carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("\"Car not found with id: \" + id"));
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar){
        return carRepository.findById(id)
                .map(car -> {
                    car.setMake(updatedCar.getMake());
                    car.setModel(updatedCar.getModel());
                    car.setYear(updatedCar.getYear());
                    return carRepository.save(car);
                }) .orElseThrow(() -> new IllegalArgumentException("Car not found with id: "+id));
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){
        carRepository.deleteById(id);
    }
}
