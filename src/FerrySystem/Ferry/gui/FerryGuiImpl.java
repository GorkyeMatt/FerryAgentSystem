package FerrySystem.Ferry.gui;

import FerrySystem.Commons.data.Port;
import FerrySystem.Ferry.FerryAgent;
import jade.core.AID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FerryGuiImpl extends JFrame implements FerryGui {

    private FerryAgent agent;

    public FerryGuiImpl(FerryAgent agent) throws HeadlessException {
        super("Ferry Agent System");
        this.agent = agent;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocation(50,50);

        var button = new JButton("Register in selected port");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var port = new Port();
                port.setAgentAID(new AID("port", AID.ISLOCALNAME));
                agent.registerInPort(port);
            }
        });

        add(button);
    }

    public void showGui(){
        setVisible(true);
    }
}
