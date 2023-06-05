package carmanagementsystem;

import carmanagementsystem.controller.CarController;
import carmanagementsystem.dto.CarDTO;
import carmanagementsystem.mapper.CarMapper;
import carmanagementsystem.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCars() throws Exception {
        // Arrange
        CarDTO carDto1 = new CarDTO(1L, "Toyota", "Camry", 2022);
        CarDTO carDto2 = new CarDTO(2L, "Honda", "Accord", 2023);
        List<CarDTO> carDTOs = Arrays.asList(carDto1, carDto2);

        when(carService.getAllCars()).thenReturn(carDTOs);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(2022))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].make").value("Honda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Accord"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value(2023));
    }

    @Test
    public void testAddCar() throws Exception {
        // Arrange
        CarDTO carDto = new CarDTO(1L, "Toyota", "Camry", 2022);

        when(carService.addCar(carDto)).thenReturn(carDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"make\": \"Toyota\", \"model\": \"Camry\", \"year\": 2022 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2022));
    }

    @Test
    public void testGetCarById() throws Exception {
        // Arrange
        Long carId = 1L;
        CarDTO carDto = new CarDTO(carId, "Toyota", "Camry", 2022);

        when(carService.getCarById(carId)).thenReturn(carDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2022));
    }

    @Test
    public void testUpdateCar() throws Exception {
        // Arrange
        Long carId = 1L;
        CarDTO updatedCarDto = new CarDTO(carId, "Honda", "Accord", 2023);

        when(carService.updateCar(carId, updatedCarDto)).thenReturn(updatedCarDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"make\": \"Honda\", \"model\": \"Accord\", \"year\": 2023 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Honda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Accord"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2023));
    }

    @Test
    public void testDeleteCar() throws Exception {
        // Arrange
        Long carId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


