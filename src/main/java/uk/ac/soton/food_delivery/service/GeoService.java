package uk.ac.soton.food_delivery.service;

import com.google.maps.model.LatLng;

/**
 * @author ShimonZhan
 * @since 2022-05-11 22:09:16
 */
public interface GeoService {
    LatLng address2GeoCode(String address);
}
