package v1;


import v1.Sandwich; // G : Idem que la classe Kebab

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
    public class Burger extends Sandwich{   //correspond à s ds le sujet
        
    public Burger(){
        this.prodSimultMax=3;
        this.tempsFabrication[0]=8;
        this.tempsFabrication[1]=14;
        this.tempsFabrication[2]=18;
        this.coutFabrication=20;
        this.prixVente=50;
    }

    public boolean isBurger() { // G : Méthode normale dont le paramètre implicite this est de type Burger, renvoie un résultat de type boolean
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
