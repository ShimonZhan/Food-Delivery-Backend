package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.entity.DTO.CustomerAddressDTO;
import uk.ac.soton.food_delivery.enums.AddressType;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.AddressMapper;
import uk.ac.soton.food_delivery.service.AddressService;
import uk.ac.soton.food_delivery.service.GeoService;
import uk.ac.soton.food_delivery.service.RestaurantService;
import uk.ac.soton.food_delivery.service.UserService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    private RestaurantService restaurantService;

    @Resource
    private UserService userService;

    @Resource
    private GeoService geoService;

    @Override
    public R updateRestaurantAddress(Address address) {
        Restaurant restaurant = restaurantService.getById(address.getRestaurantId());
        if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        address.setType(AddressType.RESTAURANT);
        saveOrUpdate(address);
        restaurant.setAddressId(address.getId());
        restaurantService.updateById(restaurant);

        Address address1 = getById(address.getId());
        LatLng latLng = geoService.address2GeoCode(address1.fullAddress());
        address1.setLatitude(latLng.lat);
        address1.setLongitude(latLng.lng);
        updateById(address1);
        return R.ok().data("addressId", address.getId());
    }

    @Override
    public R getCustomerAddresses(Long customerId) {
        if (userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getId,customerId).eq(User::getRoleId, UserRole.ROLE_CUSTOMER)) == null) {
            throw new ServicesException(ResultCode.CUSTOMER_ID_ERROR);
        }
        List<Address> addresses = list(Wrappers.<Address>lambdaQuery().eq(Address::getCustomerId, customerId));
        return R.ok().data("addresses", addresses);
    }

    @Override
    public R addCustomerAddress(CustomerAddressDTO customerAddressDTO) {
        if (userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getId,customerAddressDTO.getCustomerId()).eq(User::getRoleId, UserRole.ROLE_CUSTOMER)) == null) {
            throw new ServicesException(ResultCode.CUSTOMER_ID_ERROR);
        }
        Address address = CustomerAddressDTO.toAddressDO(customerAddressDTO);
        LatLng latLng = geoService.address2GeoCode(address.fullAddress());
        address.setLatitude(latLng.lat);
        address.setLongitude(latLng.lng);
        save(address);
        return R.ok().data("addressId", address.getId());
    }

    @Override
    public R updateCustomerAddress(CustomerAddressDTO customerAddressDTO) {
        if (getById(customerAddressDTO.getId()) == null) {
            throw new ServicesException(ResultCode.ADDRESS_ID_ERROR);
        }
        Address address = CustomerAddressDTO.toAddressDO(customerAddressDTO);
        String fullAddress = getById(customerAddressDTO.getId()).fullAddress();
        LatLng latLng = geoService.address2GeoCode(fullAddress);
        address.setLatitude(latLng.lat);
        address.setLongitude(latLng.lng);
        updateById(address);
        return R.ok();
    }

    @Override
    public R removeCustomerAddress(Long addressId) {
        if (getById(addressId) == null) {
            throw new ServicesException(ResultCode.ADDRESS_ID_ERROR);
        }
        removeById(addressId);
        return R.ok();
    }
}
