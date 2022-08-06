package com.hashedin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buyerverification")
public class BuyerVerification {

    @Id
    @Size(max=10)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long buyerVerificationId;


    @NotNull(message = "Serial No. is mandatory")
    @Column(name="serialNo" , unique = true)
    private String  serialNo;

    @NotNull(message = "Invoice image  url is mandatory")
    @Column(name = "invoiceImage")
    private String invoiceImage;


    @NotNull(message = "hardware image is mandatory")
    @Column(name="hardwareImage")
    private String hardwareImage;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @ApiModelProperty(notes="Foreign Key from userInfo")
    private UserInfo userInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification", nullable = false)
    private Vstatus vstatus;


    public enum Vstatus {
        VERIFICATION_NEEDED, PENDING,VERIFIED,NOTVERIFIED
    }
}
