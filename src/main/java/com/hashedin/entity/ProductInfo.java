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
@Table(name = "productinfo")
@ApiModel(description="Represents the product Information details")
public class ProductInfo {

    /**
     * Id of the product
     */
    @Id
    @Column(name="productId")
    @ApiModelProperty(notes="id of the user")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int productId;
    private String planName;
    private String category;
    private String appliance;
    private int priceFrom;
    private int priceTo;
    private float mrp;
    private float discount;
    private String description;
    private float rating;
    private String covered;
    private String notCovered;
    private int planDuration;
    private boolean isDeleted = false;
    /**
     * FOREIGN KEY (FROM USERINFO - userId i.e., id)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId",insertable = false, updatable = false)
    @ApiModelProperty(notes="Foreign Key from userInfo")
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", planName='" + planName + '\'' +
                ", category='" + category + '\'' +
                ", appliance='" + appliance + '\'' +
                ", priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                ", mrp=" + mrp +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", covered='" + covered + '\'' +
                ", notCovered='" + notCovered + '\'' +
                '}';
    }
}
