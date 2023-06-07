package carmanagementsystem.mapper;

import carmanagementsystem.dto.CarDTO;
import carmanagementsystem.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO toDto(Car car) {

        CarDTO carDto = new CarDTO();
        carDto.setId(car.getId());
        carDto.setMake(car.getMake());
        carDto.setModel(car.getModel());
        carDto.setYear(car.getYear());

        return carDto;
    }

    public Car toEntity(CarDTO carDto) {

        Car car = new Car();
        car.setId(carDto.getId());
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());

        return car;
    }

}

