package org.jboss.qe.kremilek.data;

import org.primefaces.model.chart.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Singleton
public class DonutDataProducer {
    @Inject
    private Logger log;

    public DonutChartModel donutChartModel;

    @PostConstruct
    private void fetchStaticDonutData() {
        log.info("Creating a Donut");
        donutChartModel = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Brand 1", 150);
        circle1.put("Brand 2", 400);
        circle1.put("Brand 3", 200);
        circle1.put("Brand 4", 10);
        donutChartModel.addCircle(circle1);

        Map<String, Number> circle2 = new LinkedHashMap<String, Number>();
        circle2.put("Brand 1", 540);
        circle2.put("Brand 2", 125);
        circle2.put("Brand 3", 702);
        circle2.put("Brand 4", 421);
        donutChartModel.addCircle(circle2);

        Map<String, Number> circle3 = new LinkedHashMap<String, Number>();
        circle3.put("Brand 1", 40);
        circle3.put("Brand 2", 325);
        circle3.put("Brand 3", 402);
        circle3.put("Brand 4", 421);
        donutChartModel.addCircle(circle3);

        donutChartModel.setTitle("Custom Options");
        donutChartModel.setLegendPosition("e");
        donutChartModel.setSliceMargin(5);
        donutChartModel.setShowDataLabels(true);
        donutChartModel.setDataFormat("value");
        donutChartModel.setShadow(false);
    }
}
