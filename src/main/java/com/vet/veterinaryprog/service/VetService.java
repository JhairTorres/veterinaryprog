package com.vet.veterinaryprog.service;

import com.vet.veterinaryprog.exceptions.VeterinaryException;
import com.vet.veterinaryprog.model.City;
import com.vet.veterinaryprog.model.Vaccine;
import com.vet.veterinaryprog.model.Vet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class VetService {
    private List<Vet> vets;
    private List<City> cities;
    private List<Vaccine> vaccines;

    public VetService() {
        vets = new ArrayList<>();
        vets.add(new Vet("1111", "Jaime", (byte) 28));
        vets.add(new Vet("2222", "Sergio", (byte) 22));
        vets.add(new Vet("3333", "Sebastian", (byte) 25));
        vets.add(new Vet("4444", "Valeria", (byte) 26));
        vets.add(new Vet("5555", "Santiago", (byte) 45));
        vets.add(new Vet("6666", "Valentina", (byte) 33));
    }

    public Vet findVetById(String code) throws VeterinaryException {
        for (Vet vetFound : this.getVets()) {
            if (vetFound.getCode().equals(code)) {
                return vetFound;
            }
        }
        throw new VeterinaryException("El veterinario con " + code + "no existe");
    }

    public Vet findVetByName(String name) throws VeterinaryException {
        for (Vet vets : this.getVets()) {
            if (vets.getName().equalsIgnoreCase(name)) {
                return vets;
            }
        }
        throw new VeterinaryException("El veterinario con " + name + "no existe");
    }

    public List<Vet> findVetByLetter(char inicial) throws VeterinaryException {
        List<Vet> vetsEncontradas = new ArrayList<>();
        for (Vet vets : this.getVets()) {
            if (vets.getName().charAt(0) == Character.toUpperCase(inicial) || vets.getName().charAt(0) == Character.toLowerCase(inicial)) {
                vetsEncontradas.add(vets);
            }
        }
        if (vetsEncontradas.isEmpty()) {
            throw new VeterinaryException("El veterinario con " + inicial + "no existe");
        } else {
            return vetsEncontradas;
        }
    }

    public List<Vet> findVetByMinAge() {
        List<Vet> vetMinAge = new ArrayList<>();
        int minAge = Integer.MAX_VALUE;

        for (Vet vet : vets) {
            int age = Integer.parseInt(String.valueOf(vet.getAge()));
            if (age < minAge) {
                minAge = age;
                vetMinAge.clear();
                vetMinAge.add(vet);
            } else if (age == minAge) {
                vetMinAge.add(vet);
            }
        }
        return vetMinAge;

    }
    public List<Vet> findVetBetAge(int minAge, int maxAge) {
        List<Vet> vetBetAges = new ArrayList<>();

        for (Vet vet : vets) {
            int age = Integer.parseInt(String.valueOf(vet.getAge()));
            if (age >= minAge && age <= maxAge) {
                vetBetAges.add(vet);
            }
        }

        return vetBetAges;
    }
    public String addVet(Vet vets) throws VeterinaryException{
        if(this.verifyVetExist(vets)){
            throw new VeterinaryException("El código ingresado ya existe");
        }
        else{
            this.vets.add(vets);

        }
        return "Ciudad adicionada correctamente";
    }

    private boolean verifyVetExist(Vet vets){
        for(Vet vetAct: this.vets){
            if(vets.getCode().equals(vetAct.getCode())){
                return true;
            }
        }
        return false;
    }

    public String updateVet(String code, Vet vets) throws VeterinaryException{
        for(Vet vetAct : this.vets){
            if(vetAct.getCode().equals(code)){
                vetAct.setName(vets.getName());
                return "Ciudad actualizada correctamente";
            }
        }
        throw new VeterinaryException("El código ingresado no existe");

    }
}
