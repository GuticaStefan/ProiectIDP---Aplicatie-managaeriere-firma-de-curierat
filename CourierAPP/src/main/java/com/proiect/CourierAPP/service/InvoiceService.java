package com.proiect.CourierAPP.service;

import com.proiect.CourierAPP.dtos.*;
import com.proiect.CourierAPP.enums.OrderStatus;
import com.proiect.CourierAPP.exceptions.OrderNotFoundException;
import com.proiect.CourierAPP.exceptions.UnauthorizedUserException;
import com.proiect.CourierAPP.model.CourierEmployee;
import com.proiect.CourierAPP.model.Invoice;
import com.proiect.CourierAPP.model.Order;
import com.proiect.CourierAPP.repository.InvoiceRepository;
import com.proiect.CourierAPP.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final TokenService tokenService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InvoiceRepository invoiceRepository;
    public OrderInvoiceDto addInvoice(UUID orderId, InvoiceDto invoiceDto) {
        if (tokenService.isAdmin()) {
            var order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
            var invoice = modelMapper.map(invoiceDto, Invoice.class);

            invoice.setDate(LocalDateTime.now());
            invoice.setOrder(order);
            invoice.setValue(invoiceDto.getValue());

            invoiceRepository.save(invoice);
            order.setStatus(OrderStatus.CONFIRMED);

            return modelMapper.map(order, OrderInvoiceDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void deleteInvoice(UUID orderId) {
        if (tokenService.isAdmin()) {
            var order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
            order.setStatus(OrderStatus.CANCELLED);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public InvoiceResponseDto getInvoiceById(UUID orderId) {
        if (tokenService.isAdmin()) {
            var invoice = invoiceRepository.findInvoiceByOrderId(orderId).orElseThrow(OrderNotFoundException::new);
            return modelMapper.map(invoice, InvoiceResponseDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<InvoiceResponseDto> getAllInvoices() {
        if (tokenService.isAdmin()) {
            List<Invoice> invoices = invoiceRepository.findAll();
            return invoices.stream()
                    .map(i -> modelMapper.map(i, InvoiceResponseDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }
}
