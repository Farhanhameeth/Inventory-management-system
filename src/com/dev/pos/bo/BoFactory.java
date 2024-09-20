package com.dev.pos.bo;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.impl.*;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){

        if (boFactory == null){
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public <T> T getBo(BoType boType) {
        switch (boType) {
            case USER:
                return (T) new UserBoImpl();
            case CUSTOMER:
                return (T) new CustomerBoImpl();
            case PRODUCT:
                return (T) new ProductBoImpl();
            case BATCH:
                return (T) new BatchBoImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailBoImpl();
            case ITEM_DETAIL:
                return (T) new ItemDetailBoImpl();
            default:
                return null;
        }
    }
}
