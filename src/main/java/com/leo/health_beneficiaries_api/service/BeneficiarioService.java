package com.leo.health_beneficiaries_api.service;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import java.util.List;

public interface BeneficiarioService {

	BeneficiarioResponseDTO criar(BeneficiarioRequestDTO request);

	List<BeneficiarioResponseDTO> listarTodos();

	BeneficiarioResponseDTO atualizar(Long id, BeneficiarioRequestDTO request);

	void deletar(Long id);

	List<DocumentoDTO> listarDocumentos(Long beneficiarioId);
}
