package com.training.rest.springrestproject.v1.mapper;


import com.training.rest.springrestproject.domain.Vendor;
import com.training.rest.springrestproject.v1.model.VendorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
