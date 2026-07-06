package com.leo.health_beneficiaries_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.entity.Beneficiario;
import com.leo.health_beneficiaries_api.exception.BusinessException;
import com.leo.health_beneficiaries_api.exception.ResourceNotFoundException;
import com.leo.health_beneficiaries_api.helper.DataHelper;
import com.leo.health_beneficiaries_api.mapper.BeneficiarioMapper;
import com.leo.health_beneficiaries_api.repository.BeneficiarioRepository;
import com.leo.health_beneficiaries_api.repository.DocumentoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeneficiarioApplicationServiceTest {

	@InjectMocks
	private BeneficiarioApplicationService beneficiarioApplicationService;

	@Mock
	private BeneficiarioRepository beneficiarioRepository;

	@Mock
	private DocumentoRepository documentoRepository;

	@Spy
	private BeneficiarioMapper beneficiarioMapper = new BeneficiarioMapper();

	@Test
	void deveCriarBeneficiario() {
		BeneficiarioRequestDTO request = DataHelper.criarBeneficiarioRequest();
		Beneficiario beneficiarioSalvo = DataHelper.criarBeneficiario();

		when(beneficiarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		when(beneficiarioRepository.save(any(Beneficiario.class))).thenReturn(beneficiarioSalvo);

		BeneficiarioResponseDTO response = beneficiarioApplicationService.criar(request);

		assertNotNull(response);
		assertEquals(beneficiarioSalvo.getId(), response.getId());
		assertEquals(request.getNome(), response.getNome());
		assertEquals(request.getEmail(), response.getEmail());
		assertEquals(1, response.getDocumentos().size());

		verify(beneficiarioRepository).findByEmail(request.getEmail());
		verify(beneficiarioRepository).save(any(Beneficiario.class));
	}

	@Test
	void deveLancarBusinessExceptionAoCriarBeneficiarioComEmailJaCadastrado() {
		BeneficiarioRequestDTO request = DataHelper.criarBeneficiarioRequest();
		Beneficiario beneficiarioExistente = DataHelper.criarBeneficiario();

		when(beneficiarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(beneficiarioExistente));

		BusinessException exception = assertThrows(
				BusinessException.class,
				() -> beneficiarioApplicationService.criar(request)
		);

		assertEquals("Email ja cadastrado", exception.getMessage());
		verify(beneficiarioRepository).findByEmail(request.getEmail());
		verify(beneficiarioRepository, never()).save(any(Beneficiario.class));
	}

	@Test
	void deveLancarResourceNotFoundExceptionAoAtualizarBeneficiarioInexistente() {
		Long id = 1L;
		BeneficiarioRequestDTO request = DataHelper.criarBeneficiarioRequest();

		when(beneficiarioRepository.findById(id)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
				() -> beneficiarioApplicationService.atualizar(id, request)
		);

		assertEquals("Beneficiario nao encontrado", exception.getMessage());
		verify(beneficiarioRepository).findById(id);
		verify(beneficiarioRepository, never()).save(any(Beneficiario.class));
	}

	@Test
	void deveDeletarBeneficiario() {
		Long id = 1L;
		Beneficiario beneficiario = DataHelper.criarBeneficiario();

		when(beneficiarioRepository.findById(id)).thenReturn(Optional.of(beneficiario));

		beneficiarioApplicationService.deletar(id);

		verify(beneficiarioRepository).findById(id);
		verify(beneficiarioRepository).delete(beneficiario);
	}

	@Test
	void deveLancarResourceNotFoundExceptionAoDeletarBeneficiarioInexistente() {
		Long id = 1L;

		when(beneficiarioRepository.findById(id)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
				() -> beneficiarioApplicationService.deletar(id)
		);

		assertEquals("Beneficiario nao encontrado", exception.getMessage());
		verify(beneficiarioRepository).findById(id);
		verify(beneficiarioRepository, never()).delete(any(Beneficiario.class));
	}
}
