package com.proiect.CourierAPP.dtos;

import com.proiect.CourierAPP.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDto {
    private LocalDateTime date;
    private Long value;
}
