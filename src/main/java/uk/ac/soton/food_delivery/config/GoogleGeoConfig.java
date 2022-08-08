package uk.ac.soton.food_delivery.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ShimonZhan
 * @since 2022-05-11 19:17:28
 */
@Configuration
public class GoogleGeoConfig {
    @Value("${google.geo.api}")
    private String geoApi;

    @Bean
    public GeoApiContext getGeoApiContext() {
        return new GeoApiContext.Builder().apiKey(geoApi).build();
    }
}
