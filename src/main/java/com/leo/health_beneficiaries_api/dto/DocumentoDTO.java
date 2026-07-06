package com.leo.health_beneficiaries_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DocumentoDTO {

	@NotBlank(message = "Tipo do documento e obrigatorio")
	@Size(max = 50, message = "Tipo do documento deve ter no maximo 50 caracteres")
	String tipoDocumento;

	@NotBlank(message = "Descricao do documento e obrigatoria")
	@Size(max = 255, message = "Descricao do documento deve ter no maximo 255 caracteres")
	String descricao;

	LocalDateTime dataInclusao;
	LocalDateTime dataAtualizacao;
}
