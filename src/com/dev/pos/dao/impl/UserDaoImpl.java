package com.dev.pos.dao.impl;

import com.dev.pos.dao.CrudUtil;
import com.dev.pos.dao.custom.UserDao;
import com.dev.pos.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO user VALUES(?,?)";
        return CrudUtil.execute(
                sql,
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(String email) {
        return false;
    }

    @Override
    public User find(String email) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM user WHERE email = ?";
        ResultSet resultSet = CrudUtil.execute(sql, email);
        if (resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<User> search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

}
