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
        super(numero);
        this.stock=stock;
        this.clock=clock;
    }
    
    public synchronized Kebab prodKebab(){
        Kebab kb = new Kebab(this.clock.getSimulationTimeEnUT());
        return kb;
    }
    
    public synchronized Burger prodBurger(){
        Burger bg = new Burger(this.clock.getSimulationTimeEnUT());
        return bg;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                this.clock.metEnAttente(prodKebab().getTempsFabrication()[1]);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : 2 Kebabs créé par le producteur n°" + this.numero);
            stock.ajouterSandwich(prodKebab());  //ajoute le kebab si le stock n'est pas plein, sinon attend
            stock.ajouterSandwich(prodKebab());
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Il y a " + stock.getNbrKebabs() + " kebabs dans le stock");

            try {
                this.clock.metEnAttente(prodBurger().getTempsFabrication()[2]);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : 3 Burgers créé par le producteur n°" + this.numero);
            stock.ajouterSandwich(prodBurger());  //ajoute le burger si le stock n'est pas plein, sinon attend
            stock.ajouterSandwich(prodBurger());
            stock.ajouterSandwich(prodBurger());
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Il y a " + stock.getNbrBurgers() + " burgers dans le stock");
        }
    }
}
