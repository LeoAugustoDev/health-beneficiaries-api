package com.leo.health_beneficiaries_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DocumentoDTO {

	@NotBlank(message = "Tipo do documento e obrigatorio")
	@Size(max = 30, message = "Tipo do documento deve ter no maximo 30 caracteres")
	String tipo;

	@NotBlank(message = "Numero do documento e obrigatorio")
	@Size(max = 50, message = "Numero do documento deve ter no maximo 50 caracteres")
	String numero;
}
