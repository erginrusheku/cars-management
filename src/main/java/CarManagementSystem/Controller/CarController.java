package CarManagementSystem.Controller;
import CarManagementSystem.Model.Car;
import CarManagementSystem.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

;import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
   private final CarService carService;

   @Autowired
    public CarController(CarService carService){
       this.carService = carService;
   }

   @GetMapping
    public List<Car> getAllCars(){
       return carService.getAllCars();
   }
   @PostMapping
    public Car addCar(@RequestBody Car car){
       return carService.addCar(car);
   }
   @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id){
       return carService.getCarById(id);
   }
   @PutMapping("/{id}")
    public  Car updateCar(@PathVariable Long id,@RequestBody Car updatedCar){
       return carService.updateCar(id,updatedCar);
   }
   @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){
       carService.deleteCar(id);
   }
}

