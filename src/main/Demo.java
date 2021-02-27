package main;

import main.client.Client;
import main.distributor.Distributor;
import main.shop.Shop;
import main.warehouse.Warehouse;

import java.util.Dictionary;

public class Demo {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("Talents Warehouse");
        Distributor distributor = new Distributor("IT Store",warehouse);
        Shop shop1 = new Shop("Shop 1",warehouse);
        Shop shop2 = new Shop("Shop 2",warehouse);
        Shop shop3 = new Shop("Shop 3",warehouse);
        Thread t1 = new Thread(shop1);
        Thread t2 = new Thread(shop2);
        Thread t3 = new Thread(shop3);
        Client client1 = new Client("Client 1", shop1);
        Client client2 = new Client("Client 2", shop1);
        Client client3 = new Client("Client 3", shop1);
        Client client4 = new Client("Client 4", shop2);
        Client client5 = new Client("Client 5", shop2);
        Client client6 = new Client("Client 6", shop2);
        Client client7 = new Client("Client 7", shop3);
        Client client8 = new Client("Client 8", shop3);
        Client client9 = new Client("Client 9", shop3);
        distributor.start();
        t1.start();
        t2.start();
        t3.start();
        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();
        client6.start();
        client7.start();
        client8.start();
        client9.start();


    }
}
