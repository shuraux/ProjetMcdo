/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Sandwich;
import v1_1.sandwich.Kebab;

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
                    System.out.println("Stock vide");
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        if("burger".equals(nom)){
            int i=0;
            boolean retire=false;
            while(retire==false){
                if(this.listeSandwichs.get(i) instanceof Burger){   //si l'element checké est un Burger
                    sandwichRetire=this.listeSandwichs.get(i);      //on récup le sandwich à mettre dans le passe plat
                    this.listeSandwichs.remove(i);                  //on l'enlève
                    System.out.println("Burger retiré du stock");
                    retire=true;                                    //on sort du while
                }
                if(i==listeSandwichs.size()-1){ //si on a atteint le dernier elt de la liste on reboot i
                    i=0;
                }
                else i++;               //sinon on incrémente i
            }
            return sandwichRetire;      //on renvoie le sandwich retiré qui sera mis sur le passeplat
        }
        
        if("kebab".equals(nom)){
            int i=0;
            boolean retire=false;
            while(retire==false){
                if(this.listeSandwichs.get(i) instanceof Kebab){
                    sandwichRetire=this.listeSandwichs.get(i);
                    this.listeSandwichs.remove(i);
                    //System.out.println("Kebab retiré du stock");
                    retire=true;
                }
                i++;
            }
            return sandwichRetire;
        }
        
        else return sandwichRetire;
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
