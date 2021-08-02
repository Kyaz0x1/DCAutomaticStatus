package net.kyaz0x1.dcautomaticstatus.task;

import net.kyaz0x1.dcautomaticstatus.api.DiscordAPI;
import net.kyaz0x1.dcautomaticstatus.api.except.StatusErrorException;
import net.kyaz0x1.dcautomaticstatus.api.models.Status;
import net.kyaz0x1.dcautomaticstatus.config.StatusConfig;
import net.kyaz0x1.dcautomaticstatus.manager.AutomaticStatusManager;
import net.kyaz0x1.dcautomaticstatus.view.AutomaticStatusView;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class StatusUpdaterTask extends TimerTask {

    private final DiscordAPI api;
    private final AtomicInteger count;

    private final AutomaticStatusManager manager;

    public StatusUpdaterTask(){
        this.api = DiscordAPI.getInstance();
        this.count = new AtomicInteger(0);

        this.manager = AutomaticStatusManager.getInstance();
    }

    @Override
    public void run() {
        final List<Status> statusList = manager.getStatus();

        if(count.get() >= statusList.size()){
            count.set(0);
        }

        final Status status = statusList.get(count.getAndIncrement());

        try {
            api.updateStatus(status);
            StatusConfig.TOTAL_UPDATES.incrementAndGet();
            AutomaticStatusView.lblUpdateCount.setText(getUpdateCount());

            System.out.printf("[DCAutomaticStatus] Status atualizado com sucesso! (+%d)\n", StatusConfig.TOTAL_UPDATES.get());
        }catch(StatusErrorException e) {
            manager.stop();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar o status!\nErro: " + e.getMessage() + "\nPor esse motivo, o status automático foi pausado.", "DCAutomaticStatus", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getUpdateCount(){
        char per = 's';

        switch(StatusConfig.TIME_TYPE){
            case SECONDS:
                per = 's';
                break;
            case MINUTES:
                per = 'm';
                break;
            case HOURS:
                per = 'h';
                break;
            case DAYS:
                per = 'd';
                break;
        }

        return StatusConfig.TOTAL_UPDATES.get() + "/" + per;
    }

}