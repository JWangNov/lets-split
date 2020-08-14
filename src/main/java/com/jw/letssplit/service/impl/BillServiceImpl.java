package com.jw.letssplit.service.impl;

import com.jw.letssplit.dao.BillMapper;
import com.jw.letssplit.po.Bill;
import com.jw.letssplit.po.BillExample;
import com.jw.letssplit.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired(required = false)
    private BillMapper billMapper;

    @Override
    public List<Bill> listAllBill() {
        return billMapper.selectByExample(new BillExample());
    }

    @Override
    public List<Bill> listBillOfUser(Integer userId) {
        BillExample example = new BillExample();
        example.createCriteria()
                .andUidEqualTo(userId);
        return billMapper.selectByExample(example);
    }

    @Override
    public List<Bill> listUnpaidBillOfUser(Integer userId) {
        BillExample example = new BillExample();
        example.createCriteria()
                .andUidEqualTo(userId)
                .andStatusEqualTo(false);
        return billMapper.selectByExample(example);
    }

    @Override
    public Bill getBill(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteBill(Long id) {
        return billMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBill(List<Long> ids) {
        ids.forEach(this::deleteBill);
    }

    @Override
    public int createBill(Bill bill) {
        return billMapper.insertSelective(bill);
    }

    @Override
    public int updateBill(Long id, Bill bill) {
        bill.setId(id);
        return billMapper.updateByPrimaryKeySelective(bill);
    }
}
