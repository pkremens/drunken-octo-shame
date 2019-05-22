package org.jboss.qe.kremilek.view;

import org.jboss.qe.kremilek.data.DonutDataProducer;
import org.primefaces.model.chart.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class DonutView {

    @Inject
    DonutDataProducer donutDataProducer;

    private DonutChartModel donutModel;

    @Named
    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    @PostConstruct
    public void fetchStaticDonutData() {
        donutModel = donutDataProducer.getDonutChartModel();
    }
}
