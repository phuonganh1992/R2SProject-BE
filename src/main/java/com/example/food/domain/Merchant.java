package com.example.food.domain;

import com.example.food.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.orderfood.constant.Constant;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"taxIdentificationNumber"})})
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String url;
    private String registrationCertificate;
    private String taxIdentificationNumber;
    private String representative;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Float rate;
    private Integer quantityRate;
    private Constant.MerchantStatus status;
    private Constant.ChannelName channel;

    @OneToMany
    private List<String> merchantImages;
}
