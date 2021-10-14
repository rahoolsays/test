package com.simpaisa.portal.service.reporting.monthwise;

import com.simpaisa.portal.entity.mysql.reporting.monthwise.MonthWiseRevenue;
import com.simpaisa.portal.repository.interfaces.MonthWiseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class MonthWiseService {
    @Autowired
    private MonthWiseRepository monthWiseRepository;

    private static final String[] MONTH_ARRAY = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December", ""};
    public List<MonthWiseRevenue> fetchMonthWiseRevenue(long merchantId, int numberOfMonths){

        List<MonthWiseRevenue> monthWiseRevenues = null;

        List<MonthWiseRevenue> result = new ArrayList<MonthWiseRevenue>();
//        result=null;
        try{
            monthWiseRevenues = monthWiseRepository.fetchMonthWiseRevenue(merchantId, numberOfMonths);

            if(monthWiseRevenues!=null && monthWiseRevenues.size()>0) {
                Calendar c = Calendar.getInstance();
                String currentMonth = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);


                int currentMonthNumber = 0;
                for (int i = 0; i < MONTH_ARRAY.length; i++) {
                    if (currentMonth.toLowerCase().equals(MONTH_ARRAY[i].toLowerCase())) {
                        currentMonthNumber = i;
                        break;
                    }
                }

                int startingIndex = 0;
                startingIndex = Math.abs((currentMonthNumber + 12) - numberOfMonths) % 12;

                int startIndexTemp = startingIndex;
                result = new ArrayList<>();
                int monthWiseDoneIndex = 0;
                int start = 0;
                while (start <= numberOfMonths) {
                    boolean addedToList = false;
                    for (int j = 0; j < monthWiseRevenues.size(); j++) {



                        if (MONTH_ARRAY[(startingIndex + 12) % 12].toLowerCase().equals(monthWiseRevenues.get(j).getMonthName().toLowerCase())) {
                            result.add(monthWiseRevenues.get(j));
                            addedToList = true;
                            monthWiseDoneIndex++;
                            break;
                        }


                    }

                    if (addedToList == false) {
                        MonthWiseRevenue monthWiseRevenue = new MonthWiseRevenue();
                        monthWiseRevenue.setAmount(0.0);
                        monthWiseRevenue.setMonthName(MONTH_ARRAY[(startingIndex + 12) % 12]);
                        result.add(monthWiseRevenue);
                    }

//                    if (monthWiseDoneIndex == monthWiseRevenues.size()) {
//                        break;
//                    }

                    startingIndex++;

                    start++;
                }
            }
            else if(monthWiseRevenues.size()==0){
                    for(int i=numberOfMonths;i>=0;i--){
                        MonthWiseRevenue monthWiseRevenue=new MonthWiseRevenue();
                        Calendar cal =  Calendar.getInstance();
                        cal.add(Calendar.MONTH, -i);
                        String previousMonthYear  = new SimpleDateFormat("MMMMMMMMMMMM").format(cal.getTime());
                        monthWiseRevenue.setMonthName(previousMonthYear);
                        monthWiseRevenue.setAmount(0.00);
                        result.add(monthWiseRevenue);
                    }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public List<MonthWiseRevenue> fetchMonthWiseRevenueALL(int numberOfMonths){

        List<MonthWiseRevenue> monthWiseRevenues = null;

        List<MonthWiseRevenue> result = new ArrayList<MonthWiseRevenue>();
//        result=null;
        try{
            monthWiseRevenues = monthWiseRepository.fetchMonthWiseRevenueALL(numberOfMonths);

            if(monthWiseRevenues!=null && monthWiseRevenues.size()>0) {
                Calendar c = Calendar.getInstance();
                String currentMonth = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);


                int currentMonthNumber = 0;
                for (int i = 0; i < MONTH_ARRAY.length; i++) {
                    if (currentMonth.toLowerCase().equals(MONTH_ARRAY[i].toLowerCase())) {
                        currentMonthNumber = i;
                        break;
                    }
                }

                int startingIndex = 0;
                startingIndex = Math.abs((currentMonthNumber + 12) - numberOfMonths) % 12;

                int startIndexTemp = startingIndex;
                result = new ArrayList<>();
                int monthWiseDoneIndex = 0;
                int start = 0;
                while (start <= numberOfMonths) {
                    boolean addedToList = false;
                    for (int j = 0; j < monthWiseRevenues.size(); j++) {



                        if (MONTH_ARRAY[(startingIndex + 12) % 12].toLowerCase().equals(monthWiseRevenues.get(j).getMonthName().toLowerCase())) {
                            result.add(monthWiseRevenues.get(j));
                            addedToList = true;
                            monthWiseDoneIndex++;
                            break;
                        }


                    }

                    if (addedToList == false) {
                        MonthWiseRevenue monthWiseRevenue = new MonthWiseRevenue();
                        monthWiseRevenue.setAmount(0.0);
                        monthWiseRevenue.setMonthName(MONTH_ARRAY[(startingIndex + 12) % 12]);
                        result.add(monthWiseRevenue);
                    }

//                    if (monthWiseDoneIndex == monthWiseRevenues.size()) {
//                        break;
//                    }

                    startingIndex++;

                    start++;
                }
            }
            else if(monthWiseRevenues.size()==0){
                for(int i=numberOfMonths;i>=0;i--){
                    MonthWiseRevenue monthWiseRevenue=new MonthWiseRevenue();
                    Calendar cal =  Calendar.getInstance();
                    cal.add(Calendar.MONTH, -i);
                    String previousMonthYear  = new SimpleDateFormat("MMMMMMMMMMMM").format(cal.getTime());
                    monthWiseRevenue.setMonthName(previousMonthYear);
                    monthWiseRevenue.setAmount(0.00);
                    result.add(monthWiseRevenue);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
