/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1_1;

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
        }
    
    public void run(){
        while(this.listeClients.isEmpty()==false){
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt dans cet exemple");
            }
        }
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