package com;


//commons-cli-1.3.1
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        System.out.println("Prepare load testing result file & samples .......");
        com.LotterySystemTest lotterySystemTest = new com.LotterySystemTest();

        Properties properties = new Properties();


        try{


            InputStream input = new FileInputStream("config.properties");

            if(input == null){
                System.out.println("Unable to find......." + "config.properties");
            }
            else {

                properties.load(input);

                System.out.println("名單位置：" + properties.getProperty("all_user_File_Path"));
                System.out.println("抽籤樣本位置：" + properties.getProperty("ballotResltCSV_File_location"));
                System.out.println("統計樣本匯出位置：" + properties.getProperty("result_count_CSV_Path"));

                //Load all name list from register.csv
                ArrayList<RegisterModel> registerModels = lotterySystemTest.GetRegister_from_csv(properties.getProperty("all_user_File_Path"));
                System.out.println("參與抽籤人數：" + registerModels.size());

                //Load all ballot result from csv files
                ArrayList<ResultModel> resultModels = lotterySystemTest.assemble_Ballot_Result(properties.getProperty("ballotResltCSV_File_location"));
                System.out.println("抽籤總樣本數：" +  resultModels.size());

                //進入統計程序
                System.out.println("...");
                System.out.println("開始進行統計.......");

                ArrayList<CaculateResultModel> crm = lotterySystemTest.ballot_Result_Count( registerModels, resultModels);

                //匯出ＣＳＶ檔
                lotterySystemTest.convertResultToCSV(crm, properties.getProperty("result_count_CSV_Path"));

            }


        }catch (Exception e){

        }

    }
}
