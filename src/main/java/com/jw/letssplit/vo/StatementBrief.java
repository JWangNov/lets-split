package com.jw.letssplit.vo;

import com.jw.letssplit.po.User;
import lombok.Data;

import java.util.Map;

@Data
public class StatementBrief {
    private User owner;
    private Map<User, Double> payeeBalanceMap;
}
