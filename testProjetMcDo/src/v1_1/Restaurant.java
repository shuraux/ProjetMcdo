/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1_1.employe.Producteur;
import v1_1.employe.Serveur;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;
import v1_1.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Restaurant {
    private int nbrCaisses, nbrEmployes;
    
     public static void main(String[] args) {
        
        Client c0 = new Client(0);    //on crée les clients
        c0.getCommande().toString();
        Client c1 = new Client(1);
        c1.getCommande().toString();
        
        File file = new File(3);
        file.ajouterClient(c0);
        file.ajouterClient(c1);
        
        PassePlat passePlat1 = new PassePlat(1, file);

        Stock stock = new Stock();
        
        Thread p1 = new Thread(new Producteur(stock, 1));
        //Thread p2 = new Thread(new Producteur(stock, 2));
        Thread s1 = new Thread(new Serveur(stock, 1, passePlat1));
        //Thread s2 = new Thread(new Serveur(stock, 2, passePlat1));
        p1.start();
        //p2.start();
        s1.start();
        //s2.start();
    }
}
