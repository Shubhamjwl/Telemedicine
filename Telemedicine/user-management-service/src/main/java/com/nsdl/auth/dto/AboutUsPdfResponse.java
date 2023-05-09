package com.nsdl.auth.dto;

import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

public class AboutUsPdfResponse {

	public String encodedPdfData;

}
