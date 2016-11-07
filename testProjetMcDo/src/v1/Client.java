/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Client implements Runnable{
    private final int numero;
    private PassePlat passePlat;
    private long tempsConsoMin, tempsConsoMax;
    
    public Client(PassePlat passePlat, int numero, long tempsConsoMin, long tempsConsoMax){
        this.numero=numero;
        this.passePlat=passePlat;
        this.tempsConsoMin=tempsConsoMin;
        this.tempsConsoMax=tempsConsoMax;
    }
    
    public synchronized long dureeConso(long tempsConsoMin, long tempsConsoMax){
        long delta = tempsConsoMax - tempsConsoMin;
        return tempsConsoMin + (long) (Math.random() * (delta +1));
    }
    
    public void run(){
        int i=0;
        while(i<5){
            System.out.println("Le client n" + this.numero +  " mange");
            passePlat.retirerSandwich();
            try {
                Thread.sleep(dureeConso(tempsConsoMin, tempsConsoMax));
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        i++;    
        }
    }
    
}
