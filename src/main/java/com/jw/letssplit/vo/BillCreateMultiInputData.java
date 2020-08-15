package com.jw.letssplit.vo;

import lombok.Data;

import java.util.List;

@Data
public class BillCreateMultiInputData {
    private Integer payerUid;
    private List<Integer> payeeUids;
    private Double totalBalance;
    private Boolean status;
    private String comment;
}
