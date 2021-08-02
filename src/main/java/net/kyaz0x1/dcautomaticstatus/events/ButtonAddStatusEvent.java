package net.kyaz0x1.dcautomaticstatus.events;

import net.kyaz0x1.dcautomaticstatus.api.models.Status;
import net.kyaz0x1.dcautomaticstatus.manager.AutomaticStatusManager;
import net.kyaz0x1.dcautomaticstatus.view.table.StatusTableModel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonAddStatusEvent implements ActionListener {

    private final JTextField txtText;
    private final JTextField txtEmoji;

    private final JTable tbStatus;

    private final AutomaticStatusManager manager;

    public ButtonAddStatusEvent(JTextField txtText, JTextField txtEmoji, JTable tbStatus){
        this.txtText = txtText;
        this.txtEmoji = txtEmoji;
        this.tbStatus = tbStatus;

        this.manager = AutomaticStatusManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String text = txtText.getText();
        final String emoji = txtEmoji.getText();

        if(emoji.length() > 1){
            JOptionPane.showMessageDialog(null, "Provavelmente o emoji inserido é inválido!", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(emoji.isEmpty() && text.isEmpty()){
            JOptionPane.showMessageDialog(null, "Você precisa informar um emoji ou um texto para adicionar!", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
            return;
        }

        manager.addStatus(new Status(text, emoji));
        tbStatus.setModel(new StatusTableModel(manager.getStatus()));

        txtEmoji.setText("");
        txtText.setText("");

        JOptionPane.showMessageDialog(null, "Status adicionado com sucesso!");
    }

}