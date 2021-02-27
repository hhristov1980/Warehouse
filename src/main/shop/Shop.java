package main.shop;

import main.client.Client;
import main.products.Product;
import main.warehouse.Warehouse;

import java.util.ArrayList;

public class Shop extends Warehouse implements Runnable {

    private Warehouse warehouse;
    private final int MIN_QUANTITY_TO_DELIVER = 5;

    public Shop(String name, Warehouse warehouse) {
        super(name);
        this.warehouse = warehouse;

    }

    @Override
    public void run() {
        while (true) {
            takeProducts();
        }
    }

    @Override
    protected synchronized void addToStorage(Product p) {
        super.addToStorage(p);
    }

    @Override
    public synchronized void takeProducts() {
        ArrayList<String> listOfDeficitProducts;
        listOfDeficitProducts = listDeficitProducts();
        ArrayList<Product> deliveryFromWarehouse = new ArrayList<>();
        if(!listOfDeficitProducts.isEmpty()){
            for (String s : listOfDeficitProducts) {
                deliveryFromWarehouse.addAll(warehouse.deliverProducts(s,MIN_QUANTITY_TO_DELIVER));
            }
            for (Product p : deliveryFromWarehouse) {
                addToStorage(p);
            }
        }
    }

    @Override
    protected synchronized ArrayList<String> listDeficitProducts() {
        return super.listDeficitProducts();
    }

    public synchronized void sellProducts(String name){

    }

}
