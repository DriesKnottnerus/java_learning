package baltesten;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

/**
 *
 * @author jk4505 Dit is een "mono-thread" versie van TestBalAWT
 */
public class TestBalSwing extends JApplet {

    private static final long serialVersionUID = 1L;
    JButton stopKnop;
    JPanel kleurPanel;
    JPanel knopPaneel;
    Color balkleur;
    BalJPanel veld;
    JComboBox kleurBox;
    JComboBox sizeBox;

    JRadioButton opzetKnop;
    JRadioButton biljartKnop;
    JRadioButton muurKnop;

    volatile boolean isActive;

    // Thread engine;
    ExecutorService executor;

    void createGUI() {

        setSize(600, 400);
        setBackground(Color.WHITE);
        setForeground(Color.RED);
        setLayout(new BorderLayout());
        knopPaneel = new JPanel();
        knopPaneel.setOpaque(true);
        kleurPanel = new JPanel();
        kleurPanel.setPreferredSize(
                new Dimension(20, 20));

        balkleur = new Color(255, 0, 0);
        kleurPanel.setBackground(balkleur);
        kleurPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        String[] kleuren = {"rood", "wit", "zwart", "blauw", "geel"};
        kleurBox = new JComboBox(kleuren);
        kleurBox.setSelectedIndex(0);

        String[] sizes = {"10", "20", "30", "40", "50"};
        sizeBox = new JComboBox(sizes);
        sizeBox.setSelectedIndex(0);

        veld = new BalJPanel();

        stopKnop = new JButton("Stop");

        //	knopPaneel.add(startKnop);
        opzetKnop = new JRadioButton("opzetten");
        opzetKnop.setSelected(true);
        biljartKnop = new JRadioButton("biljarten");
        muurKnop = new JRadioButton("muur");
        ButtonGroup bgrp = new ButtonGroup();
        bgrp.add(opzetKnop);
        bgrp.add(biljartKnop);
        bgrp.add(muurKnop);
        JPanel rbtnPanel = new JPanel(new GridLayout(0, 1));
        rbtnPanel.add(opzetKnop);
        rbtnPanel.add(biljartKnop);
        rbtnPanel.add(muurKnop);

        veld.setBiljarten(false);
        veld.setMuurOpzetten(false);
        veld.setBallsize(10);

        knopPaneel.add(sizeBox);
        knopPaneel.add(kleurBox);
        knopPaneel.add(kleurPanel);
        knopPaneel.add(rbtnPanel);
        knopPaneel.add(stopKnop);

        add(knopPaneel, BorderLayout.NORTH);
        add(veld, BorderLayout.CENTER);

        stopKnop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                veld.stop();
            }
        });

        kleurPanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == kleurPanel) {
                    balkleur = JColorChooser.showDialog(knopPaneel, "Kies een nieuwe balkleur", balkleur);
                    kleurPanel.setBackground(balkleur);
                    veld.setBalKleur(balkleur);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }
        });

        sizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String size = (String) cb.getSelectedItem();
                int sz = Integer.valueOf(size);
                veld.setBallsize(sz);
            }//end action performed
        }//end ActionListener
        );//end addActionListener

        kleurBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String kleur = (String) cb.getSelectedItem();
                switch (kleur) {
                    case "rood":
                        balkleur = new Color(255, 0, 0);
                        break;
                    case "wit":
                        balkleur = new Color(255, 255, 255);
                        break;
                    case "blauw":
                        balkleur = new Color(0, 0, 255);
                        break;
                    case "zwart":
                        balkleur = new Color(0, 0, 0);
                        break;
                    case "geel":
                        balkleur = new Color(255, 255, 0);
                        break;
                }
                kleurPanel.setBackground(balkleur);
                veld.setBalKleur(balkleur);
            }//end action performed
        }//end ActionListener
        );//end addActionListener

        opzetKnop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                veld.setBiljarten(false);
                veld.zetWrijving(false);
                veld.setMuurOpzetten(false);
            }
        });
        biljartKnop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                veld.setBiljarten(true);
                veld.zetWrijving(true);
                veld.setMuurOpzetten(false);
            }
        });
        muurKnop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                veld.setBiljarten(false);
                veld.zetWrijving(false);
                veld.setMuurOpzetten(true);
            }
        });

    }	//end createGui

    @Override
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    createGUI();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } //end init

    @Override
    public void start() {
        /*
         engine = new Thread(new Runnable() {
         @Override
         public void run() {
         while (isActive) {
         try {
         Thread.sleep(30);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         veld.moveBalls();
         }
         }
         });
         isActive = true;
         engine.start();
         */
        Runnable runEngine = new Runnable() {
            @Override
            public void run() {
                while (isActive) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    veld.moveBalls();
                }
            }
        };

        isActive = true;
        executor = Executors.newSingleThreadExecutor();
        executor.execute(runEngine);

    }

    @Override
    public void stop() {
        isActive = false;
        // engine = null;
        executor.shutdown();
    }

}
