package com.hmsapp.service;

import com.hmsapp.entity.City;
import com.hmsapp.entity.Country;
import com.hmsapp.payload.CityDto;
import com.hmsapp.payload.CountryDto;
import com.hmsapp.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private CityRepository cityRepository;
    private ModelMapper modelMapper;
    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityDto addCity(CityDto citydto){
        City city = maptoEntity(citydto);
        City citySaved = cityRepository.save(city);
        return mapToDto(citySaved);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    City maptoEntity(CityDto citydto) {
        City city = modelMapper.map(citydto,City.class);
        return city;
    }
    CityDto mapToDto(City city) {
        CityDto citydto = modelMapper.map(city,CityDto.class);
        return citydto;
    }

}
