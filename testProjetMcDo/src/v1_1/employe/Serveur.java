/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

import v1_1.PassePlat;
import v1_1.Stock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Serveur extends Employe implements Runnable{
    private Stock stock;
    private PassePlat passeplat;
    private String choix;
    private final int dureeDechargement=7;
    
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
    
    @Override
    public void run(){
        for(int i=0; i<5; i++){
            try {
                Thread.sleep(dureeDechargement);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            this.stock.retirerSandwich("kebab");
            System.out.println("Kebab retiré du stock. Il reste " + stock.getNbrKebabs() + " kebabs dans le stock");
        }
        for(int i=0; i<5; i++){
            try {
                Thread.sleep(dureeDechargement);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            this.stock.retirerSandwich("burger");
            System.out.println("Burger retiré du stock. Il reste " + stock.getNbrBurgers() + " burgers dans le stock");
        }
    }
    
}
