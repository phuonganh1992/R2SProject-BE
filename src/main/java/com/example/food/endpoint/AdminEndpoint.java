package com.example.food.endpoint;

import com.example.food.advice.BadRequestException;
import com.example.food.advice.CommonException;
import com.example.food.dto.command.UserUpdateCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.dto.view.UserView;
import com.example.food.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/admin")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminEndpoint extends AbstractEndpoint {
    private final UserService userService;

    @Operation(description = "Danh sách users")
    @GetMapping("/users")
    public ResponseEntity<ResponseBody<List<UserView>>> findAll(DefaultQueryCriteria criteria) {
        try {
            return buildPagingResponse(userService.findAll(criteria));
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Admin thay đổi trạng thái user")
    @PostMapping("/users/{id}/change-status")
    public ResponseEntity<ResponseBody<UserView>> changeStatus(@PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.changeStatus(id)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Admin update user")
    @PatchMapping("/users/{id}")
    public ResponseEntity<ResponseBody<UserView>> updateProfile(@PathVariable("id") UUID id, @RequestBody UserUpdateCommand command) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.adminUpdate(id, command)), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ResponseBody(Response.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }


}
