package uk.ac.soton.food_delivery.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.soton.food_delivery.entity.DTO.CustomerAddressDTO;
import uk.ac.soton.food_delivery.service.AddressService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-04-26 18:10:30
 */
@RequestMapping("/address")
@RestController
public class AddressController {

    @Resource
    private AddressService addressService;

    @PreAuthorize("@ex.hasAuthority('system:address:customerGet')")
    @GetMapping("/getCustomerAddresses")
    public R getCustomerAddresses(Long customerId) {
        return addressService.getCustomerAddresses(customerId);
    }

    @PreAuthorize("@ex.hasAuthority('system:address:customerAdd')")
    @PostMapping("/addCustomerAddress")
    public R addCustomerAddress(@RequestBody CustomerAddressDTO customerAddressDTO) {
        return addressService.addCustomerAddress(customerAddressDTO);
    }

    @PutMapping("/updateCustomerAddress")
    @PreAuthorize("@ex.hasAuthority('system:address:customerUpdate')")
    public R updateCustomerAddress(@RequestBody CustomerAddressDTO customerAddressDTO) {
        return addressService.updateCustomerAddress(customerAddressDTO);
    }

    @DeleteMapping("/removeCustomerAddress")
    @PreAuthorize("@ex.hasAuthority('system:address:customerRemove')")
    public R removeCustomerAddress(Long addressId) {
        return addressService.removeCustomerAddress(addressId);
    }
}
