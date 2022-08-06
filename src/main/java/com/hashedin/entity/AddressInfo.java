package com.hashedin.entity;

import io.swagger.annotations.ApiModel;
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
@Table(name = "addressinfo")
@ApiModel(description="Represents the Address Information of the users")
public class AddressInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id of the particular user
     */
    @Id
    @Size(max = 10)
    @ApiModelProperty(notes = "Address Id")
    @Column(name = "addressId", nullable = false, updatable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long addressId;


    /**
     * FOREIGN KEY (FROM USERINFO - userId i.e., id)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @ApiModelProperty(notes="Foreign Key from userInfo")
    private UserInfo userInfo;

    /**
     * Address Line 1 of the user
     */
    @Size(max=500)
    @NotNull(message="Address Line 1 cannot be null")
    @ApiModelProperty(notes="Address of the user")
    @Column(name="addressLine1")
    private String addressLine1;


    /**
     * Address Line 2 of the user
     */
    @Size(max=500)
    @ApiModelProperty(notes="Address Line 2 of the user")
    @Column(name="addressLine2")
    private String addressLine2;


    /**
     * Pincode
     */
    @Size(max=6)
    @NotNull(message="Pincode of the user")
    @ApiModelProperty(notes="Pincode of the user")
    @Column(name="pincode")
    private int pincode;


    /**
     * City
     */
    @Size(max=50)
    @NotNull(message="City cannot be null")
    @ApiModelProperty(notes="City of the user")
    @Column(name="city")
    private String city;


    /**
     * State
     */
    @Size(max=50)
    @NotNull(message="State cannot be null")
    @ApiModelProperty(notes="State")
    @Column(name="state")
    private String state;

}
