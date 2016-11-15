/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

import v1_1.sandwich.Burger;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class PassePlat {
    private final int numero;   //numéro qui permet d'identifer le passe-plat
    
    public PassePlat(int numero){
        this.numero=numero; //on donne un id au pp
    }
    
    public synchronized void ajouterBurger(Burger bg){
        
    }
    
    public int getNumero() {
        return numero;
    }
    
}