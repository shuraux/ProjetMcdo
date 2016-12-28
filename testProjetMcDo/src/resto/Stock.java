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
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Stock {
    //private List<Sandwich> listeSandwichs = Collections.synchronizedList(new ArrayList<Sandwich>());
    private final ArrayList<Sandwich> listeSandwichs;
    private final int stockMax=100;
    private boolean posAjout, posRetir;
    private final int dureeChargement=4, dureeDechargement=3;
    private final SimulationClock clock;
    
    public Stock(SimulationClock clock){
        this.listeSandwichs = new ArrayList<>();
        this.posAjout = true;
        this.posRetir = true;
        this.clock = clock;
    }

    public synchronized void ajouterSandwich(Sandwich sw){
        //System.out.println("check entrée ajouterSandwich");
        while (this.getListeSandwichs().size()==getStockMax() || this.isPosAjout()==false){
            if(this.getListeSandwichs().size()==getStockMax()){ //si le stock est plein
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : Stock plein");
                    this.wait();                                //on attend
                    System.out.flush();
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            else if(this.isPosAjout()==false){      //si qq1 est déjà en train de charger un sw dans le stock
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : ajout déjà en cours dans le stock par un autre producteur");
                    System.out.flush();
                    this.wait();        //on attend
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        }
        this.setPosAjout(false);    //on va charger un sw donc on block la possibilité aux autre de charger
        try {
                this.clock.metEnAttente(dureeChargement);      //sleep qui représente la durée pour charger
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        this.listeSandwichs.add(sw);        //on add le sw au stock
        System.out.println(this.clock.getSimulationTimeEnUT() + " : " + sw.getNom() + " ajouté au stock");
        this.setPosAjout(true);                 //on réouvre la possiblité de charger
        //System.out.println("check sortie ajouterSandwich");
        this.notifyAll();
        System.out.flush();
    } 
    public synchronized Sandwich retirerSandwich(Sandwich sw){
        Sandwich sandwichRetire=null;
        
        while (this.listeSandwichs.size() <= 0 || this.isPosRetir()==false) {   //tant que le stock est vide
            if(this.listeSandwichs.size() <= 0){
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : Impossible de retirer : Stock vide");
                    /*synchronized(this){
                        this.notify();  //on réveille un employé qui est serveur
                        System.out.println(this.clock.getSimulationTimeEnUT() +
                                " : notify de retirerSandwich exec");
                    }*/
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            else if(this.isPosRetir()==false){
                try {
                    System.out.println(this.clock.getSimulationTimeEnUT() +
                            " : Impossible de retirer : un autre serveur retire actuellement");
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
                            this.setPosRetir(false);
                            try {
                                this.clock.metEnAttente(dureeDechargement);      //sleep qui représente la durée pour décharger
                            } catch (InterruptedException ex) {
                                throw new Error("pas d'interrupt dans cet exemple");
                            }
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            this.setPosRetir(true);
                            //System.out.println(this.clock.getSimulationTimeEnUT() + " : Burger déchargé");
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
                            this.setPosRetir(false);
                            try {
                                this.clock.metEnAttente(dureeDechargement);      //sleep qui représente la durée pour décharger
                            } catch (InterruptedException ex) {
                                throw new Error("pas d'interrupt dans cet exemple");
                            }
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            this.setPosRetir(true);
                            //System.out.println(this.clock.getSimulationTimeEnUT() + " : Kebab déchargé");
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
        return sandwichRetire;      //return null : jamais censé se produire
    } 
    public synchronized void jeterSandwichs(){  //si un sw périmé est détecté par un serveur on va check tous les sw et jeter les périmés
        if(!this.listeSandwichs.isEmpty()){ //si le stock n'est pas vide
            for(int i=0; i<this.listeSandwichs.size(); i++){    //on va parcourir la liste
                if(this.listeSandwichs.get(i).Perime()==true){  //si le sw est périmé
                    try {
                        this.clock.metEnAttente(dureeDechargement);      //sleep qui représente la durée pour décharger
                    } catch (InterruptedException ex) {
                        throw new Error("pas d'interrupt dans cet exemple");
                    }
                    if(this.listeSandwichs.get(i) instanceof Kebab){
                        System.out.println(this.clock.getSimulationTimeEnUT() + 
                                " : " + this.listeSandwichs.get(i).getNom() +
                                " périmé. Il est jeté.  Il reste " + this.getNbrKebabs() +" kebabs dans le stock");
                    }
                    else if(this.listeSandwichs.get(i) instanceof Burger){
                        System.out.println(this.clock.getSimulationTimeEnUT() + 
                                " : " + this.listeSandwichs.get(i).getNom() +
                                " périmé. Il est jeté.  Il reste " + this.getNbrBurgers()+" burgers dans le stock");
                    }
                    this.listeSandwichs.remove(i);              //on l'enlève de la liste
                }
            }
        }
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
    public ArrayList<Sandwich> getListeSandwichs() {
        return listeSandwichs;
    }
    public int getStockMax() {
        return stockMax;
    }
    public boolean isPosAjout() {
        return posAjout;
    }
    public void setPosAjout(boolean posAjout) {
        this.posAjout = posAjout;
    }
    public boolean isPosRetir() {
        return posRetir;
    }
    public void setPosRetir(boolean posRetir) {
        this.posRetir = posRetir;
    }

}
