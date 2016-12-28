/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Employe implements Runnable{
    private final int numero;
    private final Stock stock;
    private final ArrayList<PassePlat> listePp;
    private final SimulationClock clock;
    private final long tempsServiceEnUT;
    private ArrayList<Sandwich> commande = new ArrayList();
    private final ArrayBlockingQueue<Integer> file;
    private int role;   //0 : ne fait rien, 1 : sert, 2 : produit
   
    public Employe(int numero, Stock stock, ArrayList<PassePlat> listePp, ArrayBlockingQueue file, long tempsServiceEnUT,
            SimulationClock clock, int role){
        this.numero=numero;
        this.role=role;
        this.stock=stock;
        this.clock=clock;
        this.listePp=listePp;
        this.tempsServiceEnUT = tempsServiceEnUT;
        this.file=file;
    }
   
    @Override
    public synchronized void run(){
        while(true){
            while (this.role==1) {
                System.out.println(this.clock.getSimulationTimeEnUT() + " :  L'employe n°" +
                        this.numero + " devient serveur");
                service();
                this.role=0;
                /*synchronized(this.stock){
                    try {
                        System.out.println("passage dans sync stock par n°" + this.numero);
                        this.stock.wait();
                        this.role=2;
                    } catch (InterruptedException ex){
                    }
                }*/
            }
            
            while(this.role==2){
                System.out.println(this.clock.getSimulationTimeEnUT() + " :  L'employe n°" +
                                this.numero + " devient producteur");
                production(2, prodKebab());
                production(3, prodBurger());
                //this.role=0;
            }
       
            while(this.role==0){
                synchronized(this.file){
                    try {
                        //System.out.println(this.clock.getSimulationTimeEnUT() + " : Passage dans sync queue par n°" + this.numero);
                        this.file.wait();
                        this.role=1;        //lorsqu'un nouveau client arrive dans la file un employe en attente devient serveur
                    } catch (InterruptedException ex){
                    }
                }
            }
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
    public synchronized void remplirCommande(int choixPp){
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
                    this.listePp.get(choixPp).ajouterSandwichPp(swCommande);  //on ajoute le sandwich sur le passe plat
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
                    this.listePp.get(choixPp).ajouterSandwichPp(swCommande);   //on ajoute le sandwich sur le passe plat
                    System.out.println(this.clock.getSimulationTimeEnUT() +
                            " : Burger retiré du stock. Il reste " + stock.getNbrBurgers()+
                            " burgers dans le stock");
                    i++;
                }
            }
        }
    }
    public synchronized void service(){
        int choixPp=choixPp();
        Integer client = this.listePp.get(choixPp).retirerClient();
        if (client == null) {
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero
                    + " n'a plus de client");
            synchronized(this.file){
                try {
                    //System.out.println(this.clock.getSimulationTimeEnUT() +" : Passage dans sync queue par n°" + this.numero);
                    this.file.wait();
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : sortie du wait par serveur n°" +
                            this.numero);
                    //this.role=1;        //lorsqu'un nouveau client arrive dans la file un employe en attente devient serveur
                } catch (InterruptedException ex){
                }
            }
        }
        try {
            this.clock.metEnAttente(this.tempsServiceEnUT);
        } catch (InterruptedException ex) {
        }
        client = this.listePp.get(choixPp).retirerClient();
        commande=this.listePp.get(choixPp).convertirCommande(client); //on convertit la commande tab en ArrayList
        remplirCommande(choixPp);                  //on parcourt la liste de commande et on add les sw au pp
                        
        System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero +
                " sert le client n°" + client + "\n");
        commande.clear();
        this.listePp.get(choixPp).setOccupe(false);
        //this.notify();   
    }
    
    public synchronized int choixPp(){
        int i=0;
        while(this.listePp.get(i).isOccupe()){
            //System.out.println("pp occupé");
            //System.out.flush();
                i++;
            }
        if(i<this.listePp.size()){
            this.listePp.get(i).setOccupe(true);
            System.out.println(this.clock.getSimulationTimeEnUT() + " : Le serveur n°" + this.numero + 
                    " se rend sur le passe-plat n°" + this.listePp.get(i).getNumero());
            return i;
        }
        else {
            try{
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Pas de passe-plat disponible pour le serveur n°" +
                        this.numero);
                this.wait();
            } catch(InterruptedException ex){
            }
        }
        System.out.println("pas normal");
        return 1000;
    }
    
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
}
