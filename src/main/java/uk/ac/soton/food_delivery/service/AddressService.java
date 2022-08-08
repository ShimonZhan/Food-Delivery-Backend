package uk.ac.soton.food_delivery.service;

import uk.ac.soton.food_delivery.entity.DO.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.entity.DTO.CustomerAddressDTO;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface AddressService extends IService<Address> {

    R updateRestaurantAddress(Address address);

    R getCustomerAddresses(Long customerId);

    R addCustomerAddress(CustomerAddressDTO customerAddressDTO);

    R updateCustomerAddress(CustomerAddressDTO customerAddressDTO);

    R removeCustomerAddress(Long addressId);


}
