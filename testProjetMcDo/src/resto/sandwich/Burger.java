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
public class Burger extends Sandwich{   //correspond à s ds le sujet
    private int[] tempsFabrication= new int[prodSimultMax];

    public Burger(long momentProd){
        super(3, 20, 50, 30, momentProd);
        this.tempsFabrication[0]=8;
        this.tempsFabrication[1]=14;
        this.tempsFabrication[2]=18;
        this.nom="Burger";
    }
    
    public boolean isBurger(){
        return true;
    }

    /**
     * @return the tempsFabrication
     */
    public int[] getTempsFabrication() {
        return tempsFabrication;
    }
    
}
