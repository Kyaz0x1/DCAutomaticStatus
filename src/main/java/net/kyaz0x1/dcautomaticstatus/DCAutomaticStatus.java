package net.kyaz0x1.dcautomaticstatus;

import net.kyaz0x1.dcautomaticstatus.view.AutomaticStatusView;

import javax.swing.SwingUtilities;

public class DCAutomaticStatus {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new AutomaticStatusView().setVisible(true));
    }

}