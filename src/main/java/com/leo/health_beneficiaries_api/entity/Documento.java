package com.leo.health_beneficiaries_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "documentos",
		indexes = {
				@Index(name = "idx_documentos_beneficiario_id", columnList = "beneficiario_id"),
				@Index(name = "idx_documentos_tipo_documento", columnList = "tipo_documento")
		}
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tipo_documento", nullable = false, length = 50)
	private String tipoDocumento;

	@Column(nullable = false, length = 255)
	private String descricao;

	@Column(name = "data_inclusao", nullable = false, updatable = false)
	private LocalDateTime dataInclusao;

	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "beneficiario_id", nullable = false)
	private Beneficiario beneficiario;

	void vincularBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	void desvincularBeneficiario() {
		this.beneficiario = null;
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
