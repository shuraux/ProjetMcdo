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

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Stock {
    private ArrayList<Sandwich> listeSandwichs;
    private final int stockMax=100;
    
    public Stock(){
        this.listeSandwichs = new ArrayList<>();
    }
    
    public synchronized void ajouterSandwich(Sandwich sw){
        //System.out.println("check entrée ajouterSandwich");
        while (this.getListeSandwichs().size()==getStockMax()){
            try {
                System.out.println("Stock plein");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeSandwichs.add(sw);
        //System.out.println("check sortie ajouterSandwich");
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized Sandwich retirerSandwich(String nom){
        Sandwich sandwichRetire=null;
        while (this.listeSandwichs.size() <= 0) {   //tant que le stock est vide
                try {
                    System.out.println("Impossible de retirer : Stock vide");
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        if("burger".equals(nom)){
            boolean retire=false;
            while(retire==false){
                try{
                    for (int i=0; i<this.listeSandwichs.size(); i++){
                        if(this.listeSandwichs.get(i) instanceof Burger && retire==false){   //si l'element checké est un Burger et qu'on n'en a pas déjà retiré un
                            sandwichRetire=this.listeSandwichs.get(i);      //on récup le sandwich à mettre dans le passe plat
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            System.out.println("Burger retiré du stock");
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
        
        if("kebab".equals(nom)){
            boolean retire=false;
            while(retire==false){
                try{
                    for (int i=0; i<this.listeSandwichs.size(); i++){
                        if(this.listeSandwichs.get(i) instanceof Kebab && retire==false){   //si l'element checké est un Burger et qu'on n'en a pas déjà retiré un
                            sandwichRetire=this.listeSandwichs.get(i);      //on récup le sandwich à mettre dans le passe plat
                            this.listeSandwichs.remove(i);                  //on l'enlève
                            System.out.println("Kebab retiré du stock");
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
