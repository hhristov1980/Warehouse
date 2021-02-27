package main.products;


import java.util.PrimitiveIterator;

public abstract class Product {
    private ProductType type;
    private String name;

    public Product(ProductType type, String name){
        this.type = type;
        if(name.length()>0){
            this.name = name;
        }
    }



    public enum ProductType{

        FRUIT, VEGETABLE, MEAT;

    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }
}
