package com.example.food.service.merchant;

import com.example.food.advice.CommonException;
import com.example.food.constant.Constant;
import com.example.food.domain.Category;
import com.example.food.domain.Food;
import com.example.food.domain.Merchant;
import com.example.food.domain.Role;
import com.example.food.dto.command.MerchantRegisterCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.FoodView;
import com.example.food.dto.view.MerchantView;
import com.example.food.dto.view.Response;
import com.example.food.repository.IFoodRepository;
import com.example.food.repository.IMerchantRepository;
import com.example.food.repository.IRoleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Lazy(value = false)
public class MerchantService implements IMerchantService {
    @Autowired
    private IMerchantRepository merchantRepository;

    @Autowired
    private IFoodRepository foodRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    public MerchantView create(@NonNull MerchantRegisterCommand command) {
        Set<ConstraintViolation<MerchantRegisterCommand>> constraintViolations = validator.validate(command);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        if (merchantRepository.existsByEmail(command.getEmail()))
            throw new CommonException(Response.EMAIL_IS_EXISTS, Response.EMAIL_IS_EXISTS.getResponseMessage());
        if (merchantRepository.existsByUsername(command.getUsername()))
            throw new CommonException(Response.USERNAME_IS_EXISTS, Response.USERNAME_IS_EXISTS.getResponseMessage());

        Merchant merchant = Merchant.builder()
                .username(command.getUsername())
                .password(passwordEncoder.encode(command.getPassword()))
                .name(command.getName())
                .address(command.getAddress())
                .representative(command.getRepresentative())
                .email(command.getEmail())
                .phone(command.getPhone())
                .registrationCertificate(command.getRegistrationCertificate())
                .taxIdentificationNumber(command.getTaxIdentificationNumber())
                .channel(Constant.ChannelName.USER)
                .status(Constant.MerchantStatus.ACTIVATE)
                .createdAt(Instant.now())
                .createdBy(command.getUsername())
                .build();

        Merchant merchantSave = merchantRepository.save(merchant);
        return convertMerchantView(merchantSave);
    }

    public List<MerchantView> findAll(DefaultQueryCriteria criteria) {
        Pageable pageable= PageRequest.of(criteria.getPage()-1, criteria.getSize(), criteria.getSortable());
        Page<Merchant> pageMerchants = merchantRepository.findAll(pageable);
        if(pageMerchants.getTotalElements()==0) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        return pageMerchants.getContent().stream().map(this::convertMerchantView)
                .collect(Collectors.toList());
    }

    public List<MerchantView> list() {
        Iterable<Merchant> merchantIterable = merchantRepository.findAll();
        List<Merchant> merchants = IterableUtils.toList(merchantIterable);

        if(merchants.isEmpty()){
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        List<MerchantView> merchantViews = merchants.stream().map(MerchantView::from).collect(Collectors.toList());
        return merchantViews;
    }

    public MerchantView findById(Long id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if(!optionalMerchant.isPresent()){
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        else return convertMerchantView(optionalMerchant.get());
    }

    public void delete(Long id) {
        Optional<Merchant> merchantOptional = merchantRepository.findById(id);
        if (merchantOptional.isPresent()) merchantRepository.deleteById(id);
        else throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
    }

    public MerchantView convertMerchantView(Merchant merchant){
        MerchantView view = MerchantView.from(merchant);
        Iterable<Food> foodIterable = foodRepository.findAllByMerchantId(merchant.getId());
        List<FoodView> foods = IterableUtils.toList(foodIterable).stream().map(FoodView::from).collect(Collectors.toList());
        view.setFoods(foods);
        return view;
    }
}
