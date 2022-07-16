package com.example.mail.test1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee{
    private int id;
    private String name;
    private int age;
    private double salary;

    //  private User user;


}