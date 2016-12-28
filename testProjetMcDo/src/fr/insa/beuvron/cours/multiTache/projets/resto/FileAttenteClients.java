/*
Copyright 2000-2014 Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.projets.resto;

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import fr.insa.beuvron.cours.probas.CalculsDirectDistributions;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import resto.Employe;

/**
 * Simulation de l'arrivée de client dans un restaurant.
 * @author francois
 */
public class FileAttenteClients {

    private ArrayBlockingQueue<Integer> file;
    private FonctionLineaireParMorceaux nombreMoyenClientsParUT;
    private double[] probasClientReste;
    private double[] probasNombreDeSandwichs;
    private double[] probasTypesDeSandwichs;
    private ClientGenerator generator;
    private int numNextClient;
    private SimulationClock clock;
    private Random rand;
    private Thread genThread;
    private boolean trace;
    private Employe serveur;

    /**
     * 
     * @param clock fixe la vitesse de la simulation : nombre de ms par UT
     * @param nombreMoyenClientsParUT UT : unité de temps de la simulation
     * @param probasClientReste probasClientReste[i] : proba que le client reste quand il y a 
     * déjà i clients dans la file d'attente. Pour i >= probasClientReste.length, on suppose
     * la proba nulle (le client s'en va tout le temps)
     */
    public FileAttenteClients(SimulationClock clock, FonctionLineaireParMorceaux nombreMoyenClientsParUT,
            double[] probasClientReste, double[] probasNombreDeSandwichs, double[] probasTypesDeSandwichs){
        this.clock = clock;
        this.nombreMoyenClientsParUT = nombreMoyenClientsParUT;
        this.probasClientReste = probasClientReste;
        this.probasNombreDeSandwichs = probasNombreDeSandwichs;
        this.probasTypesDeSandwichs = probasTypesDeSandwichs;
        this.file = new ArrayBlockingQueue<>(probasClientReste.length);
        this.generator = new ClientGenerator(clock, nombreMoyenClientsParUT, this);
        this.numNextClient = 1;
        this.rand = new Random();
    }

    public void start() {
        this.genThread = new Thread(this.generator);
        this.genThread.start();
    }

    public void stop() {
        this.clock.interromptAttente(this.genThread);
    }

    public synchronized void clientArrive() {
        int nbr = this.getFile().size();
        boolean ok = false;
        if (nbr < probasClientReste.length) {
            if (this.rand.nextDouble() < this.probasClientReste[nbr]) {
                getFile().add(this.numNextClient);
                ok = true;
            }
        }
        if (this.isTrace()) {
            if (ok) {
                System.out.println(this.clock.getSimulationTimeEnUT() + " : " +"client " +
                        (this.numNextClient) + " entre dans file ("+this.getFile().size()+" clients)");
            } else {
                System.out.println(this.clock.getSimulationTimeEnUT() + " : " +"client " +
                        (this.numNextClient) + " trouve trop de monde("+this.getFile().size()+" clients)");
            }
        }
        this.numNextClient++;
    }

    /**
     * commande du client.
     * Dans cette version, la commande est tirée aléatoirement :
     *   le nombre de sandwich est tiré en fonction de {@link #probasNombreDeSandwichs}
     * Pour chaque sandwich, le type du sandwich est tiré en fonction de {@link #probasTypesDeSandwichs}
     * 
     * @return 
     */
    public int[] commandeAlea() {
        CalculsDirectDistributions probas = new CalculsDirectDistributions();
        int nbr = probas.loiProbaExplicite(this.probasNombreDeSandwichs)+1;
        int[] res = new int[nbr];
        for (int i = 0; i < res.length; i++) {
            res[i] = probas.loiProbaExplicite(this.probasTypesDeSandwichs);
        }
        return res;
    }
    /**
     * retire le premier client de la file. 
     * @return les sadwichs commandés par le client, ou null si la file était vide
     */
    public Integer retirePremierClient() {
        return this.getFile().poll();
    }

    /**
     * nombre actuel de clients dans la file.
     * Attention : peut varier à tout moment par création
     * de client par le ClientGenerator
     * @return 
     */
    public int nbrClients() {
        return this.getFile().size();
    }

    public static double[] probasConstantes(int nbrMax) {
        double[] res = new double[nbrMax];
        double proba = 1.0/nbrMax;
        for (int i = 0; i < nbrMax; i++) {
            res[i] = proba;
        }
        return res;
    }

    public static double[] probasLineaires(int nbrMax) {
        double[] res = new double[nbrMax];
        for (int i = 0; i < nbrMax; i++) {
            res[i] = ((double) (nbrMax - i)) / nbrMax;
        }
        return res;
    }

    /**
     * @return the trace
     */
    public boolean isTrace() {
        return trace;
    }

    /**
     * @param trace the trace to set
     */
    public void setTrace(boolean trace) {
        this.trace = trace;
        this.generator.setTrace(trace);
    }

    /**
     * @return the nombreMoyenClientsParUT
     */
    public FonctionLineaireParMorceaux getNombreMoyenClientsParUT() {
        return nombreMoyenClientsParUT;
    }

    /**
     * @return the probasClientReste
     */
    public double[] getProbasClientReste() {
        return probasClientReste;
    }

    /**
     * @return the probasNombreDeSandwichs
     */
    public double[] getProbasNombreDeSandwichs() {
        return probasNombreDeSandwichs;
    }

    /**
     * @return the probasTypesDeSandwichs
     */
    public double[] getProbasTypesDeSandwichs() {
        return probasTypesDeSandwichs;
    }

    /**
     * @return the generator
     */
    public ClientGenerator getGenerator() {
        return generator;
    }

    /**
     * @return the clock
     */
    public SimulationClock getClock() {
        return clock;
    }

    /**
     * @return the file
     */
    public ArrayBlockingQueue<Integer> getFile() {
        return file;
    }


}
