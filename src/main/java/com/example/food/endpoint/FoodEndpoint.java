package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.FoodView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.food.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/foods")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FoodEndpoint {
    private final FoodService foodService;

    @Operation(description = "Danh sách foods")
    @GetMapping("")
    public ResponseEntity<ResponseBody<List<FoodView>>> findAll(DefaultQueryCriteria criteria) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, foodService.findAll(criteria)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Find food by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<FoodView>> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, foodService.findById(id)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
