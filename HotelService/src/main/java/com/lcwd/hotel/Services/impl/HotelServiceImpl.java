package com.lcwd.hotel.Services.impl;

import com.lcwd.hotel.Services.HotelService;
import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.repositories.HotelRepositories;
import com.lcwd.hotel.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepositories hotelRepositories;

    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepositories.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepositories.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found !!"));
    }
}
