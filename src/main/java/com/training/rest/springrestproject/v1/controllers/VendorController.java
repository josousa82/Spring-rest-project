package com.training.rest.springrestproject.v1.controllers;


import com.training.rest.springrestproject.services.VendorService;
import com.training.rest.springrestproject.v1.model.VendorDTO;
import com.training.rest.springrestproject.v1.model.VendorListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"description", "This is my vendor Api"})
@Slf4j
@Controller
@RequestMapping("/api/v1/vendors/")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "View list of vendors", notes = "The are notes")
    @GetMapping
    public ResponseEntity<VendorListDTO> getAllVendors(){
        return new ResponseEntity<>(
                new VendorListDTO(vendorService.getAllVendors()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get vendor by id.", notes = "The are notes")
    @GetMapping( {"/id/{id}"})
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long id){
        return new ResponseEntity<>(vendorService.getVendorById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new vendor", notes = "The are notes")
    @PostMapping
    public ResponseEntity<VendorDTO> createNewVendor(@RequestBody VendorDTO vendorDto){
        return new ResponseEntity<>(vendorService.createNewVendor(vendorDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update existent vendor", notes = "The are notes")
    @PutMapping({"/id/{id}"})
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDto){
        return new ResponseEntity<>(vendorService.saveVendorByDTO(id, vendorDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update vendor property", notes = "The are notes")
    @PatchMapping ({"/id/{id}"})
    public ResponseEntity<VendorDTO> patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDto){
        return new ResponseEntity<>(vendorService.patchVendor(id, vendorDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete existent vendor", notes = "The are notes")
    @DeleteMapping ({"/id/{id}"})
    public ResponseEntity<Void> deleteVendorById(@PathVariable Long id) {
            vendorService.deleteVendorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
