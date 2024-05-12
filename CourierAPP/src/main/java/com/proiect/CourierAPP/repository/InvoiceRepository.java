package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    @Transactional
    Optional<Invoice> findInvoiceByOrderId(UUID id);
}
