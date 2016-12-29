/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.multiTache.projets.resto;

import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Un serveur simple : supprime un client de la file tous les n UT
 *
 * @author fdebertranddeb01
 */
public class ServeurPourTest extends Thread {
    
    private static AtomicInteger totClientsServis = new AtomicInteger(0);

    private FileAttenteClients file;
    private long tempsServiceEnUT;
    private SimulationClock clock;
    private boolean trace;
    private String name;

    public ServeurPourTest(FileAttenteClients file, long tempsServiceEnUT, SimulationClock clock, boolean trace, String name) {
        this.file = file;
        this.tempsServiceEnUT = tempsServiceEnUT;
        this.clock = clock;
        this.trace = trace;
        this.name = name;
    }

    
    @Override
    public void run() {
        boolean encore = true;
        while (encore) {
            try {
                this.clock.metEnAttente(this.tempsServiceEnUT);
            } catch (InterruptedException ex) {
                encore = false;
            }
            if (encore) {
                Integer client = this.file.retirePremierClient();
                if (this.trace) {
                    if (client == null) {
                        System.out.println(this.clock.getSimulationTimeEnUT() + " : " +
                                this.name + " n'a plus de client");
                    } else {
                        System.out.println(this.clock.getSimulationTimeEnUT() + " : " +
                                this.name + " sert client " + client + 
                                " (tot servis : "+totClientsServis.incrementAndGet()+")"+
                                " commande : " + Arrays.toString(this.file.commandeAlea()));
                    }
                }
            }
        }
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
    }

}
