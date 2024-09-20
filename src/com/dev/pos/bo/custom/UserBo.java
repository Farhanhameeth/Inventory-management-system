package com.dev.pos.bo.custom;

import com.dev.pos.dto.UserDto;

import java.sql.SQLException;

public interface UserBo {

    public boolean saveUser(UserDto  dto) throws SQLException, ClassNotFoundException;

    public UserDto findUser(String email) throws SQLException, ClassNotFoundException;

}
