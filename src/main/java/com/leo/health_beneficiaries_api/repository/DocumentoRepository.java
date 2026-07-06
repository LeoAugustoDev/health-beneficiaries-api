package com.leo.health_beneficiaries_api.repository;

import com.leo.health_beneficiaries_api.entity.Documento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	List<Documento> findAllByBeneficiarioId(Long beneficiarioId);
}
