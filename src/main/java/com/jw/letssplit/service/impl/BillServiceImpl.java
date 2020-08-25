package com.jw.letssplit.service.impl;

import com.jw.letssplit.dao.BillMapper;
import com.jw.letssplit.po.Bill;
import com.jw.letssplit.po.BillExample;
import com.jw.letssplit.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        int count = billMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            log.error("[deleteBill][failed, bill #{} not found]", id);
        } else {
            log.info("[deleteBill][success, delete bill #{}]", id);
        }
        return count;
    }

    @Transactional
    @Override
    public long deleteBill(List<Long> ids) {
        long count = 0;
        for (Long id : ids) {
            count += billMapper.deleteByPrimaryKey(id);
        }
        if (count != ids.size()) {
            throw new RuntimeException();
        }
        return count;
    }

    @Transactional
    @Override
    public int createBill(Bill bill) {
        long id = billMapper.insertSelective(bill);
        log.info("[createBillSingle][success, create single bill, id: {}]", id);
        return (int) id;
    }

    @Transactional
    @Override
    public int updateBill(Long id, Bill bill) {
        bill.setId(id);
        log.info("[updateBill][success, bill #{}]", id);
        return billMapper.updateByPrimaryKeySelective(bill);
    }
}
