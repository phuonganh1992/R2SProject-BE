package com.example.food.domain;

import com.example.food.constant.Constant;
import com.example.food.converter.ListToJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"taxIdentificationNumber"})})
public class Merchant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String url;
    private String registrationCertificate;
    private String taxIdentificationNumber;
    private String representative;
    private Instant openTime;
    private Instant closeTime;
    private Float rate;
    private Integer quantityRate;
    private Constant.MerchantStatus status;
    private Constant.ChannelName channel;

    @Column(columnDefinition = "json")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> images;

    private Instant createdAt;
    private String createdBy;
    private Instant modifiedAt;
    private String modifiedBy;

}
