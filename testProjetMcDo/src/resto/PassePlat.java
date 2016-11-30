/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import resto.sandwich.Sandwich;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class PassePlat {
    private final int numero;   //numéro qui permet d'identifer le passe-plat
    private final ArrayList<Sandwich> sandwichsPp;
    private boolean commandeComplete;
    private File file;
    
    public PassePlat(int numero, File file){
        this.numero=numero; //on donne un id au pp
        this.file=file;
        this.sandwichsPp = new ArrayList<>();
    }
    
    public synchronized void ajouterSandwichPp(Sandwich sw){
        this.sandwichsPp.add(sw);
        System.out.println("Sandwich ajouté au passe-plat");
        this.notifyAll();
        System.out.flush();
    }
    
    public int getNumero() {
        return numero;
    }

    /**
     * @return the commandeComplete
     */
    public boolean isCommandeComplete() {
        return commandeComplete;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
    
}