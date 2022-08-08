package uk.ac.soton.food_delivery.entity.DTO;

import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.enums.AddressType;

/**
 * @author ShimonZhan
 * @since 2022-04-26 18:32:51
 */
@Data
public class CustomerAddressDTO {
    private Long id;
    private Long customerId;
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String country;
    private String postcode;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String firstName;
    private String lastName;

    public static Address toAddressDO(CustomerAddressDTO customerAddressDTO) {
        if (customerAddressDTO == null) {
            return null;
        }
        Address address = new Address();
        address.setId(customerAddressDTO.getId());
        address.setCustomerId(customerAddressDTO.getCustomerId());
        address.setPhone(customerAddressDTO.getPhone());
        address.setFirstAddress(customerAddressDTO.getFirstAddress());
        address.setSecondAddress(customerAddressDTO.getSecondAddress());
        address.setCity(customerAddressDTO.getCity());
        address.setCountry(customerAddressDTO.getCountry());
        address.setPostcode(customerAddressDTO.getPostcode());
        address.setLatitude(customerAddressDTO.getLatitude());
        address.setLongitude(customerAddressDTO.getLongitude());
        address.setFirstName(customerAddressDTO.getFirstName());
        address.setLastName(customerAddressDTO.getLastName());
        address.setType(AddressType.CUSTOMER);
        return address;
    }
}
