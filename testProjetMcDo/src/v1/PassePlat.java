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
    private final int numero;
    
    public PassePlat(int numero){
        this.nbSandwichs=0;
        this.numero=numero;
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
    
    public synchronized void retirerSandwich(){
        if(this.nbSandwichs>0){
            this.nbSandwichs=this.nbSandwichs-1;
        }
        else{
            System.out.println("Plus de sandwichs dans le passe plat n: " + this.numero);
        }
    }
    
}