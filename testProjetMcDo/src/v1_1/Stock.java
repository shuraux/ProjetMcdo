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
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized void retirerSandwich(String choix){
        if(choix=="burger"){
            while (this.listeSandwichs.size() <= 0) {   //tant que le stock est vide
                try {
                    System.out.println("Stock vide");
                    this.wait();                        //le thread va attendre
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            int i=0;
            boolean retire=false;
            while(retire==false){
                if(this.listeSandwichs.get(i) instanceof Burger){   //si l'element checké est un Burger
                    this.listeSandwichs.remove(i);                  //on l'enlève
                    System.out.println("Burger retiré du stock");
                    retire=true;                                    //on sort du while
                }
                if(i==listeSandwichs.size()-1){ //si on a atteint le dernier elt de la liste on reboot i
                    i=0;
                }
                else i++;               //sinon on incrémente i
            }
        }
        
        if(choix=="kebab)"){
            while (this.getListeSandwichs().size() <= 0) {
                try {
                    System.out.println("Stock vide");
                    this.wait();
                    System.out.flush();
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            int i=0;
            boolean retire=false;
            while(retire==false){
                if(this.listeSandwichs.get(i) instanceof Kebab){
                    this.listeSandwichs.remove(i);
                    System.out.println("Kebab retiré du stock");
                    retire=true;
                }
                i++;
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
