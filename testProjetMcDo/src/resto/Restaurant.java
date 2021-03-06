/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;
import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.melOptimisation.utils.PointFLM;
import fr.insa.beuvron.cours.multiTache.projets.resto.FileAttenteClients;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Restaurant {
    private static final int nbrCaisses=2, nbrEmployes=3;
    private final static ArrayList<PassePlat> listePp = new ArrayList();
    
    public static void main(String[] args) {
        SimulationClock clock = new SimulationClock(70);
        List<PointFLM> flmp = Arrays.asList(new PointFLM[]{
            new PointFLM(0, 60/3600.0),  // 20 clients par heure
            new PointFLM(3600, 60/3600.0),
            new PointFLM(7200, 60/3600.0),
            new PointFLM(10800, 60/3600.0),
            new PointFLM(14400, 0.0),});
        
        double[] probasClientReste =  FileAttenteClients.probasLineaires(5);
        double[] probasNbrSandwich = FileAttenteClients.probasConstantes(3);
        double[] probasTypeSandwich = FileAttenteClients.probasConstantes(2);
        System.out.println("probas : " + Arrays.toString(probasClientReste));
        FonctionLineaireParMorceaux arrivees = new FonctionLineaireParMorceaux(flmp);
        
        Stock stock = new Stock(clock);
        
        FileAttenteClients file = new FileAttenteClients(clock, arrivees,
                probasClientReste,probasNbrSandwich,probasTypeSandwich);
        file.setTrace(true);
        
        for (int i=0; i<nbrCaisses; i++){
            listePp.add(new PassePlat(i+1, file, clock));
        }
        
        Employe employe1 = new Employe(1, stock, listePp, file.getFile(), 3, clock,0);
        Employe employe2 = new Employe(2, stock, listePp, file.getFile(), 3, clock, 0);
        Employe employe3 = new Employe(3, stock, listePp, file.getFile(), 3, clock, 0);
        Thread e1 = new Thread(employe1);
        Thread e2 = new Thread(employe2);
        Thread e3 = new Thread(employe3);
        
        file.start();
        
        e1.start();
        e2.start();
        e3.start();
        
        clock.start();
    }
}
