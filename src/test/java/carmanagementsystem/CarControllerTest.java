package carmanagementsystem;

import carmanagementsystem.CarDTO.CarDTO;
import carmanagementsystem.controller.CarController;
import carmanagementsystem.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testGetAllCars() throws Exception {
        // Arrange
        CarDTO car1 = new CarDTO(1L, "Toyota", "Camry", 2022);
        CarDTO car2 = new CarDTO(2L, "Honda", "Accord", 2023);
        List<CarDTO> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(cars.size()));
    }

    @Test
    public void testAddCar() throws Exception {
        // Arrange
        CarDTO car = new CarDTO(null, "Toyota", "Camry", 2022);
        CarDTO savedCar = new CarDTO(1L, "Toyota", "Camry", 2022);

        when(carService.addCar(car)).thenReturn(savedCar);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2022}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedCar.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value(savedCar.getMake()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(savedCar.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(savedCar.getYear()));
    }

    // Add more tests for other controller methods (e.g., getCarById, updateCar, deleteCar)
}
