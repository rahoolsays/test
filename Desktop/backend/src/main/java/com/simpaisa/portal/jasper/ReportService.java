package com.simpaisa.portal.jasper;


import com.simpaisa.portal.entity.mysql.transaction.Transaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ReportService {

//    @Autowired
//    private EmployeeRepository repository;


    public Double convertToDouble(Object x){
        Double d=0.0;
        if(x==null || x.equals("null")){
            d=0.0;
        }
        else{
            if (x instanceof Integer )
            {
                Integer integer = (Integer) x ;
                d= new Double(integer);//first way

            }else{
                d=(Double) x;
            }
        }
        return d;
    }

    public String exportReport(String reportFormat, String title, List<Transaction> body, HttpServletResponse response) throws JRException, IOException {

        String path = "C:\\Users\\Rahul Kumar\\Desktop\\report";
        List<Transaction> transactions = body;


        //load file and compile it
//        File file = ResourceUtils.getFile("classpath:employees.jrxml");
//        System.out.println(file.getAbsolutePath());

        InputStream summaryStream = getClass().getResourceAsStream("/employees.jrxml");

//        Resource sourceFile = new ClassPathResource("employees.jrxml");


//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(summaryStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", title);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\transactions.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=transactions.pdf;");
        }

        return "report generated in path : " + path;
    }

    public String exportSummaryReport(String reportFormat, String merchantid, String merchantName, String website, String date, String time, Double openingBalance, Double fundsReceived, Double balanceAvailable, Float tdi, Float tsdi, Double tdii, Double tsdii, Double ric, Double ri, Double rfa,  String logo,List<Object> e,Double mercSum,Double custSum,String from,String to,Double grossDeduction, HttpServletResponse response) throws JRException, IOException {

        String path = "C:\\Users\\Rahul Kumar\\Desktop\\report";

        //load file and compile it
//        Map<String, String> transactions = finaly;
//        File file = ResourceUtils.getFile("classpath:summary.jrxml");
        InputStream summaryStream = getClass().getResourceAsStream("/summary.jrxml");

//        Resource sourceFile = new ClassPathResource("employees.jrxml");

//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(summaryStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(e);


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("merchantid", merchantid);
        parameters.put("merchantname", merchantName);
        parameters.put("website", website);
        parameters.put("date", date);
        parameters.put("time", time);

        parameters.put("openingbalance", openingBalance);

        parameters.put("funds", fundsReceived);

        parameters.put("balanceavailable", balanceAvailable);

        parameters.put("tdi", tdi);

        parameters.put("tsdi", tsdi);

        parameters.put("tdii", tdii);

        parameters.put("tsdii", tsdii);

        parameters.put("ric", ric);

        parameters.put("ri", ri);

        parameters.put("rfa", rfa);

        parameters.put("logo", logo);
        parameters.put("custSum", custSum);
        parameters.put("mercSum", mercSum);
        parameters.put("from", from);
        parameters.put("to", to);
        parameters.put("gd", grossDeduction);

        System.out.println(parameters.get("merchantid"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\summary.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=summary.pdf;");
        }

        return "report generated in path : " + path;
    }
//    public String exportSummaryReport(String reportFormat, String merchantid, String merchantName, String website, String date, String time, Double openingBalance, Double fundsReceived, Double balanceAvailable, Float tdi, Float tsdi, Double tdii, Double tsdii, Double tsfa, Double gd, Double ric, Double ri, Double rfa,  String logo,List<Object> e,Double mercSum,Double custSum, HttpServletResponse response) throws JRException, IOException {
//
//        String path = "C:\\Users\\Rahul Kumar\\Desktop\\report";
//        //load file and compile it
////        Map<String, String> transactions = finaly;
//        File file = ResourceUtils.getFile("classpath:summary.jrxml");
//        System.out.println(file.getAbsolutePath());
//
////        Resource sourceFile = new ClassPathResource("employees.jrxml");
//
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(e);
//
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("merchantid", merchantid);
//        parameters.put("merchantname", merchantName);
//        parameters.put("website", website);
//        parameters.put("date", date);
//        parameters.put("time", time);
//
//        parameters.put("openingbalance", openingBalance);
//
//        parameters.put("funds", fundsReceived);
//
//        parameters.put("balanceavailable", balanceAvailable);
//
//        parameters.put("tdi", tdi);
//
//        parameters.put("tsdi", tsdi);
//
//        parameters.put("tdii", tdii);
//
//        parameters.put("tsdii", tsdii);
//
//        parameters.put("tsfa", tsfa);
//
//        parameters.put("gd", gd);
//
//        parameters.put("ric", ric);
//
//        parameters.put("ri", ri);
//
//        parameters.put("rfa", rfa);
//
//        parameters.put("logo", logo);
//        parameters.put("custSum", custSum);
//        parameters.put("mercSum", mercSum);
//
//        System.out.println(parameters.get("merchantid"));
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
//        if (reportFormat.equalsIgnoreCase("html")) {
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\summary.html");
//        }
//        if (reportFormat.equalsIgnoreCase("pdf")) {
////            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
//            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition", "inline; filename=summary.pdf;");
//        }
//
//        return "report generated in path : " + path;
//    }
//
//
//    public String exportSummaryReport(String reportFormat, String merchantid, String merchantName, String website, String date, String time, Double openingBalance, Double fundsReceived, Double balanceAvailable, Float tdi, Float tsdi, Double tdii, Double tsdii, Double taxcust, Double procfee, Double servcharges, Double tsfa, Double gd, Double ric, Double ri, Double rfa, Double dr, Double nd, Double nba, String taxcustName, String taxcustperc, String taxcustName2, String taxcustperc2, Double taxcust2, String taxmerName, String taxmerName2, String taxmerperc, String taxmercperc2, String logo,List<Object> e,Double mercSum,Double custSum, HttpServletResponse response) throws JRException, IOException {
//
//        String path = "C:\\Users\\Rahul Kumar\\Desktop\\report";
//        //load file and compile it
////        Map<String, String> transactions = finaly;
//        File file = ResourceUtils.getFile("classpath:summary.jrxml");
//        System.out.println(file.getAbsolutePath());
//
////        Resource sourceFile = new ClassPathResource("employees.jrxml");
//
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(e);
//
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("merchantid", merchantid);
//        parameters.put("merchantname", merchantName);
//        parameters.put("website", website);
//        parameters.put("date", date);
//        parameters.put("time", time);
//
//            parameters.put("openingbalance", openingBalance);
//
//            parameters.put("funds", fundsReceived);
//
//            parameters.put("balanceavailable", balanceAvailable);
//
//            parameters.put("tdi", tdi);
//
//            parameters.put("tsdi", tsdi);
//
//            parameters.put("tdii", tdii);
//
//            parameters.put("tsdii", tsdii);
//
//            parameters.put("taxcust", taxcust);
//
//            parameters.put("procfee", procfee);
//
//            parameters.put("servcharges", servcharges);
//
//            parameters.put("tsfa", tsfa);
//
//            parameters.put("gd", gd);
//
//            parameters.put("ric", ric);
//
//            parameters.put("ri", ri);
//
//            parameters.put("rfa", rfa);
//
//            parameters.put("dr", dr);
//
//            parameters.put("nd", nd);
//
//            parameters.put("nba", nba);
//
//        parameters.put("taxcustName", taxcustName);
//        parameters.put("taxcustperc", taxcustperc);
//        parameters.put("taxcustName2", taxcustName2);
//        parameters.put("taxcustperc2", taxcustperc2);
//        parameters.put("taxcust2", taxcust2);
//        parameters.put("taxmerName", taxmerName);
//        parameters.put("taxmerName2", taxmerName2);
//        parameters.put("taxmerperc", taxmerperc);
//        parameters.put("taxmercperc2", taxmercperc2);
//        parameters.put("logo", logo);
//        parameters.put("custSum", custSum);
//        parameters.put("mercSum", mercSum);
//
//        System.out.println(parameters.get("merchantid"));
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
//        if (reportFormat.equalsIgnoreCase("html")) {
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\summary.html");
//        }
//        if (reportFormat.equalsIgnoreCase("pdf")) {
////            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");
//            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition", "inline; filename=summary.pdf;");
//        }
//
//        return "report generated in path : " + path;
//    }
}