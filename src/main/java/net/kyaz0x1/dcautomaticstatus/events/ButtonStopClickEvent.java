package net.kyaz0x1.dcautomaticstatus.events;

import net.kyaz0x1.dcautomaticstatus.manager.AutomaticStatusManager;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonStopClickEvent implements ActionListener {

    private final AutomaticStatusManager manager;

    public ButtonStopClickEvent(){
        this.manager = AutomaticStatusManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        manager.stop();
        JOptionPane.showMessageDialog(null, "Status automático parado com sucesso!");
    }

}