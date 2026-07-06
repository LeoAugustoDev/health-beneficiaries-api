package com.leo.health_beneficiaries_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "beneficiarios")
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

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "data_inclusao", nullable = false, updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

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

	public void atualizarDados(String nome, String telefone, LocalDate dataNascimento, List<Documento> documentos) {
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.dataAtualizacao = LocalDateTime.now();
		substituirDocumentos(documentos);
	}

	@PrePersist
	private void prePersist() {
		LocalDateTime agora = LocalDateTime.now();
		this.dataInclusao = agora;
		this.dataAtualizacao = agora;
	}

	@PreUpdate
	private void preUpdate() {
		this.dataAtualizacao = LocalDateTime.now();
	}
}
