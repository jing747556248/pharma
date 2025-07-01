package com.sanofi.pharma.core.repository;

import com.sanofi.pharma.core.entity.Prescription;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Pessimistic lock, avoid dirty reading
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Prescription p WHERE p.id = :id AND p.isDeleted = false ")
    Optional<Prescription> findWithLockById(@Param("id") Long id);
}
