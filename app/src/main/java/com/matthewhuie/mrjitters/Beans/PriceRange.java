package com.matthewhuie.mrjitters.Beans;

/**
 * Created by Ronnie Nieva on 27/03/2017.
 */

public enum PriceRange {
    CHEAP, BUDGET, EXPENSIVE;



    public static String toString(PriceRange priceRange){
        switch(priceRange){
            case CHEAP:
                return "Cheap";
            case BUDGET:
                return "Budget";
            case EXPENSIVE:
                return "Expensive";
        }
        return "Cheap";
    }

    public static PriceRange fromString(String priceRange){
        switch(priceRange){
            case "Cheap":
                return CHEAP;
            case "Budget":
                return BUDGET;
            case "Expensive":
                return EXPENSIVE;
        }
        return CHEAP;
    }
}
