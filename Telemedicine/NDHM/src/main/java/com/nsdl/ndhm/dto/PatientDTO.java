package com.nsdl.ndhm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDTO {

    private String id;

    private String name;

    private String gender;

    private String yearOfBirth;

    private AddressDTO address;
    
    public List<IdentifierDTO> identifiers;

}
