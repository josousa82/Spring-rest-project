package com.training.rest.controllers;


import com.training.rest.springrestproject.SpringRestProjectApplication;
import com.training.rest.springrestproject.services.VendorService;
import com.training.rest.springrestproject.services.exceptions.ResourceNotFoundException;
import com.training.rest.springrestproject.v1.controllers.VendorController;
import com.training.rest.springrestproject.v1.model.VendorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.training.rest.controllers.AbstractRestController.asJsonString;
import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {VendorController.class})
@ContextConfiguration(classes = SpringRestProjectApplication.class)
public class VendorControllerTest  {

    public static final String VENDORS_URL_ID_1 = VENDORS_API_URL.concat("id/1");

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAllVendors() throws Exception {
        List<VendorDTO> vendorDTOList = Arrays.asList(VENDOR_DTO_1, VENDOR_DTO_2);
        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

        mockMvc.perform(get(VENDORS_API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }


    @Test
    public void testGetVendorById() throws Exception {
        String vendorUrl = VENDORS_API_URL.concat("id/1");
        when(vendorService.getVendorById(anyLong())).thenReturn(VENDOR_DTO_1);

        mockMvc.perform(get(vendorUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_DTO_1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorUrl)));;

    }

    @Test
    public void createNewVendor() throws Exception {

        String vendorUrl = VENDORS_API_URL.concat("id/1");

        VendorDTO requestDTO = VENDOR_DTO_1;

        VendorDTO returnedDTO = VENDOR_DTO_1;
        returnedDTO.setUrl(vendorUrl);

         when(vendorService.createNewVendor(requestDTO)).thenReturn(returnedDTO);

         mockMvc.perform(post(VENDORS_API_URL)
                         .content(asJsonString(requestDTO))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.name", equalTo(VENDOR_DTO_1.getName())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo(returnedDTO.getUrl())));;
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDTO requestDTO = VENDOR_DTO_1;
        requestDTO.setUrl("");

        // TODO fix test, json path not being picked up
        VendorDTO returnedDTO = VENDOR_DTO_1;
        returnedDTO.setUrl(CUSTOMERS_API_URL.concat("1"));

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDTO);

        mockMvc.perform(put(VENDORS_URL_ID_1)
                .content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_DTO_1.getName())));
    }

    @Test
    public void testPatchVendor() throws Exception {
        VendorDTO requestDTO = VENDOR_DTO_1;

        requestDTO.setUrl("");

        VendorDTO returnedDTO = VENDOR_DTO_1;
        returnedDTO.setUrl(VENDORS_URL_ID_1);
//        BDD style also for the controller
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(returnedDTO);

        mockMvc.perform(patch(VENDORS_URL_ID_1)
                .content(asJsonString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_DTO_1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo(returnedDTO.getUrl())));
    }

    @Test
    public void deleteVendorByIdTest() throws Exception {
        mockMvc.perform(delete(VENDORS_URL_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).deleteVendorById(anyLong());
        // same as verify although in BDD style
        then(vendorService).should().deleteVendorById(anyLong());
    }


    @Test
    public void testGetVendorByIdNotFoundThrowsException() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(VENDORS_URL_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVendorNotFoundThrowsException() throws Exception {
        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(put(VENDORS_URL_ID_1)
                .content(asJsonString(VENDOR_DTO_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPatchVendorNotFoundThrowsException() throws Exception {
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(patch(VENDORS_URL_ID_1)
                .content(asJsonString(VENDOR_DTO_1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}