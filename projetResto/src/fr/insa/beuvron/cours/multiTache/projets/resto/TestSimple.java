/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.multiTache.projets.resto;

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.melOptimisation.utils.PointFLM;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fdebertranddeb01
 */
public class TestSimple {
    public static void main(String[] args) {
        SimulationClock clock = new SimulationClock(10);
        List<PointFLM> flmp = Arrays.asList(new PointFLM[]{
            new PointFLM(0, 20/3600.0),  // 20 clients par heure
            new PointFLM(3600, 40/3600.0),
            new PointFLM(7200, 30/3600.0),
            new PointFLM(10800, 60/3600.0),
            new PointFLM(14400, 0.0),});
        double[] probasClientReste =  FileAttenteClients.probasLineaires(20);
        double[] probasNbrSandwich = FileAttenteClients.probasConstantes(3);
        double[] probasTypeSandwich = FileAttenteClients.probasConstantes(2);
        System.out.println("probas : " + Arrays.toString(probasClientReste));
        FonctionLineaireParMorceaux arrivees = new FonctionLineaireParMorceaux(flmp);
        FileAttenteClients file = new FileAttenteClients(clock, arrivees,
                probasClientReste,probasNbrSandwich,probasTypeSandwich);
        file.setTrace(true);
        file.start();
        ServeurPourTest s1 = new ServeurPourTest(file, 400, clock, true,"toto");
        ServeurPourTest s2 = new ServeurPourTest(file, 1000, clock, true,"titi");
        s1.start();
        s2.start();
        clock.start();
    }
    
}
