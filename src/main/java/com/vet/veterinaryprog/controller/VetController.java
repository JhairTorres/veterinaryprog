package com.vet.veterinaryprog.controller;

import com.vet.veterinaryprog.controller.dto.ResponseDTO;
import com.vet.veterinaryprog.exceptions.VeterinaryException;
import com.vet.veterinaryprog.model.City;
import com.vet.veterinaryprog.model.Vet;
import com.vet.veterinaryprog.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="vet")
public class VetController {

    @Autowired
    private VetService veterinaryService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllVets(){
        return new ResponseEntity<>(
                new ResponseDTO(HttpStatus.OK.value(),
                        veterinaryService.getVets(),
                        null),
                HttpStatus.OK);
    }

    @GetMapping(path= "/id/{id}")
    public ResponseEntity<ResponseDTO> getVetById(@PathVariable String id){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            veterinaryService.findVetById(id),
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
    public ResponseEntity<ResponseDTO> getVetByName(@PathVariable String description){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            veterinaryService.findVetByName(description),
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
    @GetMapping(path = "/letter/{name}")
    public ResponseEntity<ResponseDTO> getVetByLetter(@PathVariable char name){
        try {
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.OK.value(),
                            veterinaryService.findVetByLetter(name),
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
    @GetMapping(path = "/min_age")
    public ResponseEntity<ResponseDTO> getVetWithMinimumAge() {
        List<Vet> vetsWithMinimumAge = veterinaryService.findVetByMinAge();

        if (vetsWithMinimumAge.isEmpty()) {
            List<String> errors = new ArrayList<>();
            errors.add("No veterinarians found.");
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(),
                            null, errors),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseDTO(HttpStatus.OK.value(), vetsWithMinimumAge,
                        null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/ran_age/{min}/{max}")
    public ResponseEntity<ResponseDTO> getVetsInAgeRange(
            @PathVariable int min,
            @PathVariable int max
    ) {
        if (min > max) {
            List<String> errors = new ArrayList<>();
            errors.add("Invalid age range.");
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.BAD_REQUEST.value(), null, errors),
                    HttpStatus.OK);
        }

        List<Vet> vetsInAgeRange = veterinaryService.findVetBetAge(min, max);

        if (vetsInAgeRange.isEmpty()) {
            List<String> errors = new ArrayList<>();
            errors.add("No veterinarians found in the specified age range.");
            return new ResponseEntity<>(
                    new ResponseDTO(HttpStatus.NOT_FOUND.value(), null, errors),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseDTO(HttpStatus.OK.value(), vetsInAgeRange, null),
                HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> createVets(
            @RequestBody Vet vets){
        try {

            return new ResponseEntity<>(
                    new ResponseDTO(
                            HttpStatus.OK.value(),
                            veterinaryService.addVet(vets),
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
    public ResponseEntity<ResponseDTO> updateVets(
            @PathVariable String id, @RequestBody Vet vets){
        try {

            return new ResponseEntity<>(
                    new ResponseDTO(
                            HttpStatus.OK.value(),
                            veterinaryService.updateVet(id,vets),
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
