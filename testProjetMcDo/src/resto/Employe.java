/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import resto.Stock;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import fr.insa.beuvron.cours.multiTache.projets.resto.FileAttenteClients;
import resto.PassePlat;
import java.util.ArrayList;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Employe implements Runnable{
    private final int numero;
    private final Stock stock;
    private final ArrayList<PassePlat> listePp;
    private final SimulationClock clock;
    private final FileAttenteClients file;
    private final long tempsServiceEnUT;
    private ArrayList<Sandwich> commande = new ArrayList();
    private int role;
   
    public Employe(int numero, int role, Stock stock, ArrayList<PassePlat> listePp,
            FileAttenteClients file, long tempsServiceEnUT, SimulationClock clock){
        this.numero=numero;
        this.role=role;
        this.stock=stock;
        this.clock=clock;
        this.listePp=listePp;
        this.file=file;
        this.tempsServiceEnUT = tempsServiceEnUT;
    }
   
    @Override
    public void run(){
       while (role==1) {
            service();
        }
       while(role==2){
            production(2, prodKebab());
            production(3, prodBurger());
        }
       while(role==0){
       }
    }
    
    //méthodes liées à la production
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
    
    //méthodes liées au service
    public synchronized ArrayList<Sandwich> convertirCommande(Integer client){
        ArrayList<Sandwich> commande = new ArrayList();
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
        return commande;
    }
    public synchronized void remplirCommande(){
        Sandwich swCommande;
        int i=0;
        while(i<commande.size()){   //on est dans le while tant qu'on a pas fini la commande
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
                    this.listePp.get(0).ajouterSandwichPp(swCommande);  //on ajoute le sandwich sur le passe plat
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
                    this.listePp.get(0).ajouterSandwichPp(swCommande);   //on ajoute le sandwich sur le passe plat
                    System.out.println(this.clock.getSimulationTimeEnUT() +
                            " : Burger retiré du stock. Il reste " + stock.getNbrBurgers()+
                            " burgers dans le stock");
                    i++;
                }
            }
        }
    }
    public synchronized void service(){
        try {
                this.clock.metEnAttente(this.tempsServiceEnUT);
            } catch (InterruptedException ex) {
            }
            Integer client = this.file.retirePremierClient();
            if (client == null) {
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero
                        + " n'a plus de client");
            }
            else {
            commande=convertirCommande(client); //on convertit la commande tab en ArrayList
            remplirCommande();                  //on parcourt la liste de commande et on add les sw au pp
                        
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero +
                    " sert le client n°" + client + "\n");
            commande.clear();
            }
    }

    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(int role) {
        this.role = role;
    }
}
