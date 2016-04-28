package com;

import sun.jvm.hotspot.asm.Register;

import java.util.ArrayList;

/**
 * Created by foxhound7812 on 2016/4/14.
 * 抽籤系統公平性測試Interface
 */
public interface ILotterySystemTest {

    //get all register from default file
    ArrayList<RegisterModel> GetRegister_from_csv(String filePath);

    //get count all "Priority Household" list
    ArrayList<RegisterModel> get_All_PriorityHousehold( ArrayList<RegisterModel> r);

    //get count all "Good-neighborliness HouseHold" list
    ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold(ArrayList<RegisterModel> r);

    //get count all "Youth HouseHold" list
    ArrayList<RegisterModel> get_All_YouthHouseHold(ArrayList<RegisterModel> r);
    //get count all "non-Youth HouseHold" list
    ArrayList<RegisterModel> get_All_NonYouthHouseHold(ArrayList<RegisterModel> r);
    //大於41歲之優先戶
    ArrayList<RegisterModel> get_All_over41PriorityHousehold(ArrayList<RegisterModel> get_All_PriorityHousehold);
    //小於41歲之優先戶
    ArrayList<RegisterModel> get_All_under41PriorityHousehold(ArrayList<RegisterModel> get_All_PriorityHousehold);
    //大於41歲之睦鄰戶
    ArrayList<RegisterModel> get_All_over41Good_neighborlinessHouseHold(ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold);
    //小於41歲之睦鄰戶
    ArrayList<RegisterModel> get_All_under41Good_neighborlinessHouseHold(ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold);

    //優先戶分數1且是青年者中籤機率
    double expected_PriorityYouthHouseholds(ArrayList<RegisterModel> _priorityHouseHold,
                                           ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                            ArrayList<RegisterModel> _youthHouseHold,
                                            int expectedPHCount, int expectedYHCount);

    //優先戶分數1且是非青年者中籤機率
    double expected_PriorityNonYouthHouseholds(ArrayList<RegisterModel> _priorityHouseHold,
                                              ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                              ArrayList<RegisterModel> _nonYouthHouseHold,
                                              int expectedPHCount, int expectedNYHCount);

    //睦鄰戶且是青年者中籤機率
    double expected_Good_neighborlinessHouseHold_Youth(ArrayList<RegisterModel> _priorityHouseHold,
                                                      ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                       ArrayList<RegisterModel> _youthHouseHold,
                                                       int expectedGDNHCount, int expectedYHCount);

    //睦鄰戶且是非青年者中籤機率
    double expected_Good_neighborlinessHouseHold_NonYouth(ArrayList<RegisterModel> _priorityHouseHold,
                                                      ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                      ArrayList<RegisterModel> _NonYouthHouseHold,
                                                      int expectedGDNHCount, int expectedYHCount);

    //一般戶且是青年者中籤機率

    //一般戶且是非青年者中簽機率

    //睦鄰戶統計中籤次數

    //Assemble all lotttery result from csv
    ArrayList<ResultModel> assemble_Ballot_Result(String filePath);

    //Compare and count all ballot result with all users
    ArrayList<CaculateResultModel> ballot_Result_Count(ArrayList<RegisterModel> all_Register_List, ArrayList<ResultModel> aLR_Result_List);

    //Convert result to CSV file
    void convertResultToCSV(ArrayList<CaculateResultModel> result, String resultSavePath);

}
