package com.hmsapp.controller;

import com.hmsapp.payload.CityDto;
import com.hmsapp.payload.CountryDto;
import com.hmsapp.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/City")
public class CityController {
    private CityService cityService;
    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    //api/v1/city/addCity
    @PostMapping("/addCity")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto city){
        CityDto addedCity = cityService.addCity(city);
    return new ResponseEntity<>(addedCity, HttpStatus.OK);
    }

    //api/v1/city/delete?id=1
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCountry(@RequestParam Long id){
        cityService.deleteCity(id);
        return new ResponseEntity<>("City deleted successfully", HttpStatus.OK);
    }
}
