package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Pet;
import java.util.List;
import lombok.Data;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;



}
