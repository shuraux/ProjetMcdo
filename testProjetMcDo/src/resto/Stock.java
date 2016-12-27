/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import resto.sandwich.Burger;
import resto.sandwich.Sandwich;
import resto.sandwich.Kebab;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Stock {
    private ArrayList<Sandwich> listeSandwichs;
    private final int stockMax=100;
    private boolean posAjout, posRetir;
    private final int dureeChargement=500, dureeDechargement=300;
    private SimulationClock clock;
    
    public Stock(SimulationClock clock){
        this.listeSandwichs = new ArrayList<>();
        this.posAjout = true;
        this.posRetir = true;
        this.clock = clock;
    }
    
    public synchronized void ajouterSandwich(Sandwich sw){
        //System.out.println("check entrée ajouterSandwich");
        while (this.getListeSandwichs().size()==getStockMax() || this.posAjout==false){
            if(this.getListeSandwichs().size()==getStockMax()){ //si le stock est plein
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : Stock plein");
                    this.wait();                                //on attend
                    System.out.flush();
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            else if(this.posAjout==false){      //si qq1 est déjà en train de charger un sw dans le stock
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : ajout déjà en cours dans le stock par un autre producteur");
                    this.wait();        //on attend
                    System.out.flush();
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        }
        this.posAjout=false;    //on va charger un sw donc on block la possibilité aux autre de charger
        try {
                this.clock.metEnAttente(dureeChargement);      //sleep qui représente la durée pour charger
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        this.listeSandwichs.add(sw);        //on add le sw au stock
        this.posAjout=true;                 //on réouvre la possiblité de charger
        //System.out.println("check sortie ajouterSandwich");
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized Sandwich retirerSandwich(Sandwich sw){
        Sandwich sandwichRetire=null;
        while (this.listeSandwichs.size() <= 0 || this.posRetir==false) {   //tant que le stock est vide
            if(this.listeSandwichs.size() <= 0){
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : Impossible de retirer : Stock vide");
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            else if(this.posRetir==false){
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : Impossible de retirer : un autre serveur retire actuellement");
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        }
        if(sw instanceof Burger){
            boolean retire=false;
            while(retire==false){
                try{
                    for (int i=0; i<this.listeSandwichs.size(); i++){
                        if(this.listeSandwichs.get(i) instanceof Burger && retire==false){   //si l'element checké est un Burger et qu'on n'en a pas déjà retiré un
                            sandwichRetire=this.listeSandwichs.get(i);      //on récup le sandwich à mettre dans le passe plat
                            this.posRetir=false;
                            try {
                                this.clock.metEnAttente(dureeDechargement);      //sleep qui représente la durée pour décharger
                            } catch (InterruptedException ex) {
                                throw new Error("pas d'interrupt dans cet exemple");
                            }
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            this.posRetir=true;
                            System.out.println(this.clock.getSimulationTimeEnUT() + " : Burger déchargé");
                            retire=true;                                    //on sort du while
                        }
                    }
                    if(retire==false){
                        this.wait();
                    }
                    System.out.flush();
                } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
                }
                
            }
            return sandwichRetire;      //on renvoie le sandwich retiré qui sera mis sur le passeplat
        }
        
        if(sw instanceof Kebab){
            boolean retire=false;
            while(retire==false){
                try{
                    for (int i=0; i<this.listeSandwichs.size(); i++){
                        if(this.listeSandwichs.get(i) instanceof Kebab && retire==false){   //si l'element checké est un Burger et qu'on n'en a pas déjà retiré un
                            sandwichRetire=this.listeSandwichs.get(i);      //on récup le sandwich à mettre dans le passe plat
                            this.posRetir=false;
                            try {
                                this.clock.metEnAttente(dureeDechargement);      //sleep qui représente la durée pour décharger
                            } catch (InterruptedException ex) {
                                throw new Error("pas d'interrupt dans cet exemple");
                            }
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            this.posRetir=true;
                            System.out.println(this.clock.getSimulationTimeEnUT() + " : Kebab déchargé");
                            retire=true;                                    //on sort du while
                        }
                    }
                    if(retire==false){
                        this.wait();
                    }
                    System.out.flush();
                } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
                }
                
            }
            return sandwichRetire;      //on renvoie le sandwich retiré qui sera mis sur le passeplat
        }
        return sandwichRetire;
    }
    
    public int getNbrKebabs(){
        int nb=0;
        for (int i=0; i<this.listeSandwichs.size(); i++){
            if(this.listeSandwichs.get(i) instanceof Kebab){
                nb++;
            }
        }
        return nb;
    }
    
    public int getNbrBurgers(){
        int nb=0;
        for (int i=0; i<this.listeSandwichs.size(); i++){
            if(this.listeSandwichs.get(i) instanceof Burger){
                nb++;
            }
        }
        return nb;
    }

    /**
     * @return the listeSandwichs
     */
    public ArrayList<Sandwich> getListeSandwichs() {
        return listeSandwichs;
    }

    /**
     * @return the stockMax
     */
    public int getStockMax() {
        return stockMax;
    }

}
