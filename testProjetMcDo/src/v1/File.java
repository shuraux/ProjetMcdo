/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1;

import java.util.ArrayList;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class File implements Runnable{
    private int longueur;
    private ArrayList<Client> listeClients=null;
    private PassePlat passePlat;
    
    public File(int longueur, PassePlat passePlat){
        this.longueur=longueur;         //on prend la longueur max de la file en entrée
        this.passePlat=passePlat;
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
                this.wait();
                 System.out.println("File d'attente vide");
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
        this.passePlat.retirerSandwich();   //le client va consommer un sandwich
        this.listeClients.remove(0);        //le client va partir
        }
    
    public void run(){
        this.retirerClient();               //le client consomme un dwich et sort de la file
        System.out.println("Le client n°" + this.getListeClients().get(0).getNumero() + " prend son dwich s'en va");
    }

    public int getLongueur() {
        return longueur;
    }

    public ArrayList<Client> getListeClients() {
        return listeClients;
    }

    public PassePlat getPassePlat() {
        return passePlat;
    }
}
