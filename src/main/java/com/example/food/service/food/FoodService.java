package com.example.food.service.food;

import com.example.food.advice.CommonException;
import com.example.food.domain.Food;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.FoodView;
import com.example.food.dto.view.Response;
import com.example.food.repository.IFoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodService {
    @Autowired
    private IFoodRepository foodRepository;

    public List<FoodView> findAll(DefaultQueryCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getSize(), criteria.getSortable());
        Page<Food> pageUsers = foodRepository.findAll(pageable);
        if (pageUsers.getTotalElements() == 0) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        return pageUsers.getContent().stream().map(FoodView::from)
                .collect(Collectors.toList());
    }

    public FoodView findById(Long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if(!optionalFood.isPresent()){
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        else return FoodView.from(optionalFood.get());
    }


}
