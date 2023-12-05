package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.mbs.enums.PaymentMode;
import com.xyz.mbs.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("payment")
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_gateway")
    private PaymentMode paymentMode;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "payment_ref_id")
    private String paymentRefId = null;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @JsonManagedReference
    private Booking booking;

    @Column(name = "payment_type", insertable=false, updatable=false)
    private String paymentType;
}
