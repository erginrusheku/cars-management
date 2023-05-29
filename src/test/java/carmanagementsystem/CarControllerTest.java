package carmanagementsystem;

import carmanagementsystem.controller.CarController;
import carmanagementsystem.model.Car;
import carmanagementsystem.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllCars() throws Exception {
        List<Car> cars = Arrays.asList(
                new Car(1L, "Toyota", "Camry", 2020),
                new Car(2L, "Honda", "Accord", 2019)
        );
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(2020))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].make").value("Honda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Accord"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value(2019));

        verify(carService, times(1)).getAllCars();
    }

    @Test
    public void testGetCarById() throws Exception {
        Long id = 1L;
        Car car = new Car(id, "Toyota", "Camry", 2020);
        when(carService.getCarById(id)).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2020));

        verify(carService, times(1)).getCarById(id);
    }

    @Test
    public void testAddCar() throws Exception {
        Car car = new Car(null, "Toyota", "Camry", 2020);
        Car savedCar = new Car(1L, "Toyota", "Camry", 2020);
        when(carService.addCar(any(Car.class))).thenReturn(savedCar);

        String carJson = objectMapper.writeValueAsString(car);

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Camry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2020));

        verify(carService, times(1)).addCar(any(Car.class));
    }

    @Test
    public void testUpdateCar() throws Exception {
        Long id = 1L;
        Car updatedCar = new Car(id, "Toyota", "Corolla", 2021);
        when(carService.updateCar(eq(id), any(Car.class))).thenReturn(updatedCar);

        String updatedCarJson = objectMapper.writeValueAsString(updatedCar);

        mockMvc.perform(MockMvcRequestBuilders.put("/cars/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCarJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2021));

        verify(carService, times(1)).updateCar(eq(id), any(Car.class));
    }

    @Test
    public void testDeleteCar() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(carService, times(1)).deleteCar(id);
    }
}
