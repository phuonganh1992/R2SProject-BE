package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.MerchantView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.merchant.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/merchants")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MerchantEndpoint {
    private final MerchantService merchantService;

    @Operation(description = "Danh sách Merchants")
    @GetMapping("")
    public ResponseEntity<ResponseBody<List<MerchantView>>> findAll(DefaultQueryCriteria criteria) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, merchantService.findAll(criteria)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Find merchant by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<MerchantView>> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, merchantService.findById(id)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
