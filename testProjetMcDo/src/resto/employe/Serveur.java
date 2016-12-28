/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.employe;

import fr.insa.beuvron.cours.multiTache.projets.resto.FileAttenteClients;
import resto.PassePlat;
import resto.Stock;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Serveur extends Employe implements Runnable{
    private Stock stock;
    private PassePlat Pp;
    private SimulationClock clock;
    private FileAttenteClients file;
    private long tempsServiceEnUT;
    private boolean trace;
    private ArrayList<Sandwich> commande = new ArrayList();
    
    public Serveur(Stock stock, int numero, PassePlat Pp, SimulationClock clock,
            FileAttenteClients file, long tempsServiceEnUT, boolean trace){
        super(numero);
        this.stock=stock;
        this.Pp=Pp;
        this.clock=clock;
        this.file=file;
        this.tempsServiceEnUT = tempsServiceEnUT;
        this.trace = trace;
    }
    
    @Override
    public synchronized void run(){
        Sandwich swCommande;
        boolean encore = true;
        while (encore) {
            try {
                this.clock.metEnAttente(this.tempsServiceEnUT);
            } catch (InterruptedException ex) {
                encore = false;
            }
            if (encore) {
                Integer client = this.file.retirePremierClient();
                if (this.trace) {
                    if (client == null) {
                        System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero
                                 + " n'a plus de client");
                    }
                    else {
                        int[] res=this.file.commandeAlea();     //on génère une commande aléatoire (des 0 et des 1)
                        
                        for(int i=0; i<res.length; i++){
                            if(res[i]==0){
                                System.out.println(this.clock.getSimulationTimeEnUT() + " : Le client n°" + client + " veut un kebab");
                                commande.add(new Kebab(this.clock.getSimulationTimeEnUT(), clock));      //on convertit un 0 en kebab
                            }
                            else {
                                System.out.println(this.clock.getSimulationTimeEnUT() + " : Le client n°" + client + " veut un burger");
                                commande.add(new Burger(this.clock.getSimulationTimeEnUT(), clock));     //et un 1 en burger
                            }
                        }
                        int i=0;
                        while(i<commande.size()){
                                //on est dans le while tant qu'on a pas fini la commande
                            swCommande=commande.get(i);
                            
                            if(swCommande instanceof Kebab){   //si sw pas périmé on ajoute le sw au pp et on augmente i  
                                Sandwich swStock=this.stock.retirerSandwich(swCommande);
                                if(swStock.Perime()==true){      //si le sw retiré est périmé
                                    this.stock.setPosRetir(false);
                                    System.out.println(this.clock.getSimulationTimeEnUT() + " : kebab périmé détecté");
                                    this.stock.jeterSandwichs();    //on appelle la méthode qui va check tous les sw et les jeter si necess
                                    this.stock.setPosRetir(true);
                                }
                                else {
                                    this.Pp.ajouterSandwichPp(swCommande);  //on ajoute le sandwich sur le passe plat
                                    System.out.println(this.clock.getSimulationTimeEnUT() +
                                        " : Kebab retiré du stock. Il reste " + stock.getNbrKebabs() +
                                        " kebabs dans le stock");
                                i++;
                                }
                            }
                            else if(swCommande instanceof Burger){
                                Sandwich swStock=this.stock.retirerSandwich(swCommande);
                                if(swStock.Perime()==true){      //si le sw retiré est périmé
                                    this.stock.setPosRetir(false);
                                    System.out.println(this.clock.getSimulationTimeEnUT() + " : burger périmé détecté");
                                    this.stock.jeterSandwichs();    //on appelle la méthode qui va check tous les sw et les jeter si necess
                                    this.stock.setPosRetir(true);
                                }
                                else {
                                    this.Pp.ajouterSandwichPp(swCommande);   //on ajoute le sandwich sur le passe plat
                                    System.out.println(this.clock.getSimulationTimeEnUT() +
                                            " : Burger retiré du stock. Il reste " + stock.getNbrBurgers()+
                                            " burgers dans le stock");
                                i++;
                                }
                            }
                        }
                        System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero +
                            " sert le client n°" + client + "\n");
                        commande.clear();
                    }
                }
            }
        }   
    }
}
    

