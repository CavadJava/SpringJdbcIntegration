package com.heyatiminkodlar.jdbc.praktika10.dao;


import com.heyatiminkodlar.jdbc.praktika10.domain.User;

import java.util.List;

/**
 * Created by root on 11/30/16.
 */
public interface IUserDao {

        public void add(User user, int gid);
        public void update(User user);
        public void delete(int id);
        public User load(int id);
        public List<User> list(String sql, Object[] args);
}
