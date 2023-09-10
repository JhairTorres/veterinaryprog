package com.vet.veterinaryprog.service;
import com.vet.veterinaryprog.exceptions.VeterinaryException;
import com.vet.veterinaryprog.model.City;
import com.vet.veterinaryprog.model.Vaccine;
import com.vet.veterinaryprog.model.Vet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return "Veterinario adicionado correctamente";
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
                vetAct.setAge(vets.getAge());
                return "Ciudad actualizada correctamente";
            }
        }
        throw new VeterinaryException("El código ingresado no existe");

    }
    public Map<String, Integer> countVetsByAgeRange() {
        Map<String, Integer> ageRangeCounts = new HashMap<>();

        int vet1to10 = 0;
        int vet11to20 = 0;
        int vet21to30 = 0;
        int vet31AndAbove = 0;

        for (Vet vet : vets) {
            int age = Integer.parseInt(String.valueOf(vet.getAge()));
            if (age >= 1 && age <= 10) {
                vet1to10++;
            } else if (age >= 11 && age <= 20) {
                vet11to20++;
            } else if (age >= 21 && age <= 30) {
                vet21to30++;
            } else {
                vet31AndAbove++;
            }
        }

        ageRangeCounts.put("1-10", vet1to10);
        ageRangeCounts.put("11-20", vet11to20);
        ageRangeCounts.put("21-30", vet21to30);
        ageRangeCounts.put("31+", vet31AndAbove);

        return ageRangeCounts;
    }
    public List<Vet> getInterleavedVetList(String message) {
        List<Vet> pairVets = new ArrayList<>();
        List<Vet> impairVets = new ArrayList<>();
        List<Vet> finalVets = new ArrayList<>();

        for (Vet vet : this.vets) {
            if (vet.getAge() % 2 == 0) {
                pairVets.add(vet);
            } else {
                impairVets.add(vet);
            }
        }

        while (!pairVets.isEmpty() || !impairVets.isEmpty()) {
            if ("pair".equalsIgnoreCase(message)) {
                if (!pairVets.isEmpty()) {
                    finalVets.add(pairVets.get(0));
                    pairVets.remove(0);
                }
                if (!impairVets.isEmpty()) {
                    finalVets.add(impairVets.get(0));
                    impairVets.remove(0);
                }
            }
            if ("impair".equalsIgnoreCase(message)) {
                if (!impairVets.isEmpty()) {
                    finalVets.add(impairVets.get(0));
                    impairVets.remove(0);
                }
                if (!pairVets.isEmpty()) {
                    finalVets.add(pairVets.get(0));
                    pairVets.remove(0);
                }
            }
        }

        return finalVets;
    }

}
