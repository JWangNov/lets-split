package com.jw.letssplit.controller;

import com.jw.letssplit.common.CommonResult;
import com.jw.letssplit.po.Bill;
import com.jw.letssplit.po.User;
import com.jw.letssplit.service.BillService;
import com.jw.letssplit.service.UserService;
import com.jw.letssplit.vo.StatementBrief;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "StatementController")
@Controller
@RequestMapping("/statement")
@Slf4j
public class StatementController {

    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "get brief unpaid statement of a user")
    @GetMapping("/brief/unpaid/{uid}")
    @ResponseBody
    public CommonResult<StatementBrief> getBriefStatement(@PathVariable("uid") Integer uid) {
        User user = userService.getUser(uid);
        if (user == null) {
            log.error("[getBriefStatement][failed, user #{} not found]", uid);
            return CommonResult.failed("user not found");
        }
        StatementBrief statement = new StatementBrief();
        statement.setOwner(user);

        Map<User, Double> payeeBalanceMap = new HashMap<>();
        List<Bill> unpaidBills = billService.listUnpaidBillOfUser(uid);
        for (Bill bill : unpaidBills) {
            User payee = userService.getUser(bill.getPaidByUid());
            Double balance = bill.getBalance();
            payeeBalanceMap.put(
                    payee,
                    payeeBalanceMap.getOrDefault(payee, 0d) + balance
            );
        }
        statement.setPayeeBalanceMap(payeeBalanceMap);
        log.info("[getBriefStatement][success]");
        return CommonResult.success(statement);
    }
}
