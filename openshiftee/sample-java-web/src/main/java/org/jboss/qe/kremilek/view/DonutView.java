package org.jboss.qe.kremilek.view;

import org.jboss.qe.kremilek.data.DonutDataProducer;
import org.primefaces.model.chart.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class DonutView {

    @Inject
    private Logger log;

    @Inject
    private DonutDataProducer donutDataProducer;

    private DonutChartModel donutModel;

    @Named
    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    @PostConstruct
    public void fetchStaticDonutData() {
        log.info("Creating a Donut");
        donutModel = donutDataProducer.getDonutChartModel();
    }
}
