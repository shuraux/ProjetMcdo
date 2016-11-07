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
public class Producteur implements Runnable{
   private int numero;
   PassePlat passePlat;
    
    public Producteur(PassePlat passePlat, int numero){
        this.numero=numero;
        this.passePlat=passePlat;
    }
    
    public void run(){
        for(int i = 0; i < 10; i++){
            System.out.println("Sandwich créé par le producteur " + numero);
            passePlat.ajouterSandwich();
            System.out.println(passePlat.getListeSandwichs().size());
        }
    }
}
