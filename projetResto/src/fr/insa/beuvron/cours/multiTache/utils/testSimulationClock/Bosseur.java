/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.multiTache.utils.testSimulationClock;

import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author fdebertranddeb01
 */
public class Bosseur implements Runnable {
    private String nom;
    private long dureeJobEnUT;
    private SimulationClock clock;
    private Logger logger;
    private int numJob;
    private boolean doitArreter;

    public Bosseur(String nom, long dureeJobEnUT, SimulationClock clock, Logger logger) {
        this.nom = nom;
        this.dureeJobEnUT = dureeJobEnUT;
        this.clock = clock;
        this.logger = logger;
        this.doitArreter = false;
    }

    @Override
    public void run() {
        this.logger.log(this.clock.getSimulationTimeEnUT() + " ; " + this.nom + " : début de boulot ");
        while (! this.doitArreter) {
            this.numJob++;
            boolean jobFini = true;
            this.getLogger().log(this.clock.getSimulationTimeEnUT() + " ; " + this.nom + " : Debut job " + this.getNumJob());
            try {
                this.clock.metEnAttente(dureeJobEnUT);
            } catch (InterruptedException ex) {
                this.getLogger().log(this.clock.getSimulationTimeEnUT() + " ; " + this.nom + " : job interrompu " + this.getNumJob());
                jobFini = false;
            }
            if (jobFini) {
                this.getLogger().log(this.clock.getSimulationTimeEnUT() + " ; " + this.nom + " : fin normale job " + this.getNumJob());
            }
        }
        this.logger.log(this.clock.getSimulationTimeEnUT() + " ; " + this.nom + " : fin de boulot ");
    }
    
    /**
     * @return the dureeJobEnUT
     */
    public long getDureeJobEnUT() {
        return dureeJobEnUT;
    }

    /**
     * @param dureeJobEnUT the dureeJobEnUT to set
     */
    public void setDureeJobEnUT(long dureeJobEnUT) {
        this.dureeJobEnUT = dureeJobEnUT;
    }

    /**
     * @return the clock
     */
    public SimulationClock getClock() {
        return clock;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return the numJob
     */
    public int getNumJob() {
        return numJob;
    }

    /**
     * @return the doitArreter
     */
    public boolean isDoitArreter() {
        return doitArreter;
    }

    /**
     * @param doitArreter the doitArreter to set
     */
    public void setDoitArreter(boolean doitArreter) {
        this.doitArreter = doitArreter;
    }
    
}
