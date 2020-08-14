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
    public CommonResult<User> createUser(@RequestBody String username) {
        User user = new User();
        user.setUsername(username);
        int id = userService.createUser(user);
        log.info("[createUser][success, username {}, id {}]", username, id);
        return CommonResult.success(user);
    }

    @ApiOperation(value = "list all users")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<User>> listAllUser() {
        return CommonResult.success(userService.listAllUser());
    }

    @ApiOperation(value = "rename a user")
    @PostMapping("/rename")
    @ResponseBody
    public CommonResult<User> renameUser(@RequestBody User user) {
        int count = userService.updateUser(user.getId(), user);
        if (count != 1) {
            log.error("[renameUser][failed, cannot find user id: {}]", user.getId());
            return CommonResult.failed("user not found");
        }
        log.info("[renameUser][success, id: {}]", user.getId());
        return CommonResult.success(user);
    }

    @ApiOperation(value = "delete a user")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public CommonResult<Object> deleteUser(@PathVariable("id") Integer id) {
        int count = userService.deleteUser(id);
        if (count != 1) {
            log.error("[deleteUser][failed, cannot find user id: {}]", id);
            return CommonResult.failed("user not found");
        }
        log.info("[deleteUser][success, id: {}]", id);
        return CommonResult.success(null);
    }
}
