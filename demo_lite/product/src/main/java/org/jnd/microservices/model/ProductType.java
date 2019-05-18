package org.jnd.microservices.model;

public enum ProductType {

    SALOON,
    SUV,
    HATCHBACK;

    public String toString(){
        switch(this){
            case SALOON:
                return "saloon";
            case SUV:
                return "suv";
            case HATCHBACK:
                return "hatchback";
        }
        return null;
    }

    public static ProductType value(Class<ProductType> enumType, String value){
        if(value.equalsIgnoreCase(SALOON.toString()))
            return ProductType.SALOON;
        else if(value.equalsIgnoreCase(SUV.toString()))
            return ProductType.SUV;
        else if(value.equalsIgnoreCase(HATCHBACK.toString()))
            return ProductType.HATCHBACK;
        else
            return null;
    }
}
