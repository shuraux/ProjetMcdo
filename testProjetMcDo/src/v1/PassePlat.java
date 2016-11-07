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
public class PassePlat {
    private int nbSandwichs;    //le nb de sandwichs dispo dans le passe-plat init à 0
    
    public PassePlat(){
        this.nbSandwichs=0;
    }
    
    public static void main(String[] args) {
        PassePlat passePlat = new PassePlat();
        
        Thread p1 = new Thread(new Producteur(passePlat, 1));
        Thread p2 = new Thread(new Producteur(passePlat, 2));
        p1.start();
        p2.start();
    }

    /**
     * @return the nbSandwichs
     */
    public synchronized int getNbSandwichs() {
        return this.nbSandwichs;
    }
    
    public synchronized void ajouterSandwich(){
        this.nbSandwichs=this.nbSandwichs+1;
    }
    
}