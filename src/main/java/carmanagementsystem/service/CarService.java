package carmanagementsystem.service;

import carmanagementsystem.dto.CarDTO;
import carmanagementsystem.mapper.CarMapper;
import carmanagementsystem.model.Car;
import carmanagementsystem.repository.CarRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Autowired
    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarDTO> carDTOs = new ArrayList<>();

        for (Car car : cars) {
            CarDTO carDTO = carMapper.toDto(car);
            carDTOs.add(carDTO);
        }

        return carDTOs;
    }

    public CarDTO addCar(CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        Car savedCar = carRepository.save(car);

        return carMapper.toDto(savedCar);
    }

    public CarDTO getCarById(Long id) throws NotFoundException {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        return carMapper.toDto(car);
    }

    public CarDTO updateCar(Long id, CarDTO updatedCar) throws NotFoundException {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + id));

        existingCar.setMake(updatedCar.getMake());
        existingCar.setModel(updatedCar.getModel());
        existingCar.setYear(updatedCar.getYear());

        Car savedCar = carRepository.save(existingCar);

        return carMapper.toDto(savedCar);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}



