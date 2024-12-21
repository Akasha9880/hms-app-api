package com.hmsapp.controller;

import com.hmsapp.entity.Property;
import com.hmsapp.payload.PropertyDto;
import com.hmsapp.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    //api/v1/property/addProperty
    @PostMapping("/addProperty")
    public ResponseEntity<PropertyDto>  addProperty(@RequestBody  PropertyDto dto){
        PropertyDto propertyAdded = propertyService.addProperty(dto);
        System.out.println("hello");
       return new ResponseEntity<>(propertyAdded, HttpStatus.CREATED);

        }

        //http://localhost:8080/api/v1/property/{searchParam}
        @GetMapping("/{searchParam}")
        public ResponseEntity<List<Property>>  searchProperty(@PathVariable String searchParam){

            List<Property> property = propertyService.getProperty(searchParam);
            return new ResponseEntity<>(property,HttpStatus.OK);
    }
    }

