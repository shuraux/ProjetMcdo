/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opti;

/**
 *
 * @author Sylvain HURAUX <your.name at your.org>
 */
public class Planning {
    private int[][] planning=new int[3][31]; //[ligne] [colonne]
    private int profit;
    
    public Planning(){
    }
    
    public void planningDeDepart(){
        this.profit=prodEnCours();
        pause(6, 9, 0);
        this.profit=this.profit+prod1S1(10, 0);
        this.profit=this.profit+prod1S2(18,0);
        pause(28, 30, 0);
        
        pause(0, 1, 1);
        this.profit=this.profit+prod2S2(2, 1);
        pause(17, 19, 1);
        this.profit=this.profit+prod1S2(20, 1);
        this.planning[1][30]=0;     //pause que pendant 1 ut
        
        this.profit=this.profit+prod3S1(9, 2);
        pause(27, 30, 2);
    }
    
    public int prod1S1(int debut,int employe){
        S1 sw1=new S1();
        assignementPoste(debut, debut+sw1.getTempsProd()[0], employe, 1);
        return sw1.getPrixVente()-sw1.getCoutFabrication();
    }
    public int prod2S1(int debut,int employe){
        S1 sw1=new S1();
        assignementPoste(debut, debut+sw1.getTempsProd()[1], employe, 1);
        return 2*sw1.getPrixVente()-2*sw1.getCoutFabrication();
    }
    public int prod3S1(int debut,int employe){
        S1 sw1=new S1();
        assignementPoste(debut, debut+sw1.getTempsProd()[2], employe, 1);
        return 3*sw1.getPrixVente()-3*sw1.getCoutFabrication();
    }
    public int prod1S2(int debut,int employe){
        S2 sw2=new S2();
        assignementPoste(debut, debut+sw2.getTempsProd()[0], employe, 2);
        return sw2.getPrixVente()-sw2.getCoutFabrication();
    }
    public int prod2S2(int debut,int employe){
        S2 sw2=new S2();
        assignementPoste(debut, debut+sw2.getTempsProd()[1], employe, 2);
        return 2*sw2.getPrixVente()-2*sw2.getCoutFabrication();
    }
    public void pause(int debut, int fin, int employe){
        assignementPoste(debut, fin, employe, 0);
    }
    
    public void assignementPoste(int debut, int fin, int employe, int role){
        for(int i=debut; i<=fin; i++){
            this.planning[employe][i]=role;
        }
    }

    public int prodEnCours(){   //correspond aux productions par les employés 1 et 3 qui sont en cours à T=0
        S1 sw1=new S1();
        S2 sw2=new S2();
        for(int i=0; i<=5; i++){
            this.planning[0][i]=1;   //employé 1 en cours de production de S1
        }
        for(int i=0; i<=8; i++){
            this.planning[2][i]=2;    //employé 3 en cours de production S2
        }
        return (2*sw1.getPrixVente()-2*sw1.getCoutFabrication())+(2*sw2.getPrixVente()-2*sw2.getCoutFabrication());
    }       //pour gérer les 2 prods en cours du planning de l'énoncé
    
    
    /**
     * @return the planning
     */
    public int[][] getPlanning() {
        return planning;
    }

    /**
     * @return the profit
     */
    public int getProfit() {
        return profit;
    }
    
    public String toString(){
        String sortie="";
        for(int i=0; i<3; i++){
            for(int j=0; j<31; j++){
                sortie=sortie+planning[i][j]+", ";
            }
            sortie=sortie+"\n";
        }
        sortie=sortie+"Le profit est de " +this.profit + "\n";
        return sortie;
    }
}
