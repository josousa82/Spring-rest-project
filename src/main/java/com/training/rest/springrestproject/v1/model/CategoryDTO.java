package com.training.rest.springrestproject.v1.model;

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
public class CategoryDTO {

     private Long id;
     private String name;
}
