package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.dto.view.UserView;
import com.example.food.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/users")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserEndpoint {
    private final UserService userService;

    @Operation(description = "Danh sách users")
    @GetMapping("")
    public ResponseEntity<ResponseBody<List<UserView>>> findAll(DefaultQueryCriteria criteria) {
        try{
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.findAll(criteria)), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Find user by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<UserView>> findById(@PathVariable UUID id) {
        try{
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.findById(id)), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
