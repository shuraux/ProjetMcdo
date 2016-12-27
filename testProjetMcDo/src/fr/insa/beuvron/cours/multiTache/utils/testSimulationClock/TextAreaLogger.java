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
package fr.insa.beuvron.cours.multiTache.utils.testSimulationClock;

import javax.swing.JTextArea;

/**
 *
 * @author francois
 */
public class TextAreaLogger implements Logger {

    private final JTextArea jtaMessages;
    
    private long timeDebut;

    public TextAreaLogger(JTextArea jtaMessages) {
        this.jtaMessages = jtaMessages;
        this.timeDebut = System.currentTimeMillis();
    }

    @Override
    public void log(String message) {
        long delta = System.currentTimeMillis() - this.timeDebut;
        long millis = delta % 1000;
        long sec = (delta / 1000) % 60;
        long min = (delta / 60000);
        message = min + ":" + sec + "." + millis + " ; " + message;
        synchronized (this.jtaMessages) {
            this.jtaMessages.append(message + "\n");
        }
    }

}
