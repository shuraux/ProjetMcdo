/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Sandwich {
    protected int prodSimultMax;
    protected int coutFabrication, prixVente, tempsPeremption;
    protected String nom;
    
    public Sandwich(int prodSimultMax, int coutFabrication, int prixVente, int tempsPeremption){
        this.coutFabrication=coutFabrication;
        this.prixVente=prixVente;
        this.tempsPeremption=tempsPeremption;
        this.prodSimultMax=prodSimultMax;
        this.nom="sandwich";
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }
    
}
