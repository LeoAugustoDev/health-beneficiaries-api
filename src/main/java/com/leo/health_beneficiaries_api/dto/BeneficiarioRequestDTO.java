package com.leo.health_beneficiaries_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BeneficiarioRequestDTO {

	@NotBlank(message = "Nome e obrigatorio")
	@Size(max = 150, message = "Nome deve ter no maximo 150 caracteres")
	String nome;

	@NotBlank(message = "Telefone e obrigatorio")
	@Size(max = 20, message = "Telefone deve ter no maximo 20 caracteres")
	String telefone;

	@NotBlank(message = "Email e obrigatorio")
	@Email(message = "Email deve ser valido")
	@Size(max = 150, message = "Email deve ter no maximo 150 caracteres")
	String email;

	@NotNull(message = "Data de nascimento e obrigatoria")
	@Past(message = "Data de nascimento deve estar no passado")
	LocalDate dataNascimento;

	@Valid
	@NotNull(message = "Documentos sao obrigatorios")
	@Size(min = 1, message = "Informe ao menos um documento")
	List<DocumentoDTO> documentos;
}
