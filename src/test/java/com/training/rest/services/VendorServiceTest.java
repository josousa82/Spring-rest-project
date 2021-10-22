package com.training.rest.services;


import com.training.rest.springrestproject.domain.Vendor;
import com.training.rest.springrestproject.repositories.VendorRepository;
import com.training.rest.springrestproject.services.VendorService;
import com.training.rest.springrestproject.services.VendorServiceImpl;
import com.training.rest.springrestproject.v1.mapper.VendorMapper;
import com.training.rest.springrestproject.v1.model.VendorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }


    @Test
    public void getAllVendorsTest() throws Exception {
        CUSTOMER_1.setId(1L);
        CUSTOMER_2.setId(2L);
        CUSTOMER_3.setId(3L);

        when(vendorRepository.findAll()).thenReturn(Arrays.asList(VENDOR_1, VENDOR_2, VENDOR_3));

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        assertEquals(3, vendorDTOS.size());
    }


    @Test
    public void getVendorByIdTest() {
        // BDD style
        // given
        given(vendorRepository.findById(any(Long.class))).willReturn(Optional.of(VENDOR_1));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        // then
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertEquals(VENDOR_1.getId(), vendorDTO.getId());
        assertEquals(VENDOR_1.getName(), vendorDTO.getName());
    }
    
    @Test
    public void createNewVendorTest() {
        when(vendorRepository.save(any(Vendor.class))).thenReturn(VENDOR_1);

        VendorDTO vendorDTO = vendorService.createNewVendor(VENDOR_DTO_1);

        assertEquals(VENDOR_1.getName(), vendorDTO.getName());
        assertEquals(VENDOR_1.getId(), vendorDTO.getId());
        assertEquals(VENDORS_API_URL.concat(VENDOR_DTO_1.getId().toString()) ,vendorDTO.getUrl());
    }


    @Test
    public void saveVendorByDTOTest() {
        VENDOR_1.setName("under test");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(VENDOR_1));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(VENDOR_1);

        VendorDTO savedDto = vendorService.saveVendorByDTO(1L, VENDOR_DTO_1);

        assertEquals(VENDOR_1.getName(), savedDto.getName());
        assertEquals(VENDOR_1.getId(), savedDto.getId());
        assertEquals(VENDORS_API_URL.concat(VENDOR_DTO_1.getId().toString()) ,savedDto.getUrl());
    }


    @Test
    public void patchVendorTest() {
        CUSTOMER_1.setFirstName("under test");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(VENDOR_1));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(VENDOR_1);

        VendorDTO savedDto = vendorService.patchVendor(1L, VENDOR_DTO_1);

        assertEquals(VENDOR_1.getName(), savedDto.getName());
        assertEquals(VENDOR_1.getId(), savedDto.getId());
        assertEquals(VENDORS_API_URL.concat(VENDOR_DTO_1.getId().toString()) ,savedDto.getUrl());
    }

    @Test
    public void deleteVendorByIdTest() {
        vendorRepository.deleteById(1L);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void getVendorByIdNotFoundTest() {
//        // BDD style
//        // given
//        given(vendorRepository.findById(any(Long.class))).willReturn(Optional.empty());
//
//        //then
//        thrown.expect(ResourceNotFoundException.class);
//        thrown.expectMessage("Vendor with id  1 not found.");
//
//        //when
//        vendorService.getVendorById(1L);
//    }
}