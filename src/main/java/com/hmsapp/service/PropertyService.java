package com.hmsapp.service;

import com.hmsapp.entity.Property;
import com.hmsapp.payload.PropertyDto;
import com.hmsapp.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;
    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDto addProperty(PropertyDto dto){
        Property property = mapToEntity(dto);
        Property propertyAdded = propertyRepository.save(property);
    return mapToDto(propertyAdded);
    }

    Property mapToEntity (PropertyDto dto){
        Property property = modelMapper.map(dto, Property.class);
        return property;
    }

    PropertyDto mapToDto (Property property){
        PropertyDto propertydto = modelMapper.map(property, PropertyDto.class);
        return propertydto;
    }


    public List<Property>  getProperty(String searchParam) {
        List<Property> properties = propertyRepository.searchProperty(searchParam);
   return properties;
    }
}
