package com.wolox.training.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String password;

    private String name;

    private LocalDate birthdate;

}
