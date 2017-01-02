/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opti;

import java.util.ArrayList;
/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Solutions {
   
    public static void main(String[] args) {
        ArrayList<Planning> listePlannings = new ArrayList();
        Planning planningDepart=new Planning();
        planningDepart.planningDeDepart();        //va créer dans planning le planning de départ de l'énoncé
        System.out.println(planningDepart.toString());
        listePlannings.add(planningDepart);
    }
    
}
