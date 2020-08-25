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
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void deleteUser(List<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            count += userMapper.deleteByPrimaryKey(id);
        }
        if (count != ids.size()) {
            throw new RuntimeException();
        }
    }

    @Override
    public int createUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(Integer id, User user) {
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
