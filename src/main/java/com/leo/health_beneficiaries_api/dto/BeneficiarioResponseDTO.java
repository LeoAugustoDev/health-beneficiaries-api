package com.leo.health_beneficiaries_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BeneficiarioResponseDTO {

	Long id;
	String nome;
	String telefone;
	LocalDate dataNascimento;
	LocalDateTime dataInclusao;
	LocalDateTime dataAtualizacao;
	List<DocumentoDTO> documentos;
}
