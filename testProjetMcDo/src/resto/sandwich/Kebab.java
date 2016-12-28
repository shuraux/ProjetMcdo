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
public class Kebab extends Sandwich{
    private int[] tempsFabrication= new int[prodSimultMax];

    public Kebab(long momentProd){
        super(2, 30, 60, 30, momentProd);
        this.tempsFabrication[0]=10;
        this.tempsFabrication[1]=15;
        this.nom="Kebab";
    }
    
    public boolean isKebab(){
        return true;
    }

    /**
     * @return the tempsFabrication
     */
    public int[] getTempsFabrication() {
        return tempsFabrication;
    }
    
}