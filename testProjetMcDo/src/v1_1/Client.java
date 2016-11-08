/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Client {
    private final int numero;
    private long tempsConsoMin, tempsConsoMax;
    
    public Client(int numero, long tempsConsoMin, long tempsConsoMax){
        this.numero=numero;
        this.tempsConsoMin=tempsConsoMin;
        this.tempsConsoMax=tempsConsoMax;
    }
    
    public synchronized long dureeConso(long tempsConsoMin, long tempsConsoMax){
        long delta = tempsConsoMax - tempsConsoMin;
        return tempsConsoMin + (long) (Math.random() * (delta +1));
    }

    public int getNumero() {
        return numero;
    }

    public long getTempsConsoMin() {
        return tempsConsoMin;
    }

    public long getTempsConsoMax() {
        return tempsConsoMax;
    }
    
}
