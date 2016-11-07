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
    
    public Client(PassePlat passePlat, int numero){
        this.numero=numero;
        this.passePlat=passePlat;
    }
    
    public void run(){
        for(int i = 0; i < 3; i++){
            System.out.println("Sandwich mangé par le client " + numero);
            passePlat.retirerSandwich();
            System.out.println(passePlat.getListeSandwichs().size());
        }
    }
    
}
