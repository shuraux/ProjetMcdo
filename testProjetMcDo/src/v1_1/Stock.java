/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Stock {
    private ArrayList<Burger> listeBurgers;
    private ArrayList<Kebab> listeKebabs;
    private final int stockMax=35;
    
    public Stock(){
        this.listeBurgers = new ArrayList<>();
        this.listeKebabs = new ArrayList<>();
    }
    
    public synchronized void ajouterBurger(Burger bg){
        while (bg.isBurger()==true && this.listeBurgers.size()+this.listeKebabs.size()==stockMax) {
            try {
                System.out.println("Stock plein");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeBurgers.add(bg);        //on ajoute le dwich à la liste de burgers du stock
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized void retirerBurger(){
        while (this.listeBurgers.size() <= 0) {
            try {
                System.out.println("Pas de burger dispo dans le stock");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeBurgers.remove(0);
        System.out.println("Burger retiré du stock");
    }
    
    public ArrayList<Burger> getListeBurgers() {
        return listeBurgers;
    }
    
    public synchronized void ajouterKebab(Kebab kb){
        while (kb.isKebab()==true && this.listeBurgers.size()+this.listeKebabs.size()==stockMax) {
            try {
                System.out.println("Stock plein");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeKebabs.add(kb);        //on ajoute le dwich à la liste de kebabs du stock
        this.notifyAll();
        System.out.flush();
    }
    
    public synchronized void retirerKebab(){
        while (this.listeKebabs.size() <= 0) {
            try {
                System.out.println("Pas de kebab dispo dans le stock");
                this.wait();
                System.out.flush();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeKebabs.remove(0);
        System.out.println("Kebab retiré du stock");
    }
    
    public ArrayList<Kebab> getListeKebabs() {
        return listeKebabs;
    }
}
