/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import fr.insa.beuvron.cours.multiTache.projets.resto.FileAttenteClients;
import java.util.ArrayList;
import resto.sandwich.Sandwich;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class PassePlat {
    private final int numero;   //numéro qui permet d'identifer le passe-plat
    private final ArrayList<Sandwich> sandwichsPp;
    private final SimulationClock clock;
    private boolean occupe;
    
    public PassePlat(int numero, SimulationClock clock){
        this.numero=numero; //on donne un id au pp
        this.sandwichsPp=new ArrayList<>();
        this.clock=clock;
        this.occupe=false;
    }
    
    public synchronized void ajouterSandwichPp(Sandwich sw){
        this.sandwichsPp.add(sw);
        System.out.println(this.clock.getSimulationTimeEnUT() + " : " + sw.getNom() + " ajouté au passe-plat n°" +
                this.numero);
        this.notifyAll();
        System.out.flush();
    } 
    
    public int getNumero() {
        return numero;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }
    
}