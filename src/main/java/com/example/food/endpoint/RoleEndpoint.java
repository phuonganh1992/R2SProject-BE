package com.example.food.endpoint;

import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/roles")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RoleEndpoint {
    private final RoleService roleService;

    @PostMapping("")
    public ResponseEntity<ResponseBody> create(@RequestBody String name) {
        return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, roleService.create(name)), HttpStatus.CREATED);
    }
}
