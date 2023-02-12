package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.Exceptions.ResourceNotFoundExceptions;
import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {

        // get user from db with help of user repo
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExceptions("User wth given Id is not found on server!! : " + userId));
        // fetch rating of the above user from RATING SERVICE
        //http://localhost:8083/ratings/users/7bde22a8-6dbd-4152-8c61-7e91595fa6d0
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/97d87be3-e883-44a9-9028-522968bf8375
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+ rating.getHotelId(), Hotel.class);

            // Using Feign Client method
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            //logger.info("response status code", forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;

        //////////////////////////////////////Using Rest Template

//        // get user from db with help of user repo
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExceptions("User wth given Id is not found on server!! : " + userId));
//        // fetch rating of the above user from RATING SERVICE
//        //http://localhost:8083/ratings/users/7bde22a8-6dbd-4152-8c61-7e91595fa6d0
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//        logger.info("", ratingsOfUser);
//
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
//
//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            //api call to hotel service to get the hotel
//            //http://localhost:8082/hotels/97d87be3-e883-44a9-9028-522968bf8375
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+ rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code", forEntity.getStatusCode());
//
//            //set the hotel to rating
//            rating.setHotel(hotel);
//
//            //return the rating
//            return rating;
//        }).collect(Collectors.toList());
//
//        user.setRatings(ratingList);
//        return user;
    }
}

