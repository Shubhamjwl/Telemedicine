package com.nsdl.ndhm.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class BinaryDTO {
    private String id;
    private String addProfile;
    private String contentType;
    private String dataElement;
}
