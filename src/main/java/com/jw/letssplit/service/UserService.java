package com.jw.letssplit.service;

import com.jw.letssplit.po.User;

import java.util.List;

public interface UserService {

    List<User> listAllUser();

    User getUser(Integer id);

    int deleteUser(Integer id);

    void deleteUser(List<Integer> ids);

    int createUser(User user);

    int updateUser(Integer id, User user);
}
