package com.leo.health_beneficiaries_api.repository;

import com.leo.health_beneficiaries_api.entity.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
}
