/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1_1.sandwich.Sandwich;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Stock {
    private ArrayList<Sandwich> listeSandwichs;
    private final int stockMax=35;
    
    public Stock(){
        this.listeSandwichs = new ArrayList<>();
    }
    
    public synchronized void ajouterSandwich(Sandwich sw){
        while (this.listeSandwichs.size()==stockMax){
            try {
                System.out.println("Stock plein");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
    }
    
    public synchronized void retirerSandwich(){
        while (this.listeSandwichs.size() <= 0) {
            try {
                System.out.println("Pas de burger dispo dans le stock");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeSandwichs.remove(0);
        System.out.println("Burger retiré du stock");
    }

}
