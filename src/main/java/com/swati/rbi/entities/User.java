package com.swati.rbi.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ID;

    private String name;

    private String email;

    private String password;


}
