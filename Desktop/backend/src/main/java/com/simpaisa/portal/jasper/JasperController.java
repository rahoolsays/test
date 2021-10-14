package com.simpaisa.portal.jasper;

import com.simpaisa.portal.entity.mysql.transaction.Transaction;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.io.File;
//import java.util.logging.FileHandler;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;

@RequestMapping("report")
@CrossOrigin(origins = "*")
@RestController
public class JasperController {

//  Logger logger = LoggerFactory.getLogger(JasperController.class);
//    @Autowired
//    private EmployeeRepository repository;
    @Autowired
    private ReportService service;
//
//    @RequestMapping("/")
//    public String index()  throws IOException {
////        logger.trace("A TRACE Message");
////        logger.debug("A DEBUG Message");
////        logger.info("An INFO Message");
////        logger.warn("A WARN Message");
////        logger.error("An ERROR Message");
//
//        Log mylog=new Log("log.txt");
//        mylog.loger2.setLevel(Level.WARNING);
//        mylog.loger2.info("asdn");
//        mylog.loger2.warning("kajsd");
//        mylog.loger2.severe("aklsdmn");
//        mylog.fh.close();
//        mylog.loger2.severe("aklsdmn");
//
//        Log mylog2=new Log("log.txt");
//        mylog2.loger2.setLevel(Level.WARNING);
//        mylog2.loger2.info("rahul");
//        mylog2.loger2.warning("kumar");
//        mylog2.loger2.severe("chugh");
//        mylog2.fh.close();
//        return "Howdy! Check out the Logs to see the output...";
//    }

//    @GetMapping("/getEmployees")
//    public List<Employee> getEmployees(){
//        return repository.findAll();
//    }

    @PostMapping("/{format}")
    public String generateReport(@PathVariable String format, @RequestParam("title") String title, @RequestBody List<Transaction> body, HttpServletResponse response) throws JRException, IOException {
        return service.exportReport(format,title,body,response);
    }


//    @PostMapping("/{format}")
//    public void generateReport(@RequestBody LinkedHashMap<String, Object> body) throws JRException {
//
//        Map<String,Object> summary= (Map<String, Object>) body.get("summary");
//        List<Map<String,Object>> customer= (List<Map<String, Object>>) summary.get("customer");
//        List<Map<String,Object>> merchant= (List<Map<String, Object>>) summary.get("merchant");
//
//        List<Object> list2=new ArrayList<>();
//        List<SummaryEmployee> list=new ArrayList<>();
//        for (int i = 0; i < merchant.size(); i++) {
//            SummaryEmployee e=new SummaryEmployee();
//            e.setMercamount((Double) merchant.get(i).get("amount"));
//            e.setMerctext((String) merchant.get(i).get("displayName"));
//            e.setCustamount(null);
//            e.setCusttext(null);
//            list.add(e);
//            list2.add(e);
//        }
////        System.out.println(list);
//
//
//        List<SummaryCustomer> listCust=new ArrayList<>();
//        for (int i = 0; i < customer.size(); i++) {
//            SummaryCustomer e=new SummaryCustomer();
//            e.setCustamount((Double) customer.get(i).get("amount"));
//            e.setCusttext((String) customer.get(i).get("displayName"));
//            e.setMercamount(null);
//            e.setMerctext(null);
//            listCust.add(e);
//            list2.add(e);
//        }
////        System.out.println(listCust);
//        System.out.println(list2);
//        Summary summary1=new Summary();
//        summary1.setC(listCust);
//        summary1.setE(list);
////        System.out.println(summary1);
////        Employee e=new Employee();
////        e.setPerc(15.25);
////        e.setTax("kjasdb");
////        Employee e1=new Employee();
////        e1.setPerc(89.25);
////        e1.setTax("kjasdb");
////        list.add(e);
////        list.add(e1);
////        System.out.println("Employees :: "+list);
//        ReportGenerator rg = new ReportGenerator();
//        rg.generatePdfReport(list2);
//    }


