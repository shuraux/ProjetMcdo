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
public class Kebab extends Sandwich{
    private int[] tempsFabrication= new int[prodSimultMax];

    public Kebab(long momentProd, SimulationClock clock){
        super(2, 30, 60, 30, momentProd, clock);    //prodSimultMax, coutFabrication, prixVente, tempsPeremption
        this.tempsFabrication[0]=10;
        this.tempsFabrication[1]=15;
        this.nom="Kebab";
    }
    
    public boolean isKebab(){
        return true;
    }

    /**
     * @return the tempsFabrication
     */
    public int[] getTempsFabrication() {
        return tempsFabrication;
    }
    
}