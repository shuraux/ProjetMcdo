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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class ClientGenerator implements Runnable {

    private SimulationClock clock;
    private FonctionLineaireParMorceaux frequences;
    private FileAttenteClients file;
    private boolean trace;

    public ClientGenerator(SimulationClock clock, FonctionLineaireParMorceaux frequences, FileAttenteClients file) {
        this.clock = clock;
        this.frequences = frequences;
        this.file = file;
    }
    
    

    @Override
    public void run() {
        double moyenne = 1;
        CalculsDirectDistributions probas = new CalculsDirectDistributions();
        while (moyenne != 0) {
            long curTime = this.clock.getSimulationTimeEnUT();
            moyenne = frequences.valeurEn(curTime);
            if (moyenne != 0) {
                long delai = (long) probas.loiExponentielle(moyenne);
                if (this.trace) {
                    System.out.println("delai avant prochain client : " + delai + " ut  (moyenne = " + 1/moyenne + ")");
                }
                try {
                    this.clock.metEnAttente(delai);
                    this.file.clientArrive();
                } catch (InterruptedException ex) {
                    moyenne = 0;
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
