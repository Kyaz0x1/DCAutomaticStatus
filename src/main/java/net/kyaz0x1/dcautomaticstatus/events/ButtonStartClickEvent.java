package net.kyaz0x1.dcautomaticstatus.events;

import net.kyaz0x1.dcautomaticstatus.api.DiscordAPI;
import net.kyaz0x1.dcautomaticstatus.api.auth.AuthCredentials;
import net.kyaz0x1.dcautomaticstatus.manager.AutomaticStatusManager;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonStartClickEvent implements ActionListener {

    private final JTextField txtToken;
    private final DiscordAPI api;

    private final AutomaticStatusManager manager;

    public ButtonStartClickEvent(JTextField txtToken){
        this.txtToken = txtToken;
        this.api = DiscordAPI.getInstance();

        this.manager = AutomaticStatusManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String token = txtToken.getText();

        if(token.isEmpty()){
            JOptionPane.showMessageDialog(null, "Informe o token!", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        AuthCredentials.TOKEN = token;

        if(!isToken(token) && !api.isValidAccount(AuthCredentials.TOKEN)){
            JOptionPane.showMessageDialog(null, "O token informado é inválido!", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(manager.getStatus().isEmpty()){
            JOptionPane.showMessageDialog(null, "Não há nenhum status para iniciar! Adicione um status nas configurações do programa.", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        manager.start();

        JOptionPane.showMessageDialog(null, "Status automático iniciado com sucesso!");
    }

    private boolean isToken(String value){
        return value.matches("[a-zA-Z0-9]{24}\\.[a-zA-Z0-9]{6}\\.[a-zA-Z0-9_\\-]{27}|mfa\\.[a-zA-Z0-9_\\-]{84}");
    }

}
