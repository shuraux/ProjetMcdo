/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import java.util.ArrayList;
import v1_1.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Client {
    private final int numero;
    private ArrayList<Sandwich> commande = new ArrayList<>();
    
    public Client(int numero, ArrayList<Sandwich> commande){
        this.numero=numero;
        this.commande=commande;
    }

    public int getNumero() {
        return numero;
    }

    /**
     * @return the commande
     */
    public ArrayList<Sandwich> getCommande() {
        return commande;
    }
    
}
