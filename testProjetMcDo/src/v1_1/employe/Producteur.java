/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

import v1.Sandwich;
import v1_1.Stock;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Producteur extends Employe implements Runnable{
    private Stock stock;
    private long tempsProdMin, tempsProdMax;
    
    public Producteur(Stock stock, int numero, long tempsProdMin, long tempsProdMax){
        this.numero=numero;
        this.tempsProdMin=tempsProdMin;
        this.tempsProdMax=tempsProdMax;
        this.stock=stock;
    }
    
    public synchronized Kebab prodKebab(){
        Kebab kb = new Kebab();
        return kb;
    }
    
    public synchronized long dureeProdKebab(long tempsProdMin, long tempsProdMax){
        long delta = tempsProdMax - tempsProdMin;
        return tempsProdMin + (long) (Math.random() * (delta +1));
    }
    
    public synchronized Burger prodBurger(){
        Burger bg = new Burger();
        return bg;
    }
    
    public synchronized long dureeProdBurger(long tempsProdMin, long tempsProdMax){
        long delta = tempsProdMax - tempsProdMin;
        return tempsProdMin + (long) (Math.random() * (delta +1));
    }
    
    public void run(){
        for(int i=0; i<10; i++){
            try {
                    Thread.sleep(dureeProdKebab(tempsProdMin, tempsProdMax));
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            System.out.println("Kebab créé par le producteur n°" + numero);
            stock.ajouterKebab(prodKebab());
            System.out.println("Il y a " + stock.getListeKebabs().size() + " kebabs dans le stock");
        }
        
        for(int i=0; i<10; i++){
            try {
                    Thread.sleep(dureeProdBurger(tempsProdMin, tempsProdMax));
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            System.out.println("Burger créé par le producteur n°" + numero);
            stock.ajouterBurger(prodBurger());
            System.out.println("Il y a " + stock.getListeBurgers().size() + " burgers dans le stock");
        }
    }
}
