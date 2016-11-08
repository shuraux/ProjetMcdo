/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1;

import java.util.ArrayList;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class PassePlat {
    private ArrayList<Sandwich> listeSandwichs=null;
    private final int numero;   //numéro qui permet d'identifer le pass-plat
    
    public PassePlat(int numero){
        this.numero=numero; //on donne un id au pp
        this.listeSandwichs = new ArrayList<>();
    }
    
    public synchronized void ajouterSandwich(){
        Sandwich sw = new Sandwich();           //on créé un objet Sandwich
        this.listeSandwichs.add(sw);        //on l'ajoute à notre liste de sandwich du pp
        this.notifyAll();
    }
    
    public synchronized void retirerSandwich(){
        while (this.listeSandwichs.size() <= 0) {
                try {
                    this.wait();
                    System.out.println("Pas de sandwich dispo sur le passe-plat");
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
        this.listeSandwichs.remove(0);
        System.out.println("Sandwich retiré du passe-plat");
        }

    public ArrayList<Sandwich> getListeSandwichs() {
        return listeSandwichs;
    }

    public int getNumero() {
        return numero;
    }
    
}