package com.jw.letssplit.service;

import com.jw.letssplit.po.Bill;

import java.util.List;

public interface BillService {

    List<Bill> listAllBill();

    List<Bill> listBillOfUser(Integer userId);

    List<Bill> listUnpaidBillOfUser(Integer userId);

    Bill getBill(Integer id);

    int deleteBill(Integer id);

    void deleteBill(List<Integer> ids);

    int createBill(Bill bill);

    int updateBill(Integer id, Bill bill);
}
