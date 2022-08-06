package com.hashedin.entity;

import com.hashedin.audit.Auditable;
import com.hashedin.dto.BuyerVerificationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userinfo")
@ApiModel(description="Represents the User Information details")
public class UserInfo extends Auditable {

    private static final long serialVersionUID = 1L;

    /**
     * Id of the user
     */
    @Id
    @Size(max=10)
    @Column(name="userId", unique = true, insertable = true)
    @ApiModelProperty(notes="id of the user")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long userId;

    /**
     * LOGININFO Table (SHARED PRIMARY KEY - id)
     */
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private LoginInfo loginInfo;

    /**
     * ADDRESS INFO TABLE (FOREIGN KEY - userId i.e., id)
     */
    @OneToOne(mappedBy = "userInfo", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    private AddressInfo addressInfo;

    /**
     * PRODUCT INFO TABLE (FOREIGN KEY - userId i.e., id)
     */
    @OneToOne(mappedBy = "userInfo", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    private ProductInfo productInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userInfo")
    private SellerVerification sellerVerification;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userInfo")
    private Set<BuyerVerification> buyerVerification;

    /**
     * Type of the user - Buyer, Seller
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    /**
     * First name of the user
     */
//    @NotNull(message = "First Name is mandatory")
    @Size(min=2, max=30)
    @ApiModelProperty(notes="First name of the user")
    @Column(name="fname")
    private String fname;

    /**
     * Last name of the user
     */
//    @NotNull(message = "Name is mandatory")
    @Size(min=2, max=30)
    @ApiModelProperty(notes="Last name of the user")
    @Column(name="lname")
    private String lname;

    /**
     * Business Name of the user
     */
//    @NotNull(message = "Business Name is mandatory")
    @Size(min=2, max=30)
    @ApiModelProperty(notes="Business name of the user")
    @Column(name="bname")
    private String bname;

    /**
     * EmailId of the user
     */
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is mandatory")
    @Size(min=2, max=50)
    @ApiModelProperty(notes="Email-Id of the user")
    @Column(name="emailId")
    private String emailId;

    /**
     * Contact Number of the user
     */
    @NotNull(message = "Contact number is mandatory")
    @Size(max=10, message = "Contact number cannot exceed 10  characters")
    @Column(name="contactNumber")
    @ApiModelProperty(notes="contact number of the user")
    private String contactNumber;

    /**
     * isDeleted for soft delete
     */
    @Column(name="isDeleted", columnDefinition = "boolean default false")
    @ApiModelProperty(notes="isDeleted for softDelete")
    private boolean isDeleted = false;

    /**
     * Gender Type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "varchar(32) default 'UNKNOWN'")
    private Gender gender = Gender.UNKNOWN;

    @Column(name="dateOfBirth")
    private LocalDate dateOfBirth;

    public UserInfo(Optional<UserInfo> userInfo) {
        super();
    }

    public enum Type {
        BUYER,SELLER,GUEST
    }

    public enum Gender {
        MALE,FEMALE,OTHER,UNKNOWN
    }
}
