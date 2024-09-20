package com.dev.pos.bo.impl;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.custom.CustomerBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.CustomerDao;
import com.dev.pos.dto.CustomerDto;
import com.dev.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.save(
                new Customer(
                        customerDto.getEmail(),
                        customerDto.getName(),
                        customerDto.getContact(),
                        customerDto.getSalary()
                )
        );
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(
                        customerDto.getEmail(),
                        customerDto.getName(),
                        customerDto.getContact(),
                        customerDto.getSalary()
                )
        );
    }

    @Override
    public boolean deleteCustomer(String email) throws SQLException, ClassNotFoundException {
        return customerDao.delete(email);
    }

    @Override
    public CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        Customer customer = customerDao.find(email);
        if (customer != null) {
            return new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            );
        }
        return null;
    }

    @Override
    public List<CustomerDto> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer c : customerDao.search(value)) {
            customerDtos.add(
                    new CustomerDto(
                            c.getEmail(),
                            c.getName(),
                            c.getContact(),
                            c.getSalary()
                    )
            );
        }
        return customerDtos;
    }

    @Override
    public List<CustomerDto> findAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer c : customerDao.findAll()) {
            customerDtos.add(
                    new CustomerDto(
                            c.getEmail(),
                            c.getName(),
                            c.getContact(),
                            c.getSalary()
                    )
            );
        }
        return customerDtos;
    }
}
