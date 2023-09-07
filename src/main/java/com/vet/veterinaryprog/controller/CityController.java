package com.vet.veterinaryprog.controller;

import com.vet.veterinaryprog.controller.dto.ResponseDTO;
import com.vet.veterinaryprog.exceptions.VeterinaryException;
import com.vet.veterinaryprog.model.City;
import com.vet.veterinaryprog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCities(){
        return new ResponseEntity<>(
                new ResponseDTO(HttpStatus.OK.value(),
                        cityService.getCities(),
                        null),
                HttpStatus.OK);
    }

    @GetMapping(path= "/id/{id}")
    public ResponseEntity<ResponseDTO> getCityById(@PathVariable String id){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            cityService.findCityById(id),
                            null),
                    HttpStatus.OK);
        }
        catch (VeterinaryException e){
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(),
                            null,errors),
                    HttpStatus.OK);
        }
    }
    @GetMapping(path = "/description/{description}")
    public ResponseEntity<ResponseDTO> getCityByName(@PathVariable String description){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            cityService.findCityByName(description),
                            null),
                    HttpStatus.OK);
        }
        catch (VeterinaryException er) {
            List<String> errors = new ArrayList<>();
            errors.add(er.getMessage());
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(),
                            null, errors),
                    HttpStatus.OK);
        }
    }
    @GetMapping(path = "/letter/{description}")
    public ResponseEntity<ResponseDTO> getCityByLetter(@PathVariable char description){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            cityService.findCityByLetter(description),
                            null),
                    HttpStatus.OK);
        }
        catch (VeterinaryException er) {
            List<String> errors = new ArrayList<>();
            errors.add(er.getMessage());
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(),
                            null, errors),
                    HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> createCity(
            @RequestBody City city){
        try {

            return new ResponseEntity<>(
                    new ResponseDTO(
                            HttpStatus.OK.value(),
                            cityService.addCity(city),
                            null
                    ), HttpStatus.OK
            );
        } catch (VeterinaryException e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.CONFLICT.value(),
                            null,
                            errors),
                    HttpStatus.OK);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseDTO> updateCity(
            @PathVariable String id, @RequestBody City city){
        try {

            return new ResponseEntity<>(
                    new ResponseDTO(
                            HttpStatus.OK.value(),
                            cityService.updateCity(id,city),
                            null
                    ), HttpStatus.OK
            );
        } catch (VeterinaryException e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(),
                            null,
                            errors),
                    HttpStatus.OK);
        }
    }
}
