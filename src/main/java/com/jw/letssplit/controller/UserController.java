package com.jw.letssplit.controller;

import com.jw.letssplit.common.CommonResult;
import com.jw.letssplit.po.User;
import com.jw.letssplit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UserController")
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "create a new user")
    @PostMapping("/create")
    @ResponseBody
    public CommonResult<User> create(@RequestBody String username) {
        User user = new User();
        user.setUsername(username);
        userService.createUser(user);
        return CommonResult.success(user);
    }

    @ApiOperation(value = "list all users")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<User>> listAllUser() {
        return CommonResult.success(userService.listAllUser());
    }
}
