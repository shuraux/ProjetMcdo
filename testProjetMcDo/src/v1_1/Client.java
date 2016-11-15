/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Client {
    private final int numero;
    private ArrayList<Sandwich> commande = new ArrayList<>();
    
    public Client(int numero){
        this.numero=numero;
    }

    public int getNumero() {
        return numero;
    }
    
    public void genererCommande(ArrayList<Sandwich> listeCommande){
        for(int i=0; i<listeCommande.size(); i++){
            this.commande.add(listeCommande.get(i));
        }
    }
    
}
