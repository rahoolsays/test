package com.simpaisa.portal.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.*;

public class ReportGenerator {

    public void generatePdfReport(List<Object> cars) throws JRException {

        String report = "src/main/resources/report2.xml";

        JasperReport jreport = JasperCompileManager.compileReport(report);

        Map<String, Object> params = new HashMap<String, Object>();

        System.out.println(cars);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(cars);


        JasperPrint jprint = JasperFillManager.fillReport(jreport, params, ds);

        JasperExportManager.exportReportToPdfFile(jprint,
                "src/main/resources/report2.pdf");
    }
}