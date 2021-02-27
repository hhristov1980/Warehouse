package main.client;

import main.products.Product;
import main.shop.Shop;
import main.util.Constants;
import main.util.Randomizer;

import java.util.ArrayList;

public class Client extends Thread {
    private String name;
    private Shop shop;

    public Client(String name, Shop shop){
        if(name.length()>0){
            this.name = name;
        }
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true){
            String productName = Constants.PRODUCT_NAMES[Randomizer.getRandomInt(0,Constants.PRODUCT_NAMES.length-1)];
            int numberOfProducts = Randomizer.getRandomInt(1,4);
            ArrayList<Product> products = new ArrayList<>();
            products = shop.deliverProducts(productName,numberOfProducts);
            if(!products.isEmpty()){
                System.out.println("Client "+this.name+" bought "+numberOfProducts+" pieces of "+productName);
            }
            else {
                System.out.println("Client "+this.name+" is waiting to buy products");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
