package net.kyaz0x1.dcautomaticstatus.view;

import net.kyaz0x1.dcautomaticstatus.api.models.Status;
import net.kyaz0x1.dcautomaticstatus.config.StatusConfig;
import net.kyaz0x1.dcautomaticstatus.events.ButtonAddStatusEvent;
import net.kyaz0x1.dcautomaticstatus.manager.AutomaticStatusManager;
import net.kyaz0x1.dcautomaticstatus.view.table.StatusTableModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatusConfigView extends JFrame implements WindowListener {

    private final JLabel lblUpdateTime;
    private final JSpinner spnTime;
    private final JComboBox<String> cbTimeType;

    private final JLabel lblStatus;
    private final JLabel lblEmoji;
    private final JLabel lblText;

    private final JTextField txtEmoji;
    private final JTextField txtText;

    private final JButton btnAddStatus;
    private JTable tbStatus;

    private final AutomaticStatusManager manager;

    public StatusConfigView(){
        super("DCAutomaticStatus - Config");
        setSize(400, 230);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        addWindowListener(this);

        this.manager = AutomaticStatusManager.getInstance();

        this.lblUpdateTime = new JLabel("Tempo para atualizar o status:");
        lblUpdateTime.setBounds(10, 5, 175, 20);

        add(lblUpdateTime);

        this.spnTime = new JSpinner();
        spnTime.setBounds(10, 25, 70, 20);

        add(spnTime);

        this.cbTimeType = new JComboBox<>(new String[]{"Segundos", "Minutos", "Horas", "Dias"});
        cbTimeType.setBounds(85, 25, 90, 20);

        add(cbTimeType);

        this.lblStatus = new JLabel("Adicionar status");
        lblStatus.setBounds(10, 45, 100, 20);

        add(lblStatus);

        this.lblEmoji = new JLabel("Emoji:");
        lblEmoji.setBounds(10, 61, 35, 20);

        add(lblEmoji);

        this.txtEmoji = new JTextField(1);
        txtEmoji.setBounds(50, 63, 35, 20);

        add(txtEmoji);

        this.lblText = new JLabel("Texto:");
        lblText.setBounds(90, 61, 35, 20);

        add(lblText);

        this.txtText = new JTextField(128);
        txtText.setBounds(130, 63, 130, 20);

        add(txtText);

        this.btnAddStatus = new JButton("Adicionar");
        btnAddStatus.setBounds(10, 85, 95, 20);

        add(btnAddStatus);

        final List<Status> statusList = manager.getStatus();

        this.tbStatus = new JTable(new StatusTableModel(statusList));

        final JScrollPane scroll = new JScrollPane(tbStatus);
        scroll.setVisible(true);

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        panel.setBounds(10, 110, 365, 75);
        btnAddStatus.addActionListener(new ButtonAddStatusEvent(txtText, txtEmoji, tbStatus));

        add(panel);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO: save config
        int value = (int) spnTime.getValue();
        TimeUnit timeType = TimeUnit.SECONDS;

        if(value <= 0){
            value = 15;
            JOptionPane.showMessageDialog(null, "O tempo para atualizar o status por padrão é definido para 15 segundos, o valor selecionado não pode ser menor e nem igual a zero.", "DCAutomaticStatus", JOptionPane.WARNING_MESSAGE);
        }else{
            final String selectedItem = String.valueOf(cbTimeType.getSelectedItem());

            switch(selectedItem){
                case "Segundos":
                    timeType = TimeUnit.SECONDS;
                    break;
                case "Minutos":
                    timeType = TimeUnit.MINUTES;
                    break;
                case "Horas":
                    timeType = TimeUnit.HOURS;
                    break;
                case "Dias":
                    timeType = TimeUnit.DAYS;
                    break;
            }
        }

        StatusConfig.UPDATE_TIME = value;
        StatusConfig.TIME_TYPE = timeType;

        if(StatusConfig.UPDATE_TIME != StatusConfig.UPDATE_TIME_BEFORE || StatusConfig.TIME_TYPE != StatusConfig.TIME_TYPE_BEFORE){
            //TODO: reload update status
            StatusConfig.UPDATE_TIME_BEFORE = StatusConfig.UPDATE_TIME;
            StatusConfig.TIME_TYPE_BEFORE = StatusConfig.TIME_TYPE;

            if(!manager.isRunning())
                return;
            manager.stop();
            manager.start();

            JOptionPane.showMessageDialog(null, "O tempo foi alterado e o status automático reiniciado!");
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO: load config

        int selectedIndex = 0;

        switch(StatusConfig.TIME_TYPE){
            case SECONDS:
                selectedIndex = 0;
                break;
            case MINUTES:
                selectedIndex = 1;
                break;
            case HOURS:
                selectedIndex = 2;
                break;
            case DAYS:
                selectedIndex = 3;
                break;
        }

        spnTime.setValue(StatusConfig.UPDATE_TIME);
        cbTimeType.setSelectedIndex(selectedIndex);
    }

    @Override
    public void windowClosing(WindowEvent e) {}

}