/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opti;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class S1 {
    private int prodSimult, coutFabrication, prixVente, frequence, stock, derniereDemande, demande;
    private int[] tempsProd=new int[3];
    
    public S1(){
        this.prodSimult=3;
        this.tempsProd[0]=8;
        this.tempsProd[1]=14;
        this.tempsProd[2]=18;
        this.coutFabrication=20;
        this.prixVente=50;
        this.frequence=5;
        this.stock=0;
        this.derniereDemande=-2;
        this.demande=3;
    }

    /**
     * @return the prodSimult
     */
    public int getProdSimult() {
        return prodSimult;
    }

    /**
     * @return the coutFabrication
     */
    public int getCoutFabrication() {
        return coutFabrication;
    }

    /**
     * @return the prixVente
     */
    public int getPrixVente() {
        return prixVente;
    }

    /**
     * @return the frequence
     */
    public int getFrequence() {
        return frequence;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the derniereDemande
     */
    public int getDerniereDemande() {
        return derniereDemande;
    }

    /**
     * @return the demande
     */
    public int getDemande() {
        return demande;
    }

    /**
     * @return the tempsProd
     */
    public int[] getTempsProd() {
        return tempsProd;
    }
}
