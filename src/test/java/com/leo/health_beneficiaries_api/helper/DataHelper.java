package com.leo.health_beneficiaries_api.helper;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import com.leo.health_beneficiaries_api.entity.Beneficiario;
import com.leo.health_beneficiaries_api.entity.Documento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class DataHelper {

	private DataHelper() {
	}

	public static BeneficiarioRequestDTO criarBeneficiarioRequest() {
		return BeneficiarioRequestDTO.builder()
				.nome("Maria Silva")
				.telefone("11999999999")
				.dataNascimento(LocalDate.of(1990, 5, 20))
				.documentos(List.of(criarDocumentoDTO()))
				.build();
	}

	public static BeneficiarioRequestDTO criarBeneficiarioAtualizacaoRequest() {
		return BeneficiarioRequestDTO.builder()
				.nome("Maria Silva Atualizada")
				.telefone("11888888888")
				.dataNascimento(LocalDate.of(1990, 5, 20))
				.documentos(List.of(criarDocumentoAtualizacaoDTO()))
				.build();
	}

	public static Beneficiario criarBeneficiario() {
		LocalDateTime dataCadastro = LocalDateTime.of(2026, 7, 6, 10, 0);

		Beneficiario beneficiario = Beneficiario.builder()
				.id(1L)
				.nome("Maria Silva")
				.telefone("11999999999")
				.dataNascimento(LocalDate.of(1990, 5, 20))
				.dataInclusao(dataCadastro)
				.dataAtualizacao(dataCadastro)
				.build();

		beneficiario.adicionarDocumento(criarDocumento());

		return beneficiario;
	}

	public static DocumentoDTO criarDocumentoDTO() {
		return DocumentoDTO.builder()
				.tipoDocumento("CPF")
				.descricao("Cadastro de Pessoa Fisica")
				.build();
	}

	public static DocumentoDTO criarDocumentoAtualizacaoDTO() {
		return DocumentoDTO.builder()
				.tipoDocumento("RG")
				.descricao("Registro Geral")
				.build();
	}

	public static Documento criarDocumento() {
		LocalDateTime dataCadastro = LocalDateTime.of(2026, 7, 6, 10, 0);

		return Documento.builder()
				.id(1L)
				.tipoDocumento("CPF")
				.descricao("Cadastro de Pessoa Fisica")
				.dataInclusao(dataCadastro)
				.dataAtualizacao(dataCadastro)
				.build();
	}
}
