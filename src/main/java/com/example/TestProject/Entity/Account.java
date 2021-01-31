package com.example.TestProject.Entity;

import com.example.TestProject.Entity.Enums.TypeOfSegment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userdata_id")
    private UserData userdata;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountCredentials_id")
    private AccountCredentials accountCredentials;

    @ManyToOne
    @JoinColumn(name="family_id")
    private Family family;

    @ElementCollection(targetClass = TypeOfSegment.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @UniqueElements
    @JsonIgnore
    private Set<TypeOfSegment> typeOfSegments = new HashSet<>();

}
