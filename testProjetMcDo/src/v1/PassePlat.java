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
        this.listeSandwichs.add(sw);       //on l'ajoute à notre liste de sandwich du pp
    }
    
    public synchronized void retirerSandwich(){
        if(this.getListeSandwichs().size()>0){   //si il reste des sandwichs sur le pp
            this.getListeSandwichs().remove(0);  //on enlève le premier élément (le + vieux)
        }
        else{
            System.out.println("Plus de sandwichs dans le passe plat n: " + this.getNumero());
        }
    }

    /**
     * @return the listeSandwichs
     */
    public ArrayList<Sandwich> getListeSandwichs() {
        return listeSandwichs;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }
    
}