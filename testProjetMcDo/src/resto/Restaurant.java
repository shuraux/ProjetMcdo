/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto;

import java.util.ArrayList;
import resto.employe.Producteur;
import resto.employe.Serveur;
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
    private static final int nbrCaisses=3, nbrEmployes=5;
    private static ArrayList<PassePlat> listePp = new ArrayList();
    private static PassePlat tabPp[] = new PassePlat[nbrCaisses];
    
    public static void main(String[] args) {
        SimulationClock clock = new SimulationClock(100);
        List<PointFLM> flmp = Arrays.asList(new PointFLM[]{
            new PointFLM(0, 60/3600.0),  // 1 clients par heure
            new PointFLM(3600, 40/3600.0),
            new PointFLM(7200, 30/3600.0),
            new PointFLM(10800, 60/3600.0),
            new PointFLM(14400, 0.0),});
        
        double[] probasClientReste =  FileAttenteClients.probasLineaires(5);
        double[] probasNbrSandwich = FileAttenteClients.probasConstantes(3);
        double[] probasTypeSandwich = FileAttenteClients.probasConstantes(2);
        System.out.println("probas : " + Arrays.toString(probasClientReste));
        FonctionLineaireParMorceaux arrivees = new FonctionLineaireParMorceaux(flmp);
        
        FileAttenteClients file = new FileAttenteClients(clock, arrivees,
                probasClientReste,probasNbrSandwich,probasTypeSandwich);
        file.setTrace(true);
        
        for (int i=0; i<nbrCaisses; i++){
            listePp.add(new PassePlat(i, file, clock));
        }
        Stock stock = new Stock(clock);
        
        file.start();
        
        Thread p1 = new Thread(new Producteur(stock, 1, clock));
        Thread p2 = new Thread(new Producteur(stock, 2, clock));
        Thread s1 = new Thread(new Serveur(stock, 1, listePp, clock, file, 3, true));
        //Thread s2 = new Thread(new Serveur(stock, 2, listePp, clock, file, 200, true));
        p1.start();
        p2.start();
        s1.start();
        //s2.start();
        clock.start();
    }
}