package carmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarDTO {

    private Long id;
    private String make;
    private String model;
    private int year;
}

