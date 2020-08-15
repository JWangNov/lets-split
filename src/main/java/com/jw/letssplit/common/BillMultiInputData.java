package com.jw.letssplit.common;

import lombok.Data;

import java.util.List;

@Data
public class BillMultiInputData {
    Integer payerUid;
    List<Integer> payeeUids;
    Double totalBalance;
    Boolean status;
    String comment;
}
