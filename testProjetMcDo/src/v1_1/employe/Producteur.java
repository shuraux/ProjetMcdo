/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

import v1_1.Stock;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Producteur extends Employe implements Runnable{
    private Stock stock;
    
    public Producteur(Stock stock, int numero){
        this.numero=numero;
        this.stock=stock;
    }
    
    public synchronized Kebab prodKebab(){
        Kebab kb = new Kebab();
        return kb;
    }
    
    public synchronized Burger prodBurger(){
        Burger bg = new Burger();
        return bg;
    }
    
    public void run(){
        for(int i=0; i<10; i++){
            Kebab kebab=prodKebab();
            try {
                Thread.sleep(kebab.getTempsFabrication()[0]);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            System.out.println("Kebab créé par le producteur n°" + numero);
            stock.ajouterKebab(kebab);  //ajoute le kebab si le stock n'est pas plein, sinon attend
            System.out.println("Il y a " + stock.getListeKebabs().size() + " kebabs dans le stock");
        }
        
        for(int i=0; i<10; i++){
            Burger burger=prodBurger();
            try {
                Thread.sleep(burger.getTempsFabrication()[0]);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            System.out.println("Burger créé par le producteur n°" + numero);
            stock.ajouterBurger(burger);
            System.out.println("Il y a " + stock.getListeBurgers().size() + " burgers dans le stock");
        }
    }
}
