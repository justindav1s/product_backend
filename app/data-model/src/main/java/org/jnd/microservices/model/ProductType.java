package org.jnd.microservices.model;

public enum ProductType {

    FOOD,
    GADGET,
    CLOTHES;

    public String toString(){
        switch(this){
            case FOOD:
                return "food";
            case GADGET:
                return "gadgets";
            case CLOTHES:
                return "clothes";
        }
        return null;
    }

    public static ProductType value(Class<ProductType> enumType, String value){
        if(value.equalsIgnoreCase(FOOD.toString()))
            return ProductType.FOOD;
        else if(value.equalsIgnoreCase(GADGET.toString()))
            return ProductType.GADGET;
        else if(value.equalsIgnoreCase(CLOTHES.toString()))
            return ProductType.CLOTHES;
        else
            return null;
    }
}
