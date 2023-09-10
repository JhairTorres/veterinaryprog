package com.vet.veterinaryprog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vet {
    private String code;
    private String name;
    private Byte age;
}
