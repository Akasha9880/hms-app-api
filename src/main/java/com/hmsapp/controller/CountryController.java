package com.hmsapp.controller;

import com.hmsapp.payload.CountryDto;
import com.hmsapp.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Country")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    //api/v1/Country/addCountry
    @PostMapping("/addCountry")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto countrydto){

        CountryDto countryAdded = countryService.addCountry(countrydto);// Code to fetch country details and return it as a CountryDto
    return new ResponseEntity<>(countryAdded, HttpStatus.CREATED);
    }

    //api/v1/Country/delete?id=1
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id){
        countryService.deleteCountry(id);
        return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
    }

}
