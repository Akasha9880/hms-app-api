package com.hmsapp.service;

import com.hmsapp.entity.Country;
import com.hmsapp.entity.User;
import com.hmsapp.payload.CountryDto;
import com.hmsapp.payload.UserDto;
import com.hmsapp.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private CountryRepository countryRepository;
    private ModelMapper modelMapper;
    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto addCountry(CountryDto countrydto){
        Country country = mapToEntity(countrydto);
        Country countrySaved = countryRepository.save(country);
        return mapToDto(countrySaved);
    }

    Country mapToEntity( CountryDto countrydto) {
        Country country = modelMapper.map(countrydto, Country.class);
        return country;
    }
    CountryDto mapToDto(Country country) {
        CountryDto countrydto = modelMapper.map(country,CountryDto.class);
        return countrydto;
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
