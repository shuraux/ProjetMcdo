/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.sandwich;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
    public class Burger extends Sandwich{   //correspond à s ds le sujet
        private int[] tempsFabrication= new int[prodSimultMax];

    public Burger(){
        super(3, 20, 50, 500);
        this.tempsFabrication[0]=8;
        this.tempsFabrication[1]=14;
        this.tempsFabrication[2]=18;
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
