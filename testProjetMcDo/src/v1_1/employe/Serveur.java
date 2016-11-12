/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1.employe;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Serveur {
    private String choix;

    public void choixSandwich(String choix){
        if(null != choix)switch (choix) {
            case "Kebab":
                this.choix=choix;
                break;
            case "Burger":
                this.choix=choix;
                break;
            default:
                System.out.println("Choix inconnu");
                break;
        }
    }
}
