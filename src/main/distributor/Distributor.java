package main.distributor;

import main.warehouse.Warehouse;

public class Distributor extends Thread{
    private String name;
    private Warehouse warehouse;

    public Distributor(String name, Warehouse warehouse){
        if(name.length()>0){
            this.name = name;
        }
    }

    @Override
    public void run() {
        while (true){
            warehouse.takeProducts();
        }
    }
}
