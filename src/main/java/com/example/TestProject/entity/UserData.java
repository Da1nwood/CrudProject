package com.example.TestProject.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString(exclude = "account")
@Table(name = "userdata")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userdata_id")
    private Long id;

    @Column(name = "firstname")
    private String firstname = "";

    @Column(name = "secondName")
    private String secondName = "";

    @Column(name = "thirdName")
    private String thirdName = "";

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    Date dateOfBirth;

    @Column(name = "maritalStatus")
    private String maritalStatus = "";
    @Column(name = "educationType")
    private String educationType = "";

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;

}
