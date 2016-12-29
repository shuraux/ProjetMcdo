/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.melOptimisation.utils;

import java.util.Comparator;

/**
 * un point dans une Fonction Linéaire par Morceau.
 * Simplement un point 2D.
 * @author fdebertranddeb01
 */
public class PointFLM {
    private double x;
    private double y;

    public PointFLM(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public static final CompareXErreurSiEgal XCOMPARATOR = new CompareXErreurSiEgal();

    public static class CompareXErreurSiEgal implements Comparator<PointFLM> {

        @Override
        public int compare(PointFLM o1, PointFLM o2) {
            if (o1.x < o2.x) {
                return -1;
            } else if (o2.x < o1.x) {
                return 1;
            } else {
                throw new Error("Points Même abscisse interdits");
            }
        }
        
    }
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
}
