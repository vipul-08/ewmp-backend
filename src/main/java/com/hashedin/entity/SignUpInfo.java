package com.hashedin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "signupinfo")
@ApiModel(description="Represents the SignUp Information")
public class SignUpInfo {

    /**
     * Id of the product
     */
    @Id
    @Column(name="id")
    @ApiModelProperty(notes="id of the user")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String otp;
    private String contactNumber;
    private String email;
    private long timestamp;

}
