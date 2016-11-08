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
public class Producteur implements Runnable{
   private int numero;
   private PassePlat passePlat;
   private long tempsProdMin, tempsProdMax;
    
    public Producteur(PassePlat passePlat, int numero, long tempsProdMin, long tempsProdMax){
        this.numero=numero;
        this.passePlat=passePlat;
        this.tempsProdMin=tempsProdMin;
        this.tempsProdMax=tempsProdMax;
    }
    
    public synchronized long dureeProd(long tempsProdMin, long tempsProdMax){
        long delta = tempsProdMax - tempsProdMin;
        return tempsProdMin + (long) (Math.random() * (delta +1));
    }
    
    public void run(){
        for(int i = 0; i < 10; i++){
            try {
                    Thread.sleep(dureeProd(tempsProdMin, tempsProdMax));
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            System.out.println("Sandwich créé par le producteur n°" + numero);
            passePlat.ajouterSandwich();
            System.out.println(passePlat.getListeSandwichs().size());
        }
    }
}
