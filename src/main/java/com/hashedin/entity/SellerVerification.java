package com.hashedin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sellerverification")
public class SellerVerification implements Serializable {

 @Id
 @Size(max=10)
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long verificationId;

 @NotNull(message = "GST NO. is mandatory")
 @Column(name = "gstNo")
 private  String gstNo;

 @NotNull(message = "Bank Account No. is mandatory")
 @Column(name="bankAccountNo")
 private String  bankAccountNo;

 @NotNull(message = "IFSC Code  is mandatory")
 @Column(name = "ifscCode")
 private String ifscCode;

 @Column(name="imageurl")
 private String imageUrl;

 @OneToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "userId", insertable = false, updatable = false)
 @ApiModelProperty(notes="Foreign Key from userInfo")
 private UserInfo userInfo;

 @Enumerated(EnumType.STRING)
 @Column(name = "verification", nullable = false)
 private BuyerVerification.Vstatus vstatus;


 public enum Vstatus {
  PENDING,VERIFIED, NOTVERIFIED
 }


}
