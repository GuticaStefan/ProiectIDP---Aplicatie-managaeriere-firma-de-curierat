package com.proiect.CourierAPP.resource;

import com.proiect.CourierAPP.dtos.*;
import com.proiect.CourierAPP.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderInvoiceDto acceptOrder(@PathVariable UUID orderId, @RequestBody InvoiceDto invoiceDto) {
        return invoiceService.addInvoice(orderId, invoiceDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> denyOrder(@PathVariable UUID orderId) {
        invoiceService.deleteInvoice(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponseDto getInvoiceById(@PathVariable UUID orderId) {
        return invoiceService.getInvoiceById(orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

}
