/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

import v1.Sandwich;
import v1_1.Stock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Serveur extends Employe implements Runnable{
    private Stock stock;
    private String choix;
    private int dureeCommande=7;
    
    public Serveur(Stock stock, int numero){
        this.stock=stock;
        this.numero=numero;
    }

    public void choixSandwich(String choix){
        if(null != choix)switch (choix) {
            case "Kebab":
                this.choix=choix;
                break;
            case "Burger":
                this.choix=choix;
                break;
            default:
                System.out.println("Choix inconnu");
                break;
        }
    }
    
    public synchronized void service(Sandwich sw){
        
    }
    
    public void run(){
        for(int i=0; i<5; i++){
            try {
                Thread.sleep(dureeCommande);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            this.stock.retirerKebab();
            System.out.println("Kebab retiré du stock. Il reste " + stock.getListeKebabs().size() + " kebabs dans le stock");
        }
        for(int i=0; i<5; i++){
            try {
                Thread.sleep(dureeCommande);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            this.stock.retirerBurger();
            System.out.println("Burger retiré du stock. Il reste " + stock.getListeBurgers().size() + " burgers dans le stock");
        }
    }

    /**
     * @return the dureeCommande
     */
    public int getDureeCommande() {
        return dureeCommande;
    }
}
