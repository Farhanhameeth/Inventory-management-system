package com.dev.pos.dao;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.dao.impl.*;

public class DaoFactory {

    private static  DaoFactory daoFactory;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        if (daoFactory == null ) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public <T> T getDao(DaoType daoType){
        switch (daoType){
            case USER:
                return (T) new UserDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case BATCH:
                return (T) new BatchDaoImpl();
            case ITEM_DETAIL:
                return (T) new ItemDetailDaoImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailDaoImpl();
            default:
                return null;
        }
    }
}
