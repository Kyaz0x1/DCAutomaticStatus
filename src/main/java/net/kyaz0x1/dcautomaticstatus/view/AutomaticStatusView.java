package net.kyaz0x1.dcautomaticstatus.view;

import net.kyaz0x1.dcautomaticstatus.events.ButtonConfigClickEvent;
import net.kyaz0x1.dcautomaticstatus.events.ButtonStartClickEvent;
import net.kyaz0x1.dcautomaticstatus.events.ButtonStopClickEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AutomaticStatusView extends JFrame {

    private final JLabel lblEnterToken;
    private final JTextField txtToken;

    public static JButton btnStart, btnStop, btnConfig;
    public static final JLabel lblUpdateCount = new JLabel("0/s");

    public AutomaticStatusView(){
        super("DCAutomaticStatus");
        setSize(400, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        this.lblEnterToken = new JLabel("Informe o token abaixo:");
        lblEnterToken.setBounds(130, 5, 135, 20);

        add(lblEnterToken);

        this.txtToken = new JTextField();
        txtToken.setBounds(10, 25, 365, 20);

        add(txtToken);

        this.btnStart = new JButton("Iniciar");
        btnStart.setBounds(65, 48, 80, 20);

        add(btnStart);

        this.btnStop = new JButton("Parar");
        btnStop.setBounds(150, 48, 80, 20);
        btnStop.setEnabled(false);

        btnStart.addActionListener(new ButtonStartClickEvent(txtToken));
        btnStop.addActionListener(new ButtonStopClickEvent());

        add(btnStop);

        this.btnConfig = new JButton("Config");
        btnConfig.setBounds(235, 48, 80 ,20);
        btnConfig.addActionListener(new ButtonConfigClickEvent());

        add(btnConfig);

        lblUpdateCount.setBounds(355, 60, 30, 20);
        lblUpdateCount.setToolTipText("Total de atualizações do status");

        add(lblUpdateCount);
    }

}