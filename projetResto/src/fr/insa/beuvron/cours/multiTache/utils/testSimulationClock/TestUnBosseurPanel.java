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

import fr.insa.beuvron.cours.m3.gui.TestePanel;
import fr.insa.beuvron.cours.multiTache.utils.SimulationClock;

/**
 *
 * @author francois
 */
public class TestUnBosseurPanel extends javax.swing.JPanel {

    private SimulationClock smClock;
    private Logger log;

    /**
     * Creates new form TestUnBosseur
     */
    public TestUnBosseurPanel() {
        initComponents();

        long utEnMs = 100;
        this.smClock = new SimulationClock(utEnMs);
        this.jtfUTenMs.setText("" + utEnMs);

        this.log = new TextAreaLogger(this.jtaMessages);

        Bosseur bo = new Bosseur("toto", 20, this.smClock, this.log);
        this.bosseurPannel1.initBosseur(bo);
    }

    public static void main(String[] args) {
        TestePanel.pack(new TestUnBosseurPanel());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bosseurPannel1 = new fr.insa.beuvron.cours.multiTache.utils.testSimulationClock.BosseurPannel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMessages = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jtfUTenMs = new javax.swing.JTextField();
        jbChangeUT = new javax.swing.JButton();
        jbResetMessages = new javax.swing.JButton();
        jbStartClock = new javax.swing.JButton();
        jbStopClock = new javax.swing.JButton();

        jtaMessages.setColumns(20);
        jtaMessages.setRows(5);
        jScrollPane1.setViewportView(jtaMessages);

        jLabel1.setText("UT en ms : ");

        jtfUTenMs.setText("100");

        jbChangeUT.setText("change");
        jbChangeUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChangeUTActionPerformed(evt);
            }
        });

        jbResetMessages.setText("Reset Messages");
        jbResetMessages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbResetMessagesActionPerformed(evt);
            }
        });

        jbStartClock.setText("Start clock");
        jbStartClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartClockActionPerformed(evt);
            }
        });

        jbStopClock.setText("Stop clock");
        jbStopClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopClockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bosseurPannel1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfUTenMs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbChangeUT))
            .addComponent(jbResetMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jbStartClock)
                .addGap(18, 18, 18)
                .addComponent(jbStopClock)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bosseurPannel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfUTenMs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbChangeUT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbStartClock)
                    .addComponent(jbStopClock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbResetMessages))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbChangeUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChangeUTActionPerformed
        this.smClock.changeUT(Long.parseLong(this.jtfUTenMs.getText()));
    }//GEN-LAST:event_jbChangeUTActionPerformed

    private void jbResetMessagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbResetMessagesActionPerformed
        // TODO add your handling code here:
        synchronized (this.jtaMessages) {
            this.jtaMessages.setText("");
        }
    }//GEN-LAST:event_jbResetMessagesActionPerformed

    private void jbStartClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStartClockActionPerformed
        // TODO add your handling code here:
        
        this.smClock.start();
        
    }//GEN-LAST:event_jbStartClockActionPerformed

    private void jbStopClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStopClockActionPerformed
        // TODO add your handling code here:
        this.smClock.stop();
        
    }//GEN-LAST:event_jbStopClockActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fr.insa.beuvron.cours.multiTache.utils.testSimulationClock.BosseurPannel bosseurPannel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbChangeUT;
    private javax.swing.JButton jbResetMessages;
    private javax.swing.JButton jbStartClock;
    private javax.swing.JButton jbStopClock;
    private javax.swing.JTextArea jtaMessages;
    private javax.swing.JTextField jtfUTenMs;
    // End of variables declaration//GEN-END:variables
}
