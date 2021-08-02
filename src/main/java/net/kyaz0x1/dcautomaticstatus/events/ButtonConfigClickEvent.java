package net.kyaz0x1.dcautomaticstatus.events;
;
import net.kyaz0x1.dcautomaticstatus.view.StatusConfigView;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonConfigClickEvent implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> new StatusConfigView().setVisible(true));
    }

}