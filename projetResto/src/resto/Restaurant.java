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
    private static final ArrayList<PassePlat> listePp = new ArrayList();
    private static Employe[] tabEmployes=new Employe[(nbrEmployes+1)];    //+1 pr avoir des employés de 1 à n et pas 0 à n-1
    private static Thread[] tabThreads=new Thread[nbrEmployes+1];
    
    public static void main(String[] args) {
        SimulationClock clock = new SimulationClock(2);
        List<PointFLM> flmp = Arrays.asList(new PointFLM[]{
            new PointFLM(0, 20/3600.0),  // 20 clients par heure
            new PointFLM(3600, 40/3600.0),
            new PointFLM(7200, 30/3600.0),
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
            listePp.add(new PassePlat(i+1, clock));
        }
        
        file.start();
        
        for(int i=1; i<=nbrEmployes; i++){
            tabEmployes[i]=new Employe(i, stock, listePp, file, file.getFile(), 3, clock, 0);
            tabThreads[i]=new Thread(tabEmployes[i]);
            tabThreads[i].start();
        }
        
        clock.start();
    }
}
