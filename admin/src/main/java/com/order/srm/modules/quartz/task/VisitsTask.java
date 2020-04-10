package com.order.srm.modules.quartz.task;

import com.order.srm.modules.monitor.service.VisitsService;
import org.springframework.stereotype.Component;

/**
 *
 * @date 2018-12-25
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run() {
        visitsService.save();
    }
}
