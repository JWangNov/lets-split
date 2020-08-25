package com.jw.letssplit.service.impl;

import com.jw.letssplit.dao.UserMapper;
import com.jw.letssplit.po.User;
import com.jw.letssplit.po.UserExample;
import com.jw.letssplit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public List<User> listAllUser() {
        log.info("[listAllUser][success]");
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int deleteUser(Integer id) {
        int count = userMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            log.error("[deleteUser][failed, cannot find user id: {}]", id);
        } else {
            log.info("[deleteUser][success, id: {}]", id);
        }
        return count;
    }

    @Transactional
    @Override
    public int deleteUser(List<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            count += userMapper.deleteByPrimaryKey(id);
        }
        if (count != ids.size()) {
            log.error("[deleteUser][failed, cannot find user]");
            throw new RuntimeException();
        }
        log.info("[deleteUser][success]");
        return count;
    }

    @Transactional
    @Override
    public int createUser(User user) {
        int id = userMapper.insertSelective(user);
        log.info("[createUser][success, id {}]", id);
        return id;
    }

    @Transactional
    @Override
    public int updateUser(Integer id, User user) {
        user.setId(id);
        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count != 1) {
            log.error("[updateUser][failed, cannot find user id: {}]", id);
        } else {
            log.info("[updateUser][success, id: {}]", user.getId());
        }
        return count;
    }
}
