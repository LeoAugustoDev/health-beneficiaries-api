package com.leo.health_beneficiaries_api.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BeneficiarioResponseDTO {

	Long id;
	String nome;
	String telefone;
	String email;
	LocalDate dataNascimento;
	List<DocumentoDTO> documentos;
}
