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
import jakarta.persistence.Table;
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
				@Index(name = "idx_documentos_numero", columnList = "numero")
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

	@Column(nullable = false, length = 30)
	private String tipo;

	@Column(nullable = false, length = 50)
	private String numero;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "beneficiario_id", nullable = false)
	private Beneficiario beneficiario;

	void vincularBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	void desvincularBeneficiario() {
		this.beneficiario = null;
	}
}
