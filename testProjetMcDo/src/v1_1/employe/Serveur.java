/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

import v1_1.PassePlat;
import v1_1.Stock;
import v1_1.sandwich.Burger;
import v1_1.sandwich.Kebab;
import v1_1.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Serveur extends Employe implements Runnable{
    private Stock stock;
    private PassePlat passePlat;
    private final int dureeDechargement=7;
    
    public Serveur(Stock stock, int numero, PassePlat passePlat){
        this.stock=stock;
        this.numero=numero;
        this.passePlat=passePlat;
    }
    
    @Override
    public void run(){
        Sandwich swCommande;
        while(true){
            for(int i=0; i<this.passePlat.getFile().getListeClients().get(0).getCommande().size(); i++){
                                //on est dans le for tant qu'on a pas fini la commande
                try {
                    Thread.sleep(dureeDechargement);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
                    
                swCommande=this.stock.retirerSandwich(this.passePlat.getFile().getListeClients().get(0).getCommande().get(i).getNom());
                if(swCommande instanceof Kebab){
                    System.out.println("Kebab retiré du stock. Il reste " + stock.getNbrKebabs() + " kebabs dans le stock");
                }
                else if(swCommande instanceof Burger){
                    System.out.println("Burger retiré du stock. Il reste " + stock.getNbrBurgers()+ " burgers dans le stock");
                }
                this.passePlat.ajouterSandwichPp(swCommande);   //on ajoute le sandwich sur le passe plat
            }
            this.passePlat.getFile().retirerClient();   //on retire le client via la méthode dans File
        }
     }
        /*for(int i=0; i<5; i++){
            try {
                Thread.sleep(dureeDechargement);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            swCommande=this.stock.retirerSandwich("burger");
            System.out.println("Burger retiré du stock. Il reste " + stock.getNbrBurgers() + " burgers dans le stock");
            
            boolean check=passePlat.checkCommande();
            if(check==true){
                passePlat.ajouterSandwichPp(swCommande);
            }
        }*/
}
    

