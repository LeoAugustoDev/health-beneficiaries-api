package com.leo.health_beneficiaries_api.mapper;

import com.leo.health_beneficiaries_api.dto.BeneficiarioRequestDTO;
import com.leo.health_beneficiaries_api.dto.BeneficiarioResponseDTO;
import com.leo.health_beneficiaries_api.dto.DocumentoDTO;
import com.leo.health_beneficiaries_api.entity.Beneficiario;
import com.leo.health_beneficiaries_api.entity.Documento;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BeneficiarioMapper {

	public Beneficiario toEntity(BeneficiarioRequestDTO request) {
		Beneficiario beneficiario = Beneficiario.builder()
				.nome(request.getNome())
				.telefone(request.getTelefone())
				.dataNascimento(request.getDataNascimento())
				.build();

		beneficiario.substituirDocumentos(toDocumentos(request.getDocumentos()));

		return beneficiario;
	}

	public BeneficiarioResponseDTO toResponse(Beneficiario beneficiario) {
		return BeneficiarioResponseDTO.builder()
				.id(beneficiario.getId())
				.nome(beneficiario.getNome())
				.telefone(beneficiario.getTelefone())
				.dataNascimento(beneficiario.getDataNascimento())
				.dataInclusao(beneficiario.getDataInclusao())
				.dataAtualizacao(beneficiario.getDataAtualizacao())
				.documentos(toDocumentoDTOs(beneficiario.getDocumentos()))
				.build();
	}

	public List<BeneficiarioResponseDTO> toResponseList(List<Beneficiario> beneficiarios) {
		return beneficiarios.stream()
				.map(this::toResponse)
				.toList();
	}

	public List<DocumentoDTO> toDocumentoDTOs(List<Documento> documentos) {
		if (documentos == null) {
			return Collections.emptyList();
		}

		return documentos.stream()
				.map(this::toDocumentoDTO)
				.toList();
	}

	public List<Documento> toDocumentos(List<DocumentoDTO> documentos) {
		if (documentos == null) {
			return Collections.emptyList();
		}

		return documentos.stream()
				.map(this::toDocumento)
				.toList();
	}

	private DocumentoDTO toDocumentoDTO(Documento documento) {
		return DocumentoDTO.builder()
				.tipoDocumento(documento.getTipoDocumento())
				.descricao(documento.getDescricao())
				.dataInclusao(documento.getDataInclusao())
				.dataAtualizacao(documento.getDataAtualizacao())
				.build();
	}

	private Documento toDocumento(DocumentoDTO documento) {
		return Documento.builder()
				.tipoDocumento(documento.getTipoDocumento())
				.descricao(documento.getDescricao())
				.build();
	}
}
