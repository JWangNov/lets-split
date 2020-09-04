package com.jw.letssplit.service;

import com.jw.letssplit.po.Bill;

import java.util.List;

/**
 * User ID: {@link Integer}
 * <p>
 * Bill ID: {@link Long}
 */
public interface BillService {

    List<Bill> listAllBill();

    List<Bill> listBillOfUser(Integer userId);

    List<Bill> listUnpaidBillOfUser(Integer userId);

    Bill getBill(Long id);

    int deleteBill(Long id);

    long deleteBill(List<Long> ids);

    int createBill(Bill bill);

    int updateBill(Long id, Bill bill);
}
