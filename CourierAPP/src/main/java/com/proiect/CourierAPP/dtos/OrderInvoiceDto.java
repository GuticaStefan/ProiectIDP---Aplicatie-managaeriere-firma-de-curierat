package com.proiect.CourierAPP.dtos;

import com.proiect.CourierAPP.enums.OrderStatus;
import com.proiect.CourierAPP.model.Invoice;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInvoiceDto {
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private OrderStatus status;
    @NotBlank
    private String description;
}
