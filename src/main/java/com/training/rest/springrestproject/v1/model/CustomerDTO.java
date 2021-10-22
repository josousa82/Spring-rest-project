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
public class CustomerDTO {

    @ApiModelProperty(value = "This is id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "This is the first name", required = true, position = 1)
    private String firstName;

    @ApiModelProperty(value = "This is the last name", required = true, position = 2)
    private String lastName;

    @ApiModelProperty(value = "value should note be included", position = 3, hidden = true)
    @JsonProperty("customer_url")
    private String url;
}
