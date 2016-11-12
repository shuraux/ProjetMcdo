/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import v1_1.employe.Producteur;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Restaurant {
    private int nbPassePlats;
    
     public static void main(String[] args) {
        /*PassePlat passePlat1 = new PassePlat(1);
        Client c0 = new Client(0, 40, 100);
        Client c1 = new Client(1, 70, 120);
        File file = new File(3, passePlat1);
        file.ajouterClient(c0);
        file.ajouterClient(c1);*/
        
        Stock stock = new Stock();
        
        Thread p1 = new Thread(new Producteur(stock, 1, 50, 100));
        Thread p2 = new Thread(new Producteur(stock, 2, 45, 60));
       /* Thread f = new Thread(file);
        f.start();*/
        p1.start();
        p2.start();
    }
}
