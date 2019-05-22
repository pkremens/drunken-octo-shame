package org.jboss.qe.kremilek.view;

import org.jboss.qe.kremilek.data.DonutDataProducer;
import org.primefaces.model.chart.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Named;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class DonutView {
    private DonutChartModel donutModel;

    @Named
    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    @PostConstruct
    public void fetchStaticDonutData() {
        donutModel = DonutDataProducer.getDonutChartModel();
    }
}
