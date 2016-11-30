/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import resto.employe.Producteur;
import resto.employe.Serveur;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Restaurant {
    private static final int nbrCaisses=3, nbrEmployes=5;
    private static PassePlat tabPp[] = new PassePlat[nbrCaisses];
    
     public static void main(String[] args) {
        
        Client c0 = new Client(0);    //on crée les clients
        Client c1 = new Client(1);
        Client c2 = new Client(2);
                
        File file = new File(3);
        file.ajouterClient(c0);
        file.ajouterClient(c1);
        file.ajouterClient(c2);
        
        for (int i=0; i<nbrCaisses; i++){
            tabPp[i] = new PassePlat(i, file);
        }
        Stock stock = new Stock();
        
        Thread p1 = new Thread(new Producteur(stock, 1));
        Thread p2 = new Thread(new Producteur(stock, 2));
        Thread s1 = new Thread(new Serveur(stock, 1, tabPp[0]));
        //Thread s2 = new Thread(new Serveur(stock, 2, passePlat1));
        p1.start();
        p2.start();
        s1.start();
        //s2.start();
    }
}
