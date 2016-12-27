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

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class PassePlat {
    private final int numero;   //numéro qui permet d'identifer le passe-plat
    private final ArrayList<Sandwich> sandwichsPp;
    private SimulationClock clock;
    private FileAttenteClients file;
    
    public PassePlat(int numero, FileAttenteClients file, SimulationClock clock){
        this.numero=numero; //on donne un id au pp
        this.sandwichsPp = new ArrayList<>();
        this.clock = clock;
        this.file = file;
    }
    
    public synchronized void ajouterSandwichPp(Sandwich sw){
        this.sandwichsPp.add(sw);
        System.out.println("Sandwich ajouté au passe-plat");
        this.notifyAll();
        System.out.flush();
    }
    
    public int getNumero() {
        return numero;
    }
    
}