package com.hashedin.entity;

import com.hashedin.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordersinfo")
public class OrdersInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String orderIdString;
    private String transactionIdString;
    private int productId;
    private Long userId;
    private int planDuration;
    private LocalDate createdAt;
}
