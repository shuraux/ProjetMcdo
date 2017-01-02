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
public class S2 {
    private int prodSimult, coutFabrication, prixVente, frequence, stock, derniereDemande, demande;
    private int[] tempsProd=new int[2];
    
    public S2(){
        this.prodSimult=2;
        this.tempsProd[0]=10;
        this.tempsProd[1]=15;
        this.coutFabrication=30;
        this.prixVente=60;
        this.frequence=7;
        this.stock=2;
        this.derniereDemande=-6;
        this.demande=0;
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
