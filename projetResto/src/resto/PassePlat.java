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
    private final FileAttenteClients file;
    private boolean occupe;
    
    public PassePlat(int numero, FileAttenteClients file, SimulationClock clock){
        this.numero=numero; //on donne un id au pp
        this.sandwichsPp=new ArrayList<>();
        this.clock=clock;
        this.file=file;
        this.occupe=false;
    }
    
    public synchronized void ajouterSandwichPp(Sandwich sw){
        this.sandwichsPp.add(sw);
        System.out.println(this.clock.getSimulationTimeEnUT() + " : " + sw.getNom() + " ajouté au passe-plat n°" +
                this.numero);
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized ArrayList<Sandwich> convertirCommande(Integer client){
        ArrayList<Sandwich> commande = new ArrayList();
        int[] res=this.file.commandeAlea();     //on génère une commande aléatoire (des 0 et des 1)
        for(int i=0; i<res.length; i++){
            if(res[i]==0){
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Le client n°" + client + " veut un kebab");
                commande.add(new Kebab(this.clock.getSimulationTimeEnUT(), clock));      //on convertit un 0 en kebab
            }
            else {
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Le client n°" + client + " veut un burger");
            commande.add(new Burger(this.clock.getSimulationTimeEnUT(), clock));     //et un 1 en burger
            }
        }
        return commande;
    }
    public synchronized Integer retirerClient(){
        Integer client = this.file.retirePremierClient();
        return client;
    }
    
    public int getNumero() {
        return numero;
    }

    public boolean isOccupe() {
        return occupe;
    }

    /**
     * @param occupe the occupe to set
     */
    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }
    
}