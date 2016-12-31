/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import fr.insa.beuvron.cours.multiTache.projets.resto.FileAttenteClients;
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
    private final FileAttenteClients fileAttente;
    private final ArrayBlockingQueue<Integer> file;
    private int role;   //0 : ne fait rien, 1 : sert, 2 : produit
   
    public Employe(int numero, Stock stock, ArrayList<PassePlat> listePp, FileAttenteClients gestionFileAttente, ArrayBlockingQueue file, long tempsServiceEnUT,
            SimulationClock clock, int role){
        this.numero=numero;
        this.role=role;
        this.stock=stock;
        this.clock=clock;
        this.listePp=listePp;
        this.tempsServiceEnUT = tempsServiceEnUT;
        this.fileAttente=gestionFileAttente;
        this.file=file;
    }
   
    @Override
    public synchronized void run(){
        int gestionFile=1, gestionStock=5;
        while(true){
            while (this.role==1) {
                System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" +
                        (this.numero) + " devient serveur");
                service();
                if(this.file.size()>gestionFile && this.stock.getListeSandwichs().size()>gestionStock){  //si file + longue que 1 et stock + grd que 5
                    this.role=1;    //reste serveur
                }
                else if(this.file.size()<gestionFile && this.stock.getListeSandwichs().size()>gestionStock){  //si file inf à 1 et stock sup à 5
                    this.role=0;    //pause
                }
                else this.role=2;   //passe en prod
            }
            
            while(this.role==2){
                System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" +
                        (this.numero) + " devient producteur");
                int[] choix = new int[2];
                choix=choixQte();
                production(choix[0], prodKebab());
                production(choix[1], prodBurger());
                if(this.stock.getListeSandwichs().size()<gestionStock && this.file.size()<gestionFile){   //si stock inf à 5 et file inf à 2
                    this.role=2;    //reste prod
                }
                else if(this.stock.getListeSandwichs().size()>gestionStock && this.file.size()<gestionFile){  //si stock sup à 5 et file inf à 2
                    this.role=0;        //pause
                }
                else this.role=1;       //devient serveur
            }
       
            while(this.role==0){
                System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" +
                        (this.numero) + " ne fait rien");
                synchronized(this.file){
                    try {
                        this.file.wait();
                        if(this.file.size()>gestionFile){
                            this.role=1;        //devient serv
                        }
                        if(this.stock.getListeSandwichs().size()<5){
                            this.role=2;        // devient prod
                        }
                        //else this.role=0;
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
                    " Kebabs créé par l'employe n°" + this.numero);
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
                    " Burgers créé par l'employe n°" + this.numero);
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
    public synchronized int[] choixQte(){
        int[] choix = new int[2];
        choix[0]=0;
        choix[1]=0;
        if(this.stock.getNbrKebabs()<2){
            choix[0]=2;
        }
        else if(this.stock.getNbrKebabs()>=2){
            choix[0]=1;
        }
        if(this.stock.getNbrBurgers()<3){
            choix[1]=3;
        }
        else if(3<=this.stock.getNbrBurgers() && this.stock.getNbrBurgers()<6){
            choix[1]=2;
        }
        else if(6<=this.stock.getNbrBurgers()){
            choix[1]=1;
        }
        return choix;
    }
    
    //méthodes liées au service
    public synchronized void service(){
        int choixPp=choixPp();
        Integer client = this.retirerClient();
        if (client == null) {
            System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" + (this.numero)
                    + " n'a plus de client");
            synchronized(this.file){
                try {
                    this.file.wait();
                } catch (InterruptedException ex){
                }
            }
        }
        try {
            this.clock.metEnAttente(this.tempsServiceEnUT);
        } catch (InterruptedException ex) {
        }
        System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employé n°" + this.numero +
                " prend la commande du client n°" + client);
        commande=this.convertirCommande(client); //on convertit la commande tab en ArrayList
        remplirCommande(choixPp);                  //on parcourt la liste de commande et on add les sw au pp
                        
        System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" + (this.numero) +
                " sert le client n°" + client + "\n");
        commande.clear();
        this.listePp.get(choixPp).setOccupe(false);
        //this.notify();   
    }
    public synchronized ArrayList<Sandwich> convertirCommande(Integer client){  //va convertir la commande numéro par des sw
        ArrayList<Sandwich> commande = new ArrayList();
        int[] res=this.fileAttente.commandeAlea();     //on génère une commande aléatoire (des 0 et des 1)
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
    public synchronized void remplirCommande(int choixPp){  //va prendre les sw du stock pour les mettre sur le pp
        Sandwich swCommande;
        int i=0;
        while(i<commande.size()){   //on est dans le while tant qu'on a pas fini la commande
            swCommande=commande.get(i);
                            
            if(swCommande instanceof Kebab){   //si sw pas périmé on ajoute le sw au pp et on augmente i  
                Sandwich swStock=this.stock.retirerSandwich(swCommande);
                if(swStock.Perime()==true){      //si le sw retiré est périmé
                    this.stock.setPosRetrait(false);
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : kebab périmé détecté");
                    this.stock.jeterSandwichs();    //on appelle la méthode qui va check tous les sw et les jeter si necess
                    this.stock.setPosRetrait(true);
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
                    this.stock.setPosRetrait(false);
                    System.out.println(this.clock.getSimulationTimeEnUT() + " : burger périmé détecté");
                    this.stock.jeterSandwichs();    //on appelle la méthode qui va check tous les sw et les jeter si necess
                    this.stock.setPosRetrait(true);
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
    public synchronized Integer retirerClient(){
        Integer client = this.fileAttente.retirePremierClient();
        return client;
    }
    
    public synchronized int choixPp(){
        int choixPp=100;    
        for(int i=this.listePp.size()-1; i>=0; i--){        //on parcourt les pp du dernier au premier
            if(!this.listePp.get(i).isOccupe()){    //si le pp est vide
                choixPp=i;      // on le select, du coup on aura le plus petit possible à la fin du for
            }
        }
        if(choixPp!=100){
            this.listePp.get(choixPp).setOccupe(true);
            System.out.println(this.clock.getSimulationTimeEnUT() + " : L'employe n°" + (this.numero) + 
                    " se rend sur le passe-plat n°" + this.listePp.get(choixPp).getNumero());
            return choixPp;
        }
        else {
            try{
                System.out.println(this.clock.getSimulationTimeEnUT() + " : Pas de passe-plat disponible pour l'employe n°" +
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
