package carmanagementsystem.controller;

import carmanagementsystem.dto.CarDTO;
import carmanagementsystem.service.CarService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public CarDTO addCar(@RequestBody CarDTO carDTO) {
        return carService.addCar(carDTO);
    }

    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable Long id) throws NotFoundException {
        return carService.getCarById(id);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarDTO updatedCar) throws NotFoundException {
        return carService.updateCar(id, updatedCar);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}

