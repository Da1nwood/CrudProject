package com.example.TestProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "family")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy="family")
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();

    public void addAccountToFamily(Account account){
        accounts.add(account);
        account.setFamily(this);
    }
    public void removeAccountFromFamily(Account account){
        accounts.remove(account);
        account.setFamily(null);
    }
}
