package com.leo.health_beneficiaries_api.service;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import com.leo.health_beneficiaries_api.entity.Beneficiario;
import com.leo.health_beneficiaries_api.exception.BusinessException;
import com.leo.health_beneficiaries_api.exception.ResourceNotFoundException;
import com.leo.health_beneficiaries_api.mapper.BeneficiarioMapper;
import com.leo.health_beneficiaries_api.repository.BeneficiarioRepository;
import com.leo.health_beneficiaries_api.repository.DocumentoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BeneficiarioApplicationService implements BeneficiarioService {

	private static final String CLASS_NAME = "BeneficiarioApplicationService";

	private final BeneficiarioRepository beneficiarioRepository;
	private final DocumentoRepository documentoRepository;
	private final BeneficiarioMapper beneficiarioMapper;

	@Override
	@Transactional
	public BeneficiarioResponseDTO criar(BeneficiarioRequestDTO request) {
		log.info("[inicia] {} - criar", CLASS_NAME);
		validarEmailDisponivel(request.getEmail(), null);

		Beneficiario beneficiario = beneficiarioMapper.toEntity(request);
		Beneficiario beneficiarioSalvo = beneficiarioRepository.save(beneficiario);

		log.info("[finaliza] {} - criar", CLASS_NAME);
		return beneficiarioMapper.toResponse(beneficiarioSalvo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeneficiarioResponseDTO> listarTodos() {
		log.info("[inicia] {} - listarTodos", CLASS_NAME);
		List<BeneficiarioResponseDTO> response = beneficiarioMapper.toResponseList(beneficiarioRepository.findAll());
		log.info("[finaliza] {} - listarTodos", CLASS_NAME);
		return response;
	}

	@Override
	@Transactional
	public BeneficiarioResponseDTO atualizar(Long id, BeneficiarioRequestDTO request) {
		log.info("[inicia] {} - atualizar", CLASS_NAME);
		log.info("[idBeneficiario] {}", id);

		Beneficiario beneficiario = buscarBeneficiarioPorId(id);
		validarEmailDisponivel(request.getEmail(), id);

		beneficiario.atualizarDados(
				request.getNome(),
				request.getTelefone(),
				request.getEmail(),
				request.getDataNascimento(),
				beneficiarioMapper.toDocumentos(request.getDocumentos())
		);

		Beneficiario beneficiarioAtualizado = beneficiarioRepository.save(beneficiario);

		log.info("[finaliza] {} - atualizar", CLASS_NAME);
		return beneficiarioMapper.toResponse(beneficiarioAtualizado);
	}

	@Override
	@Transactional
	public void deletar(Long id) {
		log.info("[inicia] {} - deletar", CLASS_NAME);
		log.info("[idBeneficiario] {}", id);

		Beneficiario beneficiario = buscarBeneficiarioPorId(id);
		beneficiarioRepository.delete(beneficiario);

		log.info("[finaliza] {} - deletar", CLASS_NAME);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentoDTO> listarDocumentos(Long beneficiarioId) {
		log.info("[inicia] {} - listarDocumentos", CLASS_NAME);
		log.info("[idBeneficiario] {}", beneficiarioId);

		buscarBeneficiarioPorId(beneficiarioId);
		List<DocumentoDTO> response = beneficiarioMapper.toDocumentoDTOs(
				documentoRepository.findAllByBeneficiarioId(beneficiarioId)
		);

		log.info("[finaliza] {} - listarDocumentos", CLASS_NAME);
		return response;
	}

	private Beneficiario buscarBeneficiarioPorId(Long id) {
		return beneficiarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Beneficiario nao encontrado"));
	}

	private void validarEmailDisponivel(String email, Long idAtual) {
		beneficiarioRepository.findByEmail(email)
				.filter(beneficiario -> idAtual == null || !beneficiario.getId().equals(idAtual))
				.ifPresent(beneficiario -> {
					throw new BusinessException("Email ja cadastrado");
				});
	}
}
