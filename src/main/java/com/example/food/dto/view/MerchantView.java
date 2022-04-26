package com.example.food.dto.view;

import com.example.food.constant.Constant;
import com.example.food.domain.Merchant;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class MerchantView implements Serializable {
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
    private List<String> images;
    private List<FoodView> foods;

    public static MerchantView from(Merchant domain){
        return MerchantView.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .name(domain.getName())
                .email(domain.getName())
                .phone(domain.getPhone())
                .address(domain.getAddress())
                .url(domain.getUrl())
                .registrationCertificate(domain.getRegistrationCertificate())
                .taxIdentificationNumber(domain.getTaxIdentificationNumber())
                .representative(domain.getRepresentative())
                .openTime(domain.getOpenTime())
                .closeTime(domain.getCloseTime())
                .rate(domain.getRate())
                .quantityRate(domain.getQuantityRate())
                .status(domain.getStatus())
                .channel(domain.getChannel())
                .images(domain.getImages())
                .build();
    }
}
