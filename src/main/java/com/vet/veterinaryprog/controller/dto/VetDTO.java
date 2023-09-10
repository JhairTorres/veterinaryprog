package com.vet.veterinaryprog.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VetDTO {
    private byte quantity;
    private String range;
}
