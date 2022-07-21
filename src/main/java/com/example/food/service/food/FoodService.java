package com.example.food.service.food;

import com.example.food.advice.CommonException;
import com.example.food.constant.Constant;
import com.example.food.domain.Category;
import com.example.food.domain.Food;
import com.example.food.domain.Merchant;
import com.example.food.dto.command.FoodCreateCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.FoodView;
import com.example.food.dto.view.Response;
import com.example.food.repository.ICategoryRepository;
import com.example.food.repository.IFoodRepository;
import com.example.food.repository.IMerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodService {
    @Autowired
    private IFoodRepository foodRepository;

    @Autowired
    private IMerchantRepository merchantRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    public Page<FoodView> findAll(DefaultQueryCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), criteria.getSortable());
        Page<Food> pageFoods = foodRepository.findAll(pageable);
        if (pageFoods.getTotalElements() == 0) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        List<FoodView> list = pageFoods.getContent().stream().map(FoodView::from)
                .collect(Collectors.toList());

        Page<FoodView> page = new PageImpl<>(list, pageable, pageFoods.getTotalElements());
        return page;
    }

    public FoodView findById(Long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (!optionalFood.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        } else return FoodView.from(optionalFood.get());
    }

    public FoodView createFood(MultipartFile[] images, FoodCreateCommand command) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(command.getMerchantId());
        if (!optionalMerchant.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        Merchant merchant = optionalMerchant.get();

        Optional<Category> optionalCategory = categoryRepository.findById(command.getCategoryId());
        if (!optionalCategory.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        Category category = optionalCategory.get();

        Food food = Food.builder()
                .name(command.getName())
                .description(command.getDescription())
                .preparedTime(command.getPreparedTime())
                .price(command.getPrice())
                .merchant(merchant)
                .category(category)
                .createdAt(Instant.now())
                .build();
        List<String> foodImages = new ArrayList<>();
        try {
            for (MultipartFile file : images) {
                byte[] fileContent = file.getBytes();
                String outputFile = Base64.getEncoder().encodeToString(fileContent);
                foodImages.add("data:image/png;base64," + outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        food.setImages(foodImages);
        food.setStatus(Constant.FoodStatus.SERVING);
        return FoodView.from(foodRepository.save(food));
    }

    public FoodView update(Long id, MultipartFile[] images, FoodCreateCommand command) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (!optionalFood.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        Food food = optionalFood.get();

        if (StringUtils.hasText(command.getName())) {
            food.setName(command.getName());
        }
        if (StringUtils.hasText(command.getDescription())) {
            food.setDescription(command.getDescription());
        }
        if (command.getPreparedTime() != null) {
            food.setPreparedTime(command.getPreparedTime());
        }
        if (command.getPrice() != null) {
            food.setPrice(command.getPrice());
        }
        if (command.getMerchantId() != null) {
            Optional<Merchant> optionalMerchant = merchantRepository.findById(command.getMerchantId());
            if (optionalMerchant.isPresent()) {
                Merchant merchant = optionalMerchant.get();
                food.setMerchant(merchant);
            }
        }
        if(command.getCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(command.getCategoryId());
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                food.setCategory(category);
            }
        }
        if (images.length>0){
            List<String> foodImages = new ArrayList<>();
            try {
                for (MultipartFile file : images) {
                    byte[] fileContent = file.getBytes();
                    String outputFile = Base64.getEncoder().encodeToString(fileContent);
                    foodImages.add("data:image/png;base64," + outputFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            food.setImages(foodImages);
        }
        food.setCreatedAt(Instant.now());
        return FoodView.from(foodRepository.save(food));
    }
}
