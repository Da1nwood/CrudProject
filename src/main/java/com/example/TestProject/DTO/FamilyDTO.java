package com.example.TestProject.DTO;

import lombok.Data;

import java.util.List;
@Data
public class FamilyDTO {
    Long familyId;

    List<String> accountUsernames;
}
