package com.example.TestProject.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;
@Data
public class UserDataDTO {
    private String firstname;

    private String secondName;

    private String thirdName;

    @JsonFormat(pattern="dd-MM-yyyy")
    Date dateOfBirth;

    private String maritalStatus;

    private String educationType;

}
