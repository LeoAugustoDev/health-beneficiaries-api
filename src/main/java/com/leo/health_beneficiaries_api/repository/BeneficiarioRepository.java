package com.leo.health_beneficiaries_api.repository;

import com.leo.health_beneficiaries_api.entity.Beneficiario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

	Optional<Beneficiario> findByEmail(String email);

	boolean existsByEmail(String email);
}
