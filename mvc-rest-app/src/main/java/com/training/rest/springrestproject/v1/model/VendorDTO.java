package com.training.rest.springrestproject.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by jt on 9/24/17.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendorDTO {

     @ApiModelProperty(value = "Vendor id", hidden = true)
     private Long id;

     @ApiModelProperty(value = "Vendor Name", required = true)
     private String name;

     @ApiModelProperty(value = "Vendor url", hidden = true)
     @JsonProperty("vendor_url")
     private String url;
}
