package com.jw.letssplit.service;

import java.util.List;

public interface UserService {

    /**
     * delete a user by its id
     */
    void delete(Integer id);

    /**
     * delete users
     */
    void delete(List<Integer> ids);

    /**
     * create a user
     */
    void create(String username);

    /**
     * rename a user's name
     */
    void rename(Integer id, String username);
}
