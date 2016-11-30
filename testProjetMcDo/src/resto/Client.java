/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import resto.sandwich.Burger;
import resto.sandwich.Kebab;
import resto.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Client {
    private final int numero;
    private ArrayList<Sandwich> commande = new ArrayList<>();
    
    public Client(int numero){
        this.numero=numero;
        this.commande=creerCommande();
    }

    public int getNumero() {
        return numero;
    }
    
    public ArrayList<Sandwich> getCommande() {
        return commande;
    }
    
    public ArrayList<Sandwich> creerCommande(){     //va créer une commande de taille comprise entre 1 et 5
        int tailleMax=5;                            //avec des sandwichs aléatoires
        int tailleMin=1;
        int taille = (int)(Math.random() * tailleMax) + tailleMin;
        System.out.println(taille);
        
        ArrayList<Sandwich> commandeRandom = new ArrayList<>();
        for (int i=0; i<taille; i++){
            commandeRandom.add(randomSandwich());
            System.out.println(commandeRandom.get(i).getNom());
        }
        return commandeRandom;
    }
        
    public Sandwich randomSandwich(){       //va sélectionner au hasard un kebab ou un burger
        double randomChoix = Math.random();
        if(randomChoix<0.5){
            Sandwich sandwich = new Burger();
            return sandwich;
        }
        else {
            Sandwich sandwich = new Kebab();
            return sandwich;
        }
    }
}
