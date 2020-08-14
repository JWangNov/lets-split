package com.jw.letssplit.controller;

import com.jw.letssplit.common.CommonResult;
import com.jw.letssplit.po.Bill;
import com.jw.letssplit.po.User;
import com.jw.letssplit.service.BillService;
import com.jw.letssplit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(tags = "BillController")
@Controller
@RequestMapping("/bill")
@Slf4j
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;


    // list bills =============================================================

    @ApiOperation(value = "list all bills")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<Bill>> listAllBill() {
        log.info("[listAllBill][success]");
        return CommonResult.success(billService.listAllBill());
    }

    @ApiOperation(value = "list all bills of a user")
    @GetMapping("/list/all/{uid}")
    @ResponseBody
    public CommonResult<List<Bill>> listUserBill(@PathVariable("uid") Integer uid) {
        User user = userService.getUser(uid);
        if (user == null) {
            log.error("[listUserBill][failed, user not found, uid: {}]", uid);
            return CommonResult.failed("user not found");
        }
        log.info("[listUserBill][success]");
        return CommonResult.success(billService.listBillOfUser(uid));
    }

    @ApiOperation(value = "list all unpaid bills of a user")
    @GetMapping("/list/unpaid/{uid}")
    @ResponseBody
    public CommonResult<List<Bill>> listUserBillUnpaid(@PathVariable("uid") Integer uid) {
        User user = userService.getUser(uid);
        if (user == null) {
            log.error("[listUserBillUnpaid][failed, user not found, uid: {}]", uid);
            return CommonResult.failed("user not found");
        }
        log.info("[listUserBillUnpaid][success]");
        return CommonResult.success(billService.listUnpaidBillOfUser(uid));
    }
}
