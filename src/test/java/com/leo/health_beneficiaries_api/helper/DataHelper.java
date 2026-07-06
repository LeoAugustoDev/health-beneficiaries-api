package com.leo.health_beneficiaries_api.helper;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import com.leo.health_beneficiaries_api.entity.Beneficiario;
import com.leo.health_beneficiaries_api.entity.Documento;
import java.time.LocalDate;
import java.util.List;

public final class DataHelper {

	private DataHelper() {
	}

	public static BeneficiarioRequestDTO criarBeneficiarioRequest() {
		return BeneficiarioRequestDTO.builder()
				.nome("Maria Silva")
				.telefone("11999999999")
				.email("maria.silva@email.com")
				.dataNascimento(LocalDate.of(1990, 5, 20))
				.documentos(List.of(criarDocumentoDTO()))
				.build();
	}

	public static Beneficiario criarBeneficiario() {
		Beneficiario beneficiario = Beneficiario.builder()
				.id(1L)
				.nome("Maria Silva")
				.telefone("11999999999")
				.email("maria.silva@email.com")
				.dataNascimento(LocalDate.of(1990, 5, 20))
				.build();

		beneficiario.adicionarDocumento(criarDocumento());

		return beneficiario;
	}

	public static DocumentoDTO criarDocumentoDTO() {
		return DocumentoDTO.builder()
				.tipo("CPF")
				.numero("12345678900")
				.build();
	}

	public static Documento criarDocumento() {
		return Documento.builder()
				.id(1L)
				.tipo("CPF")
				.numero("12345678900")
				.build();
	}
}
