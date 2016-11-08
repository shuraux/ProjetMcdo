/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.sandwich;

import v1.Sandwich;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Kebab extends Sandwich{
    
    public Kebab(){
        this.prodSimultMax=2;
        this.tempsFabrication[0]=10;
        this.tempsFabrication[1]=15;
        this.coutFabrication=30;
        this.prixVente=60;
    }
}
