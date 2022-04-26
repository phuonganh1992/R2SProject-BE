package com.example.food.endpoint;

import com.example.food.advice.BadRequestException;
import com.example.food.advice.CommonException;
import com.example.food.dto.command.UserUpdateCommand;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.dto.view.UserView;
import com.example.food.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/profile")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfileEndpoint {
    private final UserService userService;

    @Operation(description = "Profile of user")
    @GetMapping("")
    public ResponseEntity<ResponseBody<UserView>> profile(Principal principal) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.findByUsername(principal.getName())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Update profile")
    @PatchMapping("/update")
    public ResponseEntity<ResponseBody<UserView>> updateProfile(Principal principal, @RequestBody UserUpdateCommand command) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.update(principal.getName(), command)), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ResponseBody(Response.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
