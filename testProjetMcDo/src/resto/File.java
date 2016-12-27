/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class File{
    private int longueur;
    private ArrayList<Client> listeClients=null;
    
    public File(int longueur){
        this.longueur=longueur;        //on prend la longueur max de la file en entrée
        this.listeClients = new ArrayList<>();
    }
    
    public synchronized void ajouterClient(Client client){
        System.out.println("Client n°" + client.getNumero() + " ajouté à la file d'attente");
        this.listeClients.add(client);
        this.notifyAll();
    }
    
    public synchronized void retirerClient(){
        while (this.listeClients.size() <= 0) {
            try {
                System.out.println("File d'attente vide");
                this.wait();
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.listeClients.remove(0);
        System.out.println("Client servi et parti\n");
    }
    
    public int getLongueur() {
        return longueur;
    }

    public ArrayList<Client> getListeClients() {
        return listeClients;
    }
    
}