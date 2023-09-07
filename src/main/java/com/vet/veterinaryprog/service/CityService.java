package com.vet.veterinaryprog.service;

import com.vet.veterinaryprog.exceptions.VeterinaryException;
import com.vet.veterinaryprog.model.City;
import com.vet.veterinaryprog.model.Vaccine;
import com.vet.veterinaryprog.model.Vet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class CityService {
    private List<Vet> vets;
    private List<City> cities;
    private List<Vaccine> vaccines;

    public CityService(){
        cities = new ArrayList<>();
        cities.add(new City("17001","Manizales"));
        cities.add(new City("66001","Pereira"));
        cities.add(new City("86001","Mocoa"));
        cities.add(new City("05001","Medellin"));
        cities.add(new City("11001","Bogota"));
        cities.add(new City("18001","Florencia"));
        cities.add(new City("41001","Neiva"));
        cities.add(new City("19001","Popayan"));
        cities.add(new City("18094","Belen de los Andaquies"));
    }

    public City findCityById(String id) throws VeterinaryException {
        for(City cityFound : this.getCities()){
            if(cityFound.getCode().equals(id)){
                return cityFound;
            }
        }
        throw new VeterinaryException("La ciudad con "+id+ "no existe");
    }
    public City findCityByName(String description)throws VeterinaryException{
        for (City cities : this.getCities()){
            if (cities.getDescription().equalsIgnoreCase(description)){
                return cities;
            }
        }
        throw new VeterinaryException("La ciudad con "+description+ "no existe");
    }
    public List<City> findCityByLetter(char inicial) throws VeterinaryException{
        List<City> ciudadesEncontradas = new ArrayList<>();
        for (City ciudad : this.getCities()) {
            if (ciudad.getDescription().charAt(0) == Character.toUpperCase(inicial) || ciudad.getDescription().charAt(0) == Character.toLowerCase(inicial)) {
                ciudadesEncontradas.add(ciudad);
            }
        }
        if (ciudadesEncontradas.isEmpty()){
            throw new VeterinaryException("La ciudad con "+inicial+ "no existe");
        }
        else{
            return ciudadesEncontradas;
        }
    }
    public String addCity(City city) throws VeterinaryException{
        if(this.verifyCityExist(city)){
            throw new VeterinaryException("El código ingresado ya existe");
        }
        else{
            this.cities.add(city);

        }
        return "Ciudad adicionada correctamente";
    }

    private boolean verifyCityExist(City city){
        for(City cityAct: this.cities){
            if(city.getCode().equals(cityAct.getCode())){
                return true;
            }
        }
        return false;
    }

    public String updateCity(String code, City city) throws VeterinaryException{
        for(City cityAct : this.cities){
            if(cityAct.getCode().equals(code)){
                cityAct.setDescription(city.getDescription());
                return "Ciudad actualizada correctamente";
            }
        }
        throw new VeterinaryException("El código ingresado no existe");

    }
}
