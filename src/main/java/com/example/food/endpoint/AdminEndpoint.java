package com.example.food.endpoint;

import com.example.food.advice.BadRequestException;
import com.example.food.advice.CommonException;
import com.example.food.domain.Category;
import com.example.food.domain.FileMetadata;
import com.example.food.dto.command.FoodCreateCommand;
import com.example.food.dto.command.UserUpdateCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.*;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.StorageService;
import com.example.food.service.category.CategoryService;
import com.example.food.service.food.FoodService;
import com.example.food.service.merchant.MerchantService;
import com.example.food.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/admin")
@RestController
@CrossOrigin(origins = "*", exposedHeaders = "x-requested-with, Request-Header, Response-Header, X-Page-Count, X-Page-Number, X-Page-Size, X-Total-Count")
@RequiredArgsConstructor
public class AdminEndpoint extends AbstractEndpoint {
    private final UserService userService;
    private final FoodService foodService;
    private final CategoryService categoryService;
    private final MerchantService merchantService;

    private final StorageService storageService;

    @Operation(description = "Danh sách users")
    @GetMapping("/users")
    public ResponseEntity<ResponseBody<List<UserView>>> getAllUser(DefaultQueryCriteria criteria) {
        try {
            return buildPagingResponse(userService.findAll(criteria));
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Profile of user")
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseBody<UserView>> profile(@PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.findById(id)), HttpStatus.OK);
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

    @PostMapping("/users/{id}/avatar")
    public ResponseEntity<ResponseBody<FileMetadata>> createAttachments(@RequestPart(value = "attachments") List<MultipartFile> files) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, storageService.createAttachment(files)), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ResponseBody(Response.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/users/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody<UserView>> uploadAvatar(@PathVariable("id") UUID id,
                                                               @RequestPart(value = "atts") MultipartFile[] atts) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, userService.uploadAvatar(id, atts)), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ResponseBody(Response.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Danh sách foods")
    @GetMapping("/foods")
    public ResponseEntity<ResponseBody<List<FoodView>>> getAllFoods(DefaultQueryCriteria criteria) {
        try {
            return buildPagingResponse(foodService.findAll(criteria));
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Create food")
    @PostMapping(value = "/foods", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody<FoodView>> createFood(@RequestPart(value = "images", required = false) MultipartFile[] images,
                                                             @RequestPart(value = "food", required = false) FoodCreateCommand command) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, foodService.createFood(images, command)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Update food")
    @PutMapping(value = "/foods/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody<FoodView>> updateFood(@PathVariable("id") Long id,
                                                             @RequestPart(value = "images", required = false) MultipartFile[] images,
                                                             @RequestPart(value = "food", required = false) FoodCreateCommand command) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, foodService.update(id, images, command)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Find food by id")
    @GetMapping("/foods/{id}")
    public ResponseEntity<ResponseBody<FoodView>> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, foodService.findById(id)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }


    @Operation(description = "Danh sách categories")
    @GetMapping("/categories")
    public ResponseEntity<ResponseBody<List<Category>>> findAll() {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, categoryService.findAll()), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Danh sách Merchants")
    @GetMapping("/merchants")
    public ResponseEntity<ResponseBody<List<MerchantView>>> listMerchants() {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, merchantService.list()), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }


}
