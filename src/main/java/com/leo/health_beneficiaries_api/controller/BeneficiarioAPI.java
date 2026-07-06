package com.leo.health_beneficiaries_api.controller;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beneficiarios")
@Tag(name = "Beneficiarios", description = "Gerenciamento de beneficiarios e documentos")
public interface BeneficiarioAPI {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cria um beneficiario com documentos")
	BeneficiarioResponseDTO criar(@RequestBody @Valid BeneficiarioRequestDTO request);

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Lista todos os beneficiarios")
	List<BeneficiarioResponseDTO> listarTodos();

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Atualiza os dados de um beneficiario")
	BeneficiarioResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid BeneficiarioRequestDTO request);

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove um beneficiario")
	void deletar(@PathVariable Long id);

	@GetMapping("/{id}/documentos")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Lista os documentos de um beneficiario")
	List<DocumentoDTO> listarDocumentos(@PathVariable Long id);
}
