package net.kyaz0x1.dcautomaticstatus.manager;

import net.kyaz0x1.dcautomaticstatus.api.models.Status;
import net.kyaz0x1.dcautomaticstatus.config.StatusConfig;
import net.kyaz0x1.dcautomaticstatus.task.StatusUpdaterTask;
import net.kyaz0x1.dcautomaticstatus.view.AutomaticStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public final class AutomaticStatusManager {

    private static AutomaticStatusManager INSTANCE;

    private final List<Status> status;
    private Timer timer;

    private boolean running;

    private AutomaticStatusManager(){
        this.status = new ArrayList<>();
        this.timer = new Timer();

        this.running = false;
    }

    public void start(){
        running = true;

        AutomaticStatusView.btnStart.setEnabled(false);
        AutomaticStatusView.btnStop.setEnabled(true);

        timer = new Timer();

        final long time = StatusConfig.TIME_TYPE.toMillis(StatusConfig.UPDATE_TIME);
        timer.scheduleAtFixedRate(new StatusUpdaterTask(), time, time);
    }

    public void stop(){
        running = false;
        timer.cancel();
        timer = null;

        AutomaticStatusView.btnStart.setEnabled(true);
        AutomaticStatusView.btnStop.setEnabled(false);

        StatusConfig.TOTAL_UPDATES.set(0);
    }

    public boolean isRunning() {
        return running;
    }

    public void addStatus(Status status){
        this.status.add(status);
    }

    public List<Status> getStatus(){
        return status;
    }

    public static AutomaticStatusManager getInstance(){
        if(INSTANCE == null){
            synchronized(AutomaticStatusManager.class){
                if(INSTANCE == null){
                    INSTANCE = new AutomaticStatusManager();
                }
            }
        }
        return INSTANCE;
    }

}