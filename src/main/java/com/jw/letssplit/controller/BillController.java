package com.jw.letssplit.controller;

import com.jw.letssplit.common.CommonResult;
import com.jw.letssplit.po.Bill;
import com.jw.letssplit.po.User;
import com.jw.letssplit.service.BillService;
import com.jw.letssplit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    // create bill ============================================================

    @ApiOperation(value = "create a bill with 1 payee & 1 payer")
    @PostMapping("/create/single")
    @ResponseBody
    public CommonResult<Bill> createBillSingle(@RequestBody Bill inputBill) {
        User userPayee = userService.getUser(inputBill.getUid());
        User userPayer = userService.getUser(inputBill.getPaidByUid());
        if (userPayee == null || userPayer == null) {
            log.error("[createBillSingle][failed, user not found, input uid: {}]",
                    userPayee == null ? inputBill.getUid() : inputBill.getPaidByUid());
            return CommonResult.failed("user not found");
        }
        if (inputBill.getBalance() < 0) {
            log.error("[createBillSingle][failed, balance should be >= 0, input balance: {}]", inputBill.getBalance());
            return CommonResult.failed("balance incorrect");
        }

        Bill bill = new Bill();
        BeanUtils.copyProperties(inputBill, bill);
        Date currentDate = new Date();
        bill.setCreateTime(currentDate);
        if (bill.getBalance() == 0) {
            bill.setStatus(true);
            bill.setDoneTime(currentDate);
        }
        if (!bill.getStatus()) {
            bill.setDoneTime(null);
        }

        long id = billService.createBill(bill);
        bill.setId(id);
        log.info("[createBillSingle][success, create single bill, id: {}]", id);
        return CommonResult.success(bill);
    }

    @ApiOperation(value = "create a bill with N payee & 1 payer")
    @PostMapping("/create/multi")
    @ResponseBody
    public CommonResult<Object> createBillMulti(
            @RequestBody Integer payerUid,
            @RequestBody List<Integer> payeeUids,
            @RequestBody Double totalBalance,
            @RequestBody Boolean status,
            @RequestBody String comment
    ) {
        if (totalBalance < 0) {
            log.error("[createBillMulti][failed, balance should be >= 0, input balance: {}]", totalBalance);
            return CommonResult.failed("balance incorrect");
        }
        if (userService.getUser(payerUid) == null) {
            log.error("[createBillMulti][failed, user not found, input uid: {}]", payerUid);
            return CommonResult.failed("user not found");
        }
        List<Integer> deduplicatedPayeeUids = payeeUids.stream().distinct().collect(Collectors.toList());
        if (deduplicatedPayeeUids.size() < 1) {
            log.error("[createBillMulti][failed, invalid input]");
            return CommonResult.failed("invalid input");
        }
        for (Integer payeeUid : deduplicatedPayeeUids) {
            if (userService.getUser(payeeUid) == null) {
                log.error("[createBillMulti][failed, user not found, input uid: {}]", payeeUid);
                return CommonResult.failed("user not found");
            }
        }

        Double balance = totalBalance / deduplicatedPayeeUids.size();
        Date currentDate = new Date();
        for (Integer payeeUid : deduplicatedPayeeUids) {
            if (payeeUid.equals(payerUid)) continue;
            Bill bill = new Bill();
            bill.setUid(payeeUid);
            bill.setPaidByUid(payerUid);
            bill.setBalance(balance);
            bill.setCreateTime(currentDate);
            bill.setStatus(status);
            bill.setComment(comment);
            billService.createBill(bill);
        }
        log.info("[createBillMulti][success]");
        return CommonResult.success(null);
    }
}
