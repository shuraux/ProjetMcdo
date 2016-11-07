/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Restaurant {
     public static void main(String[] args) {
        PassePlat passePlat = new PassePlat(1);
        
        Thread p1 = new Thread(new Producteur(passePlat, 1));
        Thread p2 = new Thread(new Producteur(passePlat, 2));
        p1.start();
        p2.start();
    }
}
