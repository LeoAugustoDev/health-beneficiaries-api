package com.leo.health_beneficiaries_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "beneficiarios",
		indexes = @Index(name = "idx_beneficiarios_email", columnList = "email")
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Beneficiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, length = 20)
	private String telefone;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Builder.Default
	@OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Documento> documentos = new ArrayList<>();

	public void adicionarDocumento(Documento documento) {
		documentos.add(documento);
		documento.vincularBeneficiario(this);
	}

	public void substituirDocumentos(List<Documento> novosDocumentos) {
		documentos.forEach(Documento::desvincularBeneficiario);
		documentos.clear();

		if (novosDocumentos != null) {
			novosDocumentos.forEach(this::adicionarDocumento);
		}
	}

	public void atualizarDados(String nome, String telefone, String email, LocalDate dataNascimento, List<Documento> documentos) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		substituirDocumentos(documentos);
	}
}
