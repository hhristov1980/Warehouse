package main.warehouse;

import main.products.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Warehouse {
    private String name;
    private ConcurrentSkipListMap<Product.ProductType,ConcurrentSkipListMap<String, BlockingQueue<Product>>> storage;
    private final int MIN_QUANTITY = 5;
    private final int DELIVER_QUANTITY = 25;

    public Warehouse(String name){
        if(name.length()>0){
            this.name = name;
        }
        storage = new ConcurrentSkipListMap<>();
        for(int i = 0; i<15; i++){
            Product p = new Apple(Product.ProductType.FRUIT,"Apple");
            addToStorage(p);
            p = new Banana(Product.ProductType.FRUIT,"Banana");
            addToStorage(p);
            p = new Orange(Product.ProductType.FRUIT,"Orange");
            addToStorage(p);
            p = new Cucumber(Product.ProductType.VEGETABLE,"Cucumber");
            addToStorage(p);
            p = new Eggplant(Product.ProductType.VEGETABLE,"Eggplant");
            addToStorage(p);
            p = new Potato(Product.ProductType.VEGETABLE,"Potato");
            addToStorage(p);
            p = new Pork(Product.ProductType.MEAT,"Pork");
            addToStorage(p);
            p = new Chicken(Product.ProductType.MEAT,"Chicken");
            addToStorage(p);
            p = new Beef(Product.ProductType.MEAT,"Beef");
            addToStorage(p);
        }
    }


    public synchronized void takeProducts(){ //зареждане от дистрибутора
        ArrayList<String> deficitProducts = listDeficitProducts();
        if(deficitProducts.isEmpty()){
            try {
                System.out.println("No deficit goods!");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            supplyDeficitProducts(deficitProducts);
            notifyAll();
        }
    }

    public synchronized ArrayList<Product> deliverProducts(String name, int numberOfProductsToDeliver){
        ArrayList<Product> productsToDeliver = new ArrayList<>();
        for(Map.Entry<Product.ProductType,ConcurrentSkipListMap<String, BlockingQueue<Product>>>type: storage.entrySet()){
            for(Map.Entry<String,BlockingQueue<Product>> kind:type.getValue().entrySet()){
                if(name.equals(kind.getKey())){
                    if(kind.getValue().size()<=MIN_QUANTITY){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int i = 0; i<numberOfProductsToDeliver; i++){
                        try {
                            productsToDeliver.add(kind.getValue().take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Delivered "+productsToDeliver.size()+" of "+name);
                    notifyAll();
                }
            }
        }
        return productsToDeliver;
    }

    protected synchronized ArrayList<String> listDeficitProducts(){
        ArrayList<String> listOfDeficitProducts = new ArrayList<>();
        for(Map.Entry<Product.ProductType,ConcurrentSkipListMap<String, BlockingQueue<Product>>> type: storage.entrySet()){
            for(Map.Entry<String, BlockingQueue<Product>> kind: type.getValue().entrySet()){
                if(kind.getValue().size()<=5){
                    listOfDeficitProducts.add(kind.getKey());
                }
            }
        }
        return listOfDeficitProducts;
    }

    protected synchronized void addToStorage(Product p){
        if(!storage.containsKey(p.getType())){
            storage.put(p.getType(),new ConcurrentSkipListMap<>());
        }
        if(!storage.get(p.getType()).containsKey(p.getName())){
            storage.get(p.getType()).put(p.getName(),new LinkedBlockingQueue<>());
        }
        try {
            storage.get(p.getType()).get(p.getName()).put(p);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void supplyDeficitProducts(ArrayList<String> deficitProducts){
        for(String s: deficitProducts){
            switch (s){
                case "Apple":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Apple(Product.ProductType.FRUIT,"Apple"));
                    }
                    break;
                case "Banana":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Banana(Product.ProductType.FRUIT,"Banana"));
                    }
                    break;
                case "Orange":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Orange(Product.ProductType.FRUIT,"Orange"));
                    }
                    break;
                case "Potato":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Potato(Product.ProductType.VEGETABLE,"Potato"));
                    }
                    break;
                case "Eggplant":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Eggplant(Product.ProductType.VEGETABLE,"Eggplant"));
                    }
                    break;
                case "Cucumber":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Cucumber(Product.ProductType.VEGETABLE,"Cucumber"));
                    }
                    break;
                case "Pork":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Pork(Product.ProductType.MEAT,"Pork"));
                    }
                    break;
                case "Chicken":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Chicken(Product.ProductType.MEAT,"Chicken"));
                    }
                    break;
                case "Beef":
                    for(int i = 0; i<DELIVER_QUANTITY; i++){
                        addToStorage(new Beef(Product.ProductType.MEAT,"Beef"));
                    }
                    break;
            }
        }
    }

}
