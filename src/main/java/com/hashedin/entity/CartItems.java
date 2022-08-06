package com.hashedin.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartitems")
@ApiModel(description="Represents the CartItems Information details")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    private int productId;
    private int userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "verification", nullable = false)
    private Vstatus vstatus;

    public enum Vstatus {
        VERIFICATION_NEEDED,PENDING,VERIFIED, NOTVERIFIED
    }
}
