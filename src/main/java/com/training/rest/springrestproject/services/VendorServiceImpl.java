package com.training.rest.springrestproject.services;


import com.training.rest.springrestproject.domain.Vendor;
import com.training.rest.springrestproject.repositories.VendorRepository;
import com.training.rest.springrestproject.services.exceptions.ResourceNotFoundException;
import com.training.rest.springrestproject.v1.mapper.VendorMapper;
import com.training.rest.springrestproject.v1.model.VendorDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.training.rest.springrestproject.services.SerializingDeserializingAbstractHelper.asJsonString;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService{

    public static final String VENDORS_URL = "/api/v1/vendors/";
    public static final String NOT_FOUND = "Vendor with id  %d not found. ";
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> getVendorDto(vendor, "id/".concat(vendor.getId().toString())) )
                .collect(Collectors.toList());
    }


    @Override
    public VendorDTO getVendorById(Long id){
        return vendorRepository.findById(id)
                .map(vendor -> getVendorDto(vendor, "id/".concat(vendor.getId().toString())) )
                .orElseThrow( () -> new ResourceNotFoundException(String.format(NOT_FOUND, id ) ));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        VendorDTO returnDTO = saveAndReturnDTO(vendor);
        log.info("Created vendor {}", asJsonString(returnDTO));
        return returnDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        Vendor vendor1 =  vendorRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException(String.format(NOT_FOUND, id )));
        vendor.setId(vendor1.getId());
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        // TODO refactor coed.. don't like it.. smellllll
        return vendorRepository.findById(id).map(vendor -> {
            if(Strings.isNotBlank(vendorDTO.getName())){
                vendor.setName(vendorDTO.getName());
            }
           return saveAndReturnDTO(vendorRepository.save(vendor));
        }).orElseThrow(ResourceNotFoundException::new);
    }

//    TODO test exception
    @Override
    public void deleteVendorById(Long id) {
        try {
            vendorRepository.deleteById(id);
            log.info("Deleted vendor with id {}.", id);
        }catch (Exception e){
            log.error("Vendor with id {} not found. ", id);
            throw new ResourceNotFoundException(String.format(NOT_FOUND, id));
        }
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnDTO.setUrl(VENDORS_URL.concat(savedVendor.getId().toString()));
        return returnDTO;
    }

    private VendorDTO getVendorDto(Vendor vendor, String path) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO.setUrl(VENDORS_URL.concat(path));
        return vendorDTO;
    }
}
