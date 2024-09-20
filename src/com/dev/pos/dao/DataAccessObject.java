package com.dev.pos.dao;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.dao.custom.CustomerDao;
import com.dev.pos.dao.custom.ProductDao;
import com.dev.pos.dao.custom.UserDao;
import com.dev.pos.dao.impl.CustomerDaoImpl;
import com.dev.pos.dao.impl.ProductDaoImpl;
import com.dev.pos.dao.impl.UserDaoImpl;
import com.dev.pos.dto.CustomerDto;
import com.dev.pos.dto.ProductDto;
import com.dev.pos.dto.UserDto;
import com.dev.pos.entity.Customer;
import com.dev.pos.entity.Product;
import com.dev.pos.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {

    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    //=================Start User=================
    public boolean createUser(UserDto userDto) throws SQLException, ClassNotFoundException {

        return userDao.save(
                new User(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
    }

    public UserDto findUser(String email) throws SQLException, ClassNotFoundException {

        User user = userDao.find(email);
        if (user != null) {
            return new UserDto(
                    user.getEmail(),
                    user.getPassword()
            );
        }
        return null;
    }
    //=================End User=================

    //=================Start Customer=================
    public boolean createCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {

        return customerDao.save(
                new Customer(
                        dto.getEmail(),
                        dto.getName(),
                        dto.getContact(),
                        dto.getSalary()
                )
        );
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {

        return customerDao.update(
                new Customer(
                        dto.getEmail(),
                        dto.getName(),
                        dto.getContact(),
                        dto.getSalary()
                )
        );
    }

    public boolean deleteCustomer(String email) throws SQLException, ClassNotFoundException {

        return customerDao.delete(email);
    }

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

    public List<CustomerDto> findAllCustomers() throws SQLException, ClassNotFoundException {

        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer c : customerDao.findAll()) {
            customerDtos.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return customerDtos;
    }

    public List<CustomerDto> searchCustomers(String searchText) throws SQLException, ClassNotFoundException {

        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer c : customerDao.search(searchText)) {
            customerDtos.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return customerDtos;
    }
    //=================End Customer=================

    //=================Start Product===============
    public int getLastProductID() throws SQLException, ClassNotFoundException {

        return productDao.getLastProductID();
    }

    public boolean saveProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {

        return productDao.save(
                new Product(
                        productDto.getCode(),
                        productDto.getDescription()
                )
        );
    }

    public boolean updateProduct(ProductDto productDto) throws SQLException, ClassNotFoundException {

        return productDao.update(
                new Product(
                        productDto.getCode(),
                        productDto.getDescription()
                )
        );
    }

    public boolean deleteProduct(int Code) throws SQLException, ClassNotFoundException {

        return productDao.delete(Code);
    }

    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException {

        Product product = productDao.find(code);
        if (product != null) {
            return new ProductDto(
                    product.getCode(),
                    product.getDescription()
            );
        }
        return null;
    }

    public List<ProductDto> getAllProducts() throws SQLException, ClassNotFoundException {

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.findAll()) {
            productDtos.add(new ProductDto(
                    p.getCode(),
                    p.getDescription()
            ));
        }
        return productDtos;
    }

    public List<ProductDto> searchProductByDescription(String searchText) throws SQLException, ClassNotFoundException {

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.searchByDescription(searchText)) {
            productDtos.add(new ProductDto(
                    p.getCode(),
                    p.getDescription()
            ));
        }
        return productDtos;
    }
    //=================End Product=================
}
