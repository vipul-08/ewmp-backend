package com.hashedin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logininfo")
@ApiModel(description="Represents the Login Information details")
public class LoginInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id of the particular user
     */
    @Id
    @Size(max = 10)
    @ApiModelProperty(notes = "User Id")
    @Column(name = "userId", nullable = false, updatable = false)
    private long userId;

    /**
     * SHARED PRIMARY KEY FROM USER INFO (id i.e., id)
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "loginId", insertable = false, updatable = false)
    private UserInfo userInfo;

    /**
     * Password of a particular user
     */
    @Column(name = "password")
    @ApiModelProperty(notes = "User's Password")
    private String password;

    /**
     * OTP, when the user forgets the password
     */
    @Column(name = "otp")
    @ApiModelProperty(notes = "OTP of the user when requested for forgot password")
    private String otp;

    @Temporal(TIMESTAMP)
    @Column(name = "otpTimestamp")
    @ApiModelProperty(notes = "Timestamp for generated OTP")
    private Date otpTimestamp;

    /**
     * isDeleted for soft delete
     */
    @Column(name="isDeleted", columnDefinition = "boolean default false")
    @ApiModelProperty(notes="isDeleted for softDelete")
    private boolean isDeleted = false;

}
