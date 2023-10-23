package com.CrimsonBackendDatabase.crimsondb.Payments;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    @Query("SELECT s FROM Payments s WHERE s.payerId = ?1 AND s.payerType = ?2 AND s.random = ?3")
    Optional<Payments> findPaymentsByPayerIdPayerTypeAndRandom(Long payerId, String payerType, String random);

    @Query("SELECT s FROM Payments s WHERE s.payerId = ?1 AND s.payerType = ?2")
    Optional<List<Payments>> findPaymentsByPayerId(Long payerId, String payerType);

}
