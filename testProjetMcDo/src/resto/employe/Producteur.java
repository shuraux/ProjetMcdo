/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.employe;

import resto.Stock;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Producteur extends Employe implements Runnable{
    private Stock stock;
    private SimulationClock clock;
    
    public Producteur(Stock stock, int numero, SimulationClock clock){
        this.numero=numero;
        this.stock=stock;
        this.clock=clock;
    }
    
    public synchronized Kebab prodKebab(){
        Kebab kb = new Kebab();
        return kb;
    }
    
    public synchronized Burger prodBurger(){
        Burger bg = new Burger();
        return bg;
    }
    
    @Override
    public void run(){
        while(true){
            for(int i=0; i<2; i++){
                Kebab kebab=prodKebab();
                try {
                    this.clock.metEnAttente(kebab.getTempsFabrication()[0]);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Kebab créé par le producteur n°" + numero);
                stock.ajouterSandwich(kebab);  //ajoute le kebab si le stock n'est pas plein, sinon attend
                System.out.println("Il y a " + stock.getNbrKebabs() + " kebabs dans le stock");
            }

            for(int i=0; i<3; i++){
                Burger burger=prodBurger();
                try {
                    this.clock.metEnAttente(burger.getTempsFabrication()[0]);
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Burger créé par le producteur n°" + numero);
                stock.ajouterSandwich(burger);  //ajoute le kebab si le stock n'est pas plein, sinon attend
                System.out.println("Il y a " + stock.getNbrBurgers() + " burgers dans le stock");
            }
        }
    }
}
