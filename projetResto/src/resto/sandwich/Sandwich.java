/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.sandwich;

import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Sandwich {
    protected int prodSimultMax;
    protected int coutFabrication, prixVente;
    long tempsPeremption, momentProd;
    protected String nom;
    protected SimulationClock clock;
    
    public Sandwich(int prodSimultMax, int coutFabrication, int prixVente, long tempsPeremption, long momentProd,
            SimulationClock clock){
        this.coutFabrication=coutFabrication;
        this.prixVente=prixVente;
        this.tempsPeremption=tempsPeremption;
        this.momentProd=momentProd;
        this.prodSimultMax=prodSimultMax;
        this.nom="sandwich";
        this.clock=clock;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }
    
    public boolean Perime(){
        if(this.clock.getSimulationTimeEnUT()-momentProd>tempsPeremption){
            return true;    //le sw est périmé
        }
        else return false;  //sw pas encore périmé
    }
}