    @PostMapping("/summary/{format}")
    public String generateSummaryReport(@PathVariable String format,
    @RequestParam("merchantId") String merchantId, @RequestParam("merchantName") String merchantName, @RequestParam("date") String date,
    @RequestParam("time") String time,@RequestParam("from") String from,@RequestParam("to") String to,@RequestBody LinkedHashMap<String, Object> body, HttpServletResponse response) throws JRException, IOException {


        Map<String,Object> merchantDetails= (Map<String, Object>) body.get("merchantDetails");
        Map<String,Object> merchantBalance= (Map<String, Object>) body.get("merchantBalance");
        Map<String,Object> disbursementCount= (Map<String, Object>) body.get("disbursementCount");
        List<Map<String,Object>> totalSuccessfulDisbursement= (List<Map<String, Object>>) body.get("totalSuccessfulDisbursement");
        List<Map<String,Object>> totalReversalDisbursement= (List<Map<String, Object>>) body.get("totalReversalDisbursement");

        Double openingBalance=service.convertToDouble(merchantBalance.get("total"));
        Double fundsReceived=0.0;
        Double balanceAvailable=service.convertToDouble(merchantBalance.get("available"));

        int tdi=0;
        tdi= (int) disbursementCount.get("count");

        Double tdii=service.convertToDouble(disbursementCount.get("totalAmount"));

        int tsdi=0;
        tsdi= (int) totalSuccessfulDisbursement.get(0).get("count");
        Double tsdii=service.convertToDouble(totalSuccessfulDisbursement.get(0).get("amount"));

        int ric=0;
        ric= (int) totalReversalDisbursement.get(0).get("count");
        Double ri=service.convertToDouble(totalReversalDisbursement.get(0).get("amount"));

        String website= (String) merchantDetails.get("website");
        if(website==null || website.equals("null")){
            website="N/A";
        }

        String logo= (String) merchantDetails.get("logo");
        if(logo==null || logo.equals("null")){
            logo="https://pic.onlinewebfonts.com/svg/img_529940.png";

        }

        Map<String,Object> summary= (Map<String, Object>) body.get("summary");
        List<Map<String,Object>> reversal= (List<Map<String, Object>>) body.get("ReversalFeeDetails");
        List<Map<String,Object>> customer= (List<Map<String, Object>>) summary.get("customer");
        List<Map<String,Object>> merchant= (List<Map<String, Object>>) summary.get("merchant");
        Double rfa=0.0;
        if(reversal.size()>0){
            rfa=service.convertToDouble(reversal.get(0).get("amount"));
        }



        Double mercSum=0.0;
        Double custSum=0.0;
        Double mercAmountSum=0.0;
        Double custAmountSum=0.0;
        Double grossDeduction=0.0;
        List<Object> list2=new ArrayList<>();
        List<SummaryEmployee> list=new ArrayList<>();
        List<SummaryCustomer> listCust=new ArrayList<>();

        SummaryEmployee f=new SummaryEmployee();
        f.setMercamount(null);
        f.setCustamount(null);
        f.setCusttext(null);
        f.setCustamount(null);
        list2.add(f);

        if(merchant.size()>0 || customer.size()>0){
            int merSize=0;
            int cusSize=0;
            int index=0;
            if(merchant.size()>0){
                merSize=merchant.size();
            }
            if(customer.size()>0){
                cusSize=customer.size();
            }
            if (merSize>cusSize){
                index=merSize;
            }else{
                index=cusSize;
            }
            System.out.println("index :: "+index);

            for (int i = 0; i < index; i++) {
                SummaryEmployee e=new SummaryEmployee();

                if(merchant.size()>i){
                    e.setMercamount(service.convertToDouble(merchant.get(i).get("total")));

                    String perc="";
                    Double percentage=0.0;
                    if((int) merchant.get(i).get("percentile")==1){
                        perc="%";
                    }
                    if (merchant.get(i).get("value") instanceof Integer )
                    {
                        Integer integer = (Integer) merchant.get(i).get("value") ;
                        Double d= new Double(integer);//first way
                        percentage=d;

                    }else{
                        percentage=(Double) merchant.get(i).get("value");
                    }
                    e.setMerctext(((String) merchant.get(i).get("displayName"))+" @ "+String.valueOf(percentage)+" "+String.valueOf(perc)+"");

                }else{
                    e.setMercamount(null);
                    e.setCusttext(null);
                }


                if(customer.size()>i) {
                    e.setCustamount(service.convertToDouble(customer.get(i).get("total")));

                    String perc2 = "";
                    Double percentage2 = 0.0;
                    if ((int) customer.get(i).get("percentile") == 1) {
                        perc2 = "%";
                    }
                    if (customer.get(i).get("value") instanceof Integer) {
                        Integer integer = (Integer) customer.get(i).get("value");
                        Double d = new Double(integer);//first way
                        percentage2 = d;

                    } else {
                        percentage2 = (Double) customer.get(i).get("value");
                    }
                    e.setCusttext(((String) customer.get(i).get("displayName")) + " @ " + String.valueOf(percentage2) + " " + String.valueOf(perc2) + "");
                }else{
                    e.setCusttext(null);
                    e.setCustamount(null);
                }

//                list.add(e);
                list2.add(e);
            }
        }

        System.out.println("List :: "+list2);

        if(merchant.size()>0){
            for (int i = 0; i < merchant.size(); i++) {
//                SummaryEmployee e=new SummaryEmployee();
//                e.setMercamount(service.convertToDouble(merchant.get(i).get("total")));
//
//                String perc="";
//                Double percentage=0.0;
//                if((int) merchant.get(i).get("percentile")==1){
//                    perc="%";
//                }
//                if (merchant.get(i).get("value") instanceof Integer )
//                {
//                    Integer integer = (Integer) merchant.get(i).get("value") ;
//                    Double d= new Double(integer);//first way
//                    percentage=d;
//
//                }else{
//                    percentage=(Double) merchant.get(i).get("value");
//                }
//                e.setMerctext(((String) merchant.get(i).get("displayName"))+" @ "+String.valueOf(percentage)+" "+String.valueOf(perc)+" (AOM)");


                if(merchant.get(i).get("total")==null){
                    mercSum=mercSum+0.0;
                }else{
                    if (merchant.get(i).get("total") instanceof Integer )
                    {
                        Integer integer = (Integer) merchant.get(i).get("total") ;
                        Double d= new Double(integer);//first way
                        mercSum=mercSum+ d;

                    }else{
                        mercSum=mercSum+ (Double) merchant.get(i).get("total");
                    }
                }

                if(merchant.get(i).get("amount")==null){
                    mercAmountSum=mercAmountSum+0.0;
                }else{
                    if (merchant.get(i).get("amount") instanceof Integer )
                    {
                        Integer integer = (Integer) merchant.get(i).get("amount") ;
                        Double d= new Double(integer);//first way
                        mercAmountSum=mercAmountSum+ d;

                    }else{
                        mercAmountSum=mercAmountSum+ (Double) merchant.get(i).get("amount");
                    }
                }


//                e.setCustamount(null);
//                e.setCusttext(null);
//                list.add(e);
//                list2.add(e);
            }
        }

        if(customer.size()>0){
            for (int i = 0; i < customer.size(); i++) {
//                SummaryCustomer e=new SummaryCustomer();
//                e.setCustamount(service.convertToDouble(customer.get(i).get("total")));
//
//
//
//                String perc="";
//                Double percentage=0.0;
//                if((int) customer.get(i).get("percentile")==1){
//                    perc="%";
//                }
//                if (customer.get(i).get("value") instanceof Integer )
//                {
//                    Integer integer = (Integer) customer.get(i).get("value") ;
//                    Double d= new Double(integer);//first way
//                    percentage=d;
//
//                }else{
//                    percentage=(Double) customer.get(i).get("value");
//                }
//                e.setCusttext(((String) customer.get(i).get("displayName"))+" @ "+String.valueOf(percentage)+" "+String.valueOf(perc)+" (AOC)");



                if(customer.get(i).get("total")==null){
                    custSum=custSum+0.0;
                }else{
                    if (customer.get(i).get("total") instanceof Integer )
                    {
                        Integer integer = (Integer) customer.get(i).get("total") ;
                        Double d= new Double(integer);//first way
                        custSum=custSum+ d;

                    }else{
                        custSum=custSum+ (Double) customer.get(i).get("total");
                    }
                }

                if(customer.get(i).get("amount")==null){
                    custAmountSum=custAmountSum+0.0;
                }else{
                    if (customer.get(i).get("amount") instanceof Integer )
                    {
                        Integer integer = (Integer) customer.get(i).get("amount") ;
                        Double d= new Double(integer);//first way
                        custAmountSum=custAmountSum+ d;

                    }else{
                        custAmountSum=custAmountSum+ (Double) customer.get(i).get("amount");
                    }
                }



//                e.setMercamount(null);
//                e.setMerctext(null);
//                listCust.add(e);
//                list2.add(e);
            }
        }


        System.out.println(list2);
        grossDeduction=mercAmountSum+mercSum+custAmountSum+custSum;

        return service.exportSummaryReport(format,merchantId,merchantName,website,date,time,openingBalance, fundsReceived,balanceAvailable,(float) tdi,(float) tsdi,tdii,tsdii,service.convertToDouble(ric),ri,rfa,logo,list2,mercSum,custSum,from,to,grossDeduction,response);
    }


//    @PostMapping("/summary/{format}")
//    public String generateSummaryReport(@PathVariable String format, @RequestParam("merchantId") String merchantId, @RequestParam("merchantName") String merchantName, @RequestParam("website") String website, @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("openingBalance") String openingBalance, @RequestParam("fundsReceived") String fundsReceived, @RequestParam("balanceAvailable") String balanceAvailable, @RequestParam("tdi") String tdi, @RequestParam("tsdi") String tsdi, @RequestParam("tdii") String tdii, @RequestParam("tsdii") String tsdii, @RequestParam("tsfa") String tsfa, @RequestParam("gd") String gd, @RequestParam("ric") String ric, @RequestParam("ri") String ri,  @RequestParam("logo") String logo, @RequestBody LinkedHashMap<String, Object> body, HttpServletResponse response) throws JRException, IOException {
//
//        if(website==null || website.equals("null")){
//            website="N/A";
//        }
//        if(openingBalance==null || openingBalance.equals("null")){
//            openingBalance="0";
//        }
//        if(fundsReceived==null || fundsReceived.equals("null")){
//            fundsReceived="0";
//        }
//        if(balanceAvailable==null || balanceAvailable.equals("null")){
//            balanceAvailable="0";
//        }
//        if(tdi==null || tdi.equals("null")){
//            tdi="0";
//        }
//        if(tsdi==null || tsdi.equals("null")){
//            tsdi="0";
//        }
//        if(tdii==null || tdii.equals("null")){
//            tdii="0";
//        }
//        if(tsdii==null || tsdii.equals("null")){
//            tsdii="0";
//        }
//        if(tsfa==null || tsfa.equals("null")){
//            tsfa="0";
//        }
//        if(gd==null || gd.equals("null")){
//            gd="0";
//        }
//        if(ric==null || ric.equals("null")){
//            ric="0";
//        }
//        if(ri==null || ri.equals("null")){
//            ri="0";
//        }
//        if(logo==null || logo.equals("null")){
//            logo="https://pic.onlinewebfonts.com/svg/img_529940.png";
//
//        }
//
//        Map<String,Object> summary= (Map<String, Object>) body.get("summary");
//        List<Map<String,Object>> reversal= (List<Map<String, Object>>) body.get("ReversalFeeDetails");
//        List<Map<String,Object>> customer= (List<Map<String, Object>>) summary.get("customer");
//        List<Map<String,Object>> merchant= (List<Map<String, Object>>) summary.get("merchant");
//
//
//        Double rfa=0.0;
//        try{
//            if (reversal.get(0).get("amount") instanceof Integer )
//            {
//                Integer integer = (Integer) reversal.get(0).get("amount") ;
//                Double d= new Double(integer);//first way
//                rfa=d;
//
//            }else{
//                rfa=(Double) reversal.get(0).get("amount");
//            }
//        }
//        catch (Exception ex){
//
//        }
//
//
//        Double mercSum=0.0;
//        Double custSum=0.0;
//        List<Object> list2=new ArrayList<>();
//        List<SummaryEmployee> list=new ArrayList<>();
//        for (int i = 0; i < merchant.size(); i++) {
//            SummaryEmployee e=new SummaryEmployee();
//            if (merchant.get(i).get("amount") instanceof Integer )
//            {
//                Integer integer = (Integer) merchant.get(i).get("amount") ;
//                Double d= new Double(integer);//first way
//                e.setMercamount(d);
//
//            }else{
//                e.setMercamount((Double) merchant.get(i).get("amount"));
//            }
//
//
//            String perc="";
//            Double percentage=0.0;
//            if((int) merchant.get(i).get("percentile")==1){
//                perc="%";
//            }
//            if (merchant.get(i).get("value") instanceof Integer )
//            {
//                Integer integer = (Integer) merchant.get(i).get("value") ;
//                Double d= new Double(integer);//first way
//                percentage=d;
//
//            }else{
//                percentage=(Double) merchant.get(i).get("value");
//            }
//            e.setMerctext(((String) merchant.get(i).get("displayName"))+" @ "+String.valueOf(percentage)+" "+String.valueOf(perc)+" (AOM)");
//
//
//            if(merchant.get(i).get("amount")==null){
//                mercSum=mercSum+0.0;
//            }else{
//                if (merchant.get(i).get("amount") instanceof Integer )
//                {
//                    Integer integer = (Integer) merchant.get(i).get("amount") ;
//                    Double d= new Double(integer);//first way
//                    mercSum=mercSum+ d;
//
//                }else{
//                    mercSum=mercSum+ (Double) merchant.get(i).get("amount");
//                }
//            }
//
//
//            e.setCustamount(null);
//            e.setCusttext(null);
//            list.add(e);
//            list2.add(e);
//        }
//
//        List<SummaryCustomer> listCust=new ArrayList<>();
//        for (int i = 0; i < customer.size(); i++) {
//            SummaryCustomer e=new SummaryCustomer();
//            if (customer.get(i).get("amount") instanceof Integer )
//            {
//                Integer integer = (Integer) customer.get(i).get("amount") ;
//                Double d= new Double(integer);//first way
//                e.setCustamount(d);
//
//            }else{
//                e.setCustamount((Double) customer.get(i).get("amount"));
//            }
//
//
//
//            String perc="";
//            Double percentage=0.0;
//            if((int) customer.get(i).get("percentile")==1){
//                perc="%";
//            }
//            if (customer.get(i).get("value") instanceof Integer )
//            {
//                Integer integer = (Integer) customer.get(i).get("value") ;
//                Double d= new Double(integer);//first way
//                percentage=d;
//
//            }else{
//                percentage=(Double) customer.get(i).get("value");
//            }
//            e.setCusttext(((String) customer.get(i).get("displayName"))+" @ "+String.valueOf(percentage)+" "+String.valueOf(perc)+" (AOC)");
//
//
//
//            if(customer.get(i).get("amount")==null){
//                custSum=custSum+0.0;
//            }else{
//                if (customer.get(i).get("amount") instanceof Integer )
//                {
//                    Integer integer = (Integer) customer.get(i).get("amount") ;
//                    Double d= new Double(integer);//first way
//                    custSum=custSum+ d;
//
//                }else{
//                    custSum=custSum+ (Double) customer.get(i).get("amount");
//                }
//            }
//
//
//            e.setMercamount(null);
//            e.setMerctext(null);
//            listCust.add(e);
//            list2.add(e);
//        }
//
//        return service.exportSummaryReport(format,merchantId,merchantName,website,date,time,Double.parseDouble(openingBalance), Double.parseDouble(fundsReceived),Double.parseDouble(balanceAvailable),Float.parseFloat(tdi),Float.parseFloat(tsdi),Double.parseDouble(tdii),Double.parseDouble(tsdii),Double.parseDouble(tsfa),Double.parseDouble(gd),Double.parseDouble(ric),Double.parseDouble(ri),rfa,logo,list2,mercSum,custSum,response);
//    }
//
//    @PostMapping("/summary/{format}")
//    public String generateSummaryReport(@PathVariable String format, @RequestParam("merchantId") String merchantId, @RequestParam("merchantName") String merchantName, @RequestParam("website") String website, @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("openingBalance") String openingBalance, @RequestParam("fundsReceived") String fundsReceived, @RequestParam("balanceAvailable") String balanceAvailable, @RequestParam("tdi") String tdi, @RequestParam("tsdi") String tsdi, @RequestParam("tdii") String tdii, @RequestParam("tsdii") String tsdii, @RequestParam("taxcust") String taxcust, @RequestParam("procfee") String procfee, @RequestParam("servcharges") String servcharges, @RequestParam("tsfa") String tsfa, @RequestParam("gd") String gd, @RequestParam("ric") String ric, @RequestParam("ri") String ri, @RequestParam("rfa") String rfa, @RequestParam("dr") String dr, @RequestParam("nd") String nd, @RequestParam("nba") String nba, @RequestParam("taxcustName") String taxcustName, @RequestParam("taxcustperc") String taxcustperc, @RequestParam("taxcustName2") String taxcustName2, @RequestParam("taxcustperc2") String taxcustperc2, @RequestParam("taxcust2") String taxcust2, @RequestParam("taxmerName") String taxmerName, @RequestParam("taxmerName2") String taxmerName2, @RequestParam("taxmerperc") String taxmerperc, @RequestParam("taxmercperc2") String taxmercperc2, @RequestParam("perc1") String perc1, @RequestParam("perc2") String perc2, @RequestParam("perc3") String perc3, @RequestParam("perc4") String perc4, @RequestParam("logo") String logo, @RequestBody LinkedHashMap<String, Object> body, HttpServletResponse response) throws JRException, IOException {
//
//        if(website==null || website.equals("null")){
//            website="N/A";
//        }
//        if(openingBalance==null || openingBalance.equals("null")){
//            openingBalance="0";
//        }
//        if(fundsReceived==null || fundsReceived.equals("null")){
//            fundsReceived="0";
//        }
//        if(balanceAvailable==null || balanceAvailable.equals("null")){
//            balanceAvailable="0";
//        }
//        if(tdi==null || tdi.equals("null")){
//            tdi="0";
//        }
//        if(tsdi==null || tsdi.equals("null")){
//            tsdi="0";
//        }
//        if(tdii==null || tdii.equals("null")){
//            tdii="0";
//        }
//        if(tsdii==null || tsdii.equals("null")){
//            tsdii="0";
//        }
//        if(taxcust==null || taxcust.equals("null")){
//            taxcust="0";
//        }
//        if(procfee==null || procfee.equals("null")){
//            procfee="0";
//        }
//        if(servcharges==null || servcharges.equals("null")){
//            tdii="0";
//        }
//        if(tsfa==null || tsfa.equals("null")){
//            tsfa="0";
//        }
//        if(gd==null || gd.equals("null")){
//            gd="0";
//        }
//        if(ric==null || ric.equals("null")){
//            ric="0";
//        }
//        if(ri==null || ri.equals("null")){
//            ri="0";
//        }
//        if(rfa==null || rfa.equals("null")){
//            rfa="0";
//        }
//        if(dr==null || dr.equals("null")){
//            dr="0";
//        }
//        if(nd==null || nd.equals("null")){
//            nd="0";
//        }
//        if(nba==null || nba.equals("null")){
//            nba="0";
//        }
//        if(taxcust2==null || taxcust2.equals("null")){
//            taxcust2="0";
//        }
//        if(perc1.equals("1")){
//            taxcustperc=taxcustperc+"%";
//            System.out.println("in here"+taxcustperc);
//        }
//        if(perc2.equals("1")){
//            taxcustperc2=taxcustperc2+"%";
//        }
//        if(perc3.equals("1")){
//            taxmerperc=taxmerperc+"%";
//        }
//        if(perc4.equals("1")){
//            taxmercperc2=taxmercperc2+"%";
//        }
//        if(logo==null || logo.equals("null")){
//            logo="https://pic.onlinewebfonts.com/svg/img_529940.png";
//
//        }
//
//        Map<String,Object> summary= (Map<String, Object>) body.get("summary");
//        List<Map<String,Object>> customer= (List<Map<String, Object>>) summary.get("customer");
//        List<Map<String,Object>> merchant= (List<Map<String, Object>>) summary.get("merchant");
//
//        Double mercSum=0.0;
//        Double custSum=0.0;
//        List<Object> list2=new ArrayList<>();
//        List<SummaryEmployee> list=new ArrayList<>();
//        for (int i = 0; i < merchant.size(); i++) {
//            SummaryEmployee e=new SummaryEmployee();
//            e.setMercamount((Double) merchant.get(i).get("amount"));
//            e.setMerctext((String) merchant.get(i).get("displayName"));
//            if(merchant.get(i).get("amount")==null){
//                mercSum=mercSum+0.0;
//            }else{
//                mercSum=mercSum+ (Double) merchant.get(i).get("amount");
//            }
//            e.setCustamount(null);
//            e.setCusttext(null);
//            list.add(e);
//            list2.add(e);
//        }
//
//        List<SummaryCustomer> listCust=new ArrayList<>();
//        for (int i = 0; i < customer.size(); i++) {
//            SummaryCustomer e=new SummaryCustomer();
//            e.setCustamount((Double) customer.get(i).get("amount"));
//            e.setCusttext((String) customer.get(i).get("displayName"));
//            if(customer.get(i).get("amount")==null){
//                custSum=custSum+0.0;
//            }else{
//                custSum=custSum+ (Double) customer.get(i).get("amount");
//            }
//            e.setMercamount(null);
//            e.setMerctext(null);
//            listCust.add(e);
//            list2.add(e);
//        }
//
//        return service.exportSummaryReport(format,merchantId,merchantName,website,date,time,Double.parseDouble(openingBalance), Double.parseDouble(fundsReceived),Double.parseDouble(balanceAvailable),Float.parseFloat(tdi),Float.parseFloat(tsdi),Double.parseDouble(tdii),Double.parseDouble(tsdii),Double.parseDouble(taxcust),Double.parseDouble(procfee),Double.parseDouble(servcharges),Double.parseDouble(tsfa),Double.parseDouble(gd),Double.parseDouble(ric),Double.parseDouble(ri),Double.parseDouble(rfa),Double.parseDouble(dr),Double.parseDouble(nd),Double.parseDouble(nba),taxcustName,taxcustperc,taxcustName2,taxcustperc2,Double.parseDouble(taxcust2),taxmerName,taxmerName2,taxmerperc,taxmercperc2,logo,list2,mercSum,custSum,response);
//    }
}