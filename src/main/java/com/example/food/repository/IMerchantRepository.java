package com.example.food.repository;

import com.example.food.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
    Boolean existsByUsername(String username);

    Boolean existsByTaxIdentificationNumber(String taxCode);

    Boolean existsByEmail(String email);

    Optional<Merchant> findFirstByUsername(String username);

    Optional<Merchant> findMerchantByEmail(String email);

    Optional<Merchant> findMerchantByTaxIdentificationNumber(String taxCode);

    Iterable<Merchant> findAllByNameContaining(String name);
}
