/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.employe;

import resto.Stock;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;
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
    
    @Override
    public void run(){
        while(true){
            production(2, prodKebab());
            production(3, prodBurger());
        }
    }
    
    public synchronized void production(int nombre, Sandwich sw){
        if(sw instanceof Kebab){
            try {
                this.clock.metEnAttente(prodKebab().getTempsFabrication()[nombre-1]);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : " + nombre +
                    " Kebabs créé par le producteur n°" + this.numero);
            for(int i=0; i<nombre; i++){
                stock.ajouterSandwich(prodKebab()); //ajoute le kebab si le stock n'est pas plein, sinon attend
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Il y a " +
                    stock.getNbrKebabs() + " kebabs dans le stock");
        }
        else if(sw instanceof Burger){
            try {
                this.clock.metEnAttente(prodBurger().getTempsFabrication()[nombre-1]);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : " + nombre +
                    " Burgers créé par le producteur n°" + this.numero);
            for(int i=0; i<nombre; i++){
                stock.ajouterSandwich(prodBurger()); //ajoute le burger si le stock n'est pas plein, sinon attend
            }
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Il y a " +
                    stock.getNbrBurgers() + " burgers dans le stock");
        }
    }
    
    public synchronized Kebab prodKebab(){
        Kebab kb = new Kebab(this.clock.getSimulationTimeEnUT(), clock);
        return kb;
    }
    
    public synchronized Burger prodBurger(){
        Burger bg = new Burger(this.clock.getSimulationTimeEnUT(), clock);
        return bg;
    }
}
