package uk.ac.soton.food_delivery.serviceImpl;

import com.google.maps.model.LatLng;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.service.GeoService;

/**
 * @author ShimonZhan
 * @since 2022-05-11 19:12:02
 */
@Service
public class GeoServiceImpl implements GeoService {
//    @Resource
//    private GeoApiContext context;

    @Override
    @SneakyThrows
    public LatLng address2GeoCode(String address) {
        return new LatLng(0,0);
//        GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
//        return results.length == 0? null: results[0].geometry.location;
    }
}
