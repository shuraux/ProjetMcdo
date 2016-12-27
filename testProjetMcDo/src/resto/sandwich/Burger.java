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

    public Burger(){
        super(3, 20, 50, 500);
        this.tempsFabrication[0]=800;
        this.tempsFabrication[1]=1400;
        this.tempsFabrication[2]=1800;
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
