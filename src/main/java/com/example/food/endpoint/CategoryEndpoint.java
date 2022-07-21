package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.domain.Category;
import com.example.food.dto.view.MerchantView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoryEndpoint {
    private final CategoryService categoryService;

    @Operation(description = "Danh sách categories")
    @GetMapping("")
    public ResponseEntity<ResponseBody<List<Category>>> findAll() {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, categoryService.findAll()), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
