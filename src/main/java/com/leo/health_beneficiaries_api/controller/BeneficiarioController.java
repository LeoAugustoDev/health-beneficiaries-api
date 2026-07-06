package com.leo.health_beneficiaries_api.controller;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import com.leo.health_beneficiaries_api.service.BeneficiarioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class BeneficiarioController implements BeneficiarioAPI {

	private static final String CLASS_NAME = "BeneficiarioController";

	private final BeneficiarioService beneficiarioService;

	@Override
	public BeneficiarioResponseDTO criar(BeneficiarioRequestDTO request) {
		log.info("[inicia] {} - criar", CLASS_NAME);
		BeneficiarioResponseDTO response = beneficiarioService.criar(request);
		log.info("[finaliza] {} - criar", CLASS_NAME);
		return response;
	}

	@Override
	public List<BeneficiarioResponseDTO> listarTodos() {
		log.info("[inicia] {} - listarTodos", CLASS_NAME);
		List<BeneficiarioResponseDTO> response = beneficiarioService.listarTodos();
		log.info("[finaliza] {} - listarTodos", CLASS_NAME);
		return response;
	}

	@Override
	public BeneficiarioResponseDTO atualizar(Long id, BeneficiarioRequestDTO request) {
		log.info("[inicia] {} - atualizar", CLASS_NAME);
		log.info("[idBeneficiario] {}", id);
		BeneficiarioResponseDTO response = beneficiarioService.atualizar(id, request);
		log.info("[finaliza] {} - atualizar", CLASS_NAME);
		return response;
	}

	@Override
	public void deletar(Long id) {
		log.info("[inicia] {} - deletar", CLASS_NAME);
		log.info("[idBeneficiario] {}", id);
		beneficiarioService.deletar(id);
		log.info("[finaliza] {} - deletar", CLASS_NAME);
	}

	@Override
	public List<DocumentoDTO> listarDocumentos(Long id) {
		log.info("[inicia] {} - listarDocumentos", CLASS_NAME);
		log.info("[idBeneficiario] {}", id);
		List<DocumentoDTO> response = beneficiarioService.listarDocumentos(id);
		log.info("[finaliza] {} - listarDocumentos", CLASS_NAME);
		return response;
	}
}
