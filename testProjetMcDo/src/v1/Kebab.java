package v1;


import v1.Sandwich; // G : Utile ? Les classes Kebab et Sandwich sont dans le même package. 
// G : En fait, la classe Kebab est juste un instance de la classe Sandwich, un cas particulier de la classe Sandwich

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Kebab extends Sandwich{
    
    public Kebab(){
        this.prodSimultMax=2;
        this.tempsFabrication[0]=10;
        this.tempsFabrication[1]=15;
        this.coutFabrication=30;
        this.prixVente=60;
    }
}
