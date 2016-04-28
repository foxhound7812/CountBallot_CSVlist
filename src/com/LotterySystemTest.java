package com;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import sun.jvm.hotspot.asm.Register;
import sun.jvm.hotspot.jdi.IntegerTypeImpl;
import sun.jvm.hotspot.tools.SysPropsDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by foxhound7812 on 2016/4/14.
 */
public class LotterySystemTest implements ILotterySystemTest{

    @Override
    public ArrayList<RegisterModel> GetRegister_from_csv(String filePath){

        try{

            ICsvBeanReader getRegisterFile = new CsvBeanReader(new InputStreamReader(new FileInputStream(filePath), "unicode"), CsvPreference.EXCEL_PREFERENCE);

            final String[] header = getRegisterFile.getHeader(true);

            RegisterModel registerModel;   //register Object Container

            //建立一個ArrayList承接CSV上所人抽籤名單
            ArrayList<RegisterModel> all_Register_List = new ArrayList<RegisterModel>();

            //單筆insert to ArrayList
            while((registerModel = getRegisterFile.read(RegisterModel.class, header)) != null){

                all_Register_List.add(registerModel);

            }

            //Close I/O
            getRegisterFile.close();

            return all_Register_List;

        }catch (Exception e){

            System.out.println(e.getMessage());
            return null;

        }
    }

    //get count all "Priority Household" list - 優先戶類別列表
    @Override
    public ArrayList<RegisterModel> get_All_PriorityHousehold( ArrayList<RegisterModel> r){

        //建立一個ArrayList承接優先戶名單
        ArrayList<RegisterModel> _priorityHousehold = new ArrayList<RegisterModel>();

        for(int i = 0; i < r.size(); i++){

            if(r.get(i).getRType().contains("1")){
                _priorityHousehold.add(r.get(i));
            }

        }

        return _priorityHousehold;

    }

    //get count all "Good-neighborliness HouseHold" list - 睦鄰戶類別列表
    @Override
    public ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold(ArrayList<RegisterModel> r){

        //建立一個ArrayList承接睦鄰戶名單
        ArrayList<RegisterModel> _good__neighborlinessHouseHold = new ArrayList<RegisterModel>();

        for(int i = 0; i < r.size(); i++){

            if(r.get(i).getRType().contains("2")){
                _good__neighborlinessHouseHold.add(r.get(i));
            }

        }

        return _good__neighborlinessHouseHold;
    }

    //get count all "Youth HouseHold" list - 青年戶類別列表
    @Override
    public ArrayList<RegisterModel> get_All_YouthHouseHold(ArrayList<RegisterModel> r){

        //建立一個ArrayList承接青年戶名單
        ArrayList<RegisterModel> _youthHouseHold = new ArrayList<RegisterModel>();

        for(int i = 0; i < r.size(); i++){

            if(r.get(i).getRType().contains("3")){
                _youthHouseHold.add(r.get(i));
            }

        }

        return _youthHouseHold;
    }

    //get count all "non-Youth HouseHold" list - 非青年戶類別列表
    @Override
    public ArrayList<RegisterModel> get_All_NonYouthHouseHold(ArrayList<RegisterModel> r){

        //建立一個ArrayList承接非青年戶名單
        ArrayList<RegisterModel> _nonYouthHouseHold = new ArrayList<RegisterModel>();

        for(int i = 0; i < r.size(); i++){

            if(r.get(i).getRType().contains("4")){
                _nonYouthHouseHold.add(r.get(i));
            }

        }

        return _nonYouthHouseHold;

    }

    @Override
    public ArrayList<RegisterModel> get_All_over41PriorityHousehold(ArrayList<RegisterModel> get_All_PriorityHousehold){

        ArrayList<RegisterModel> _over41PriorityHousehold = new ArrayList<>();

        for(int i = 0; i < get_All_PriorityHousehold.size(); i++){
            if((get_All_PriorityHousehold.get(i).getScore().contains("1"))&&Integer.valueOf(get_All_PriorityHousehold.get(i).getAge()) >= 41){
                _over41PriorityHousehold.add(get_All_PriorityHousehold.get(i));
            }
        }

        return _over41PriorityHousehold;

    }

    @Override
    public ArrayList<RegisterModel> get_All_under41PriorityHousehold(ArrayList<RegisterModel> get_All_PriorityHousehold){
        ArrayList<RegisterModel> _under41PriorityHousehold = new ArrayList<>();

        for(int i = 0; i < get_All_PriorityHousehold.size(); i++){
            if((get_All_PriorityHousehold.get(i).getScore().contains("1"))&&(Integer.valueOf(get_All_PriorityHousehold.get(i).getAge()) < 41) ){
                _under41PriorityHousehold.add(get_All_PriorityHousehold.get(i));
            }
        }

        return _under41PriorityHousehold;
    }

    @Override
    public ArrayList<RegisterModel> get_All_over41Good_neighborlinessHouseHold(ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold){
        ArrayList<RegisterModel> _over41Good_neighborliness = new ArrayList<>();

        for(int i = 0; i < get_All_Good_neighborlinessHouseHold.size(); i++){
            if(Integer.valueOf(get_All_Good_neighborlinessHouseHold.get(i).getAge()) >= 41){
                _over41Good_neighborliness.add(get_All_Good_neighborlinessHouseHold.get(i));
            }
        }

        return _over41Good_neighborliness;
    }

    @Override
    public ArrayList<RegisterModel> get_All_under41Good_neighborlinessHouseHold(ArrayList<RegisterModel> get_All_Good_neighborlinessHouseHold){
        ArrayList<RegisterModel> _under41Good_neighborliness = new ArrayList<>();

        for(int i = 0; i < get_All_Good_neighborlinessHouseHold.size(); i++){
            if(Integer.valueOf(get_All_Good_neighborlinessHouseHold.get(i).getAge()) < 41){
                _under41Good_neighborliness.add(get_All_Good_neighborlinessHouseHold.get(i));
            }
        }

        return _under41Good_neighborliness;

    }

    //優先戶分數1且是青年者中簽機率
    @Override
    public double expected_PriorityYouthHouseholds(ArrayList<RegisterModel> _priorityHouseHold,
                                                  ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                   ArrayList<RegisterModel> _youthHouseHold,
                                                   int expectedPHCount, int expectedYHCount){

        //宣告分類
        ArrayList<RegisterModel> _score1_under41 = new ArrayList<>(); //得1分且小於41歲的群組
        ArrayList<RegisterModel> _score1         = new ArrayList<>(); //得1分的群組
        ArrayList<RegisterModel> _score2         = new ArrayList<>(); //得2分的群組
        ArrayList<RegisterModel> _score3         = new ArrayList<>(); //得3分的群組

        //分類
        for(int i = 0; i < _priorityHouseHold.size(); i++){

            if(_priorityHouseHold.get(i).getScore().contains("1")){

                if(Integer.valueOf(_priorityHouseHold.get(i).getAge())< 41){
                    _score1_under41.add(_priorityHouseHold.get(i));
                }

                _score1.add(_priorityHouseHold.get(i));

            }else if(_priorityHouseHold.get(i).getScore().contains("2")){
                _score2.add(_priorityHouseHold.get(i));
            }else if(_priorityHouseHold.get(i).getScore().contains("3")){
                _score3.add(_priorityHouseHold.get(i));
            }

        }

        //按照比例估算 優先戶1分者 >=41歲的 Ｘ人 <41歲的 Ｙ人
        double _over41priorityHouseHoldtakeShot  = ((double)get_All_over41PriorityHousehold(_priorityHouseHold).size() * 50)/142;
        double _under41priorityHouseHoldtakeShot = ((double)get_All_under41PriorityHousehold(_priorityHouseHold).size() * 50)/142;

        //預估備取之進入青年戶之優先戶 -> 所有41歲以下的扣掉待在正取名單的人
        int _under41priorityHouseHoldNoLuck = _score1_under41.size() - (int)_under41priorityHouseHoldtakeShot;

        //按照比例估算 睦鄰戶 >= 41歲的 X人 < 41歲的 Ｙ人
        double _over41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        double _under41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;

        //預估備取之進入青年戶之睦鄰戶
        int _under41Good_neighborlinessHouseHoldNoLuck = get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size() - (int)_under41Good_neighborlinessHouseHoldtakeShot;

        //預估進入青年戶名單的之人數
        int youth_List = _under41priorityHouseHoldNoLuck + _under41Good_neighborlinessHouseHoldNoLuck + _youthHouseHold.size();


//        System.out.println("得1分且小於41歲的群組: " + _score1_under41.size());
//        System.out.println("得1分者: " + _score1.size());
//        System.out.println("得2分者: " + _score2.size());
//        System.out.println("得3分者: " + _score3.size());

        /****************************************計算機率***********************************************/

        //優先戶中籤 => 142人抽出50人 => 中籤率 50/142 約等於 0.35％
        double expected_priority = (double)50/_score1.size();

        //青年戶中籤機率 => 低於41歲的優先戶跑去青年戶 + 青年戶的人數人數
        double expected_youth = ((double)165)/youth_List;

//        System.out.println("In expected_PriorityYouthHouseholds().... _score1.size -> " + _score1.size());
//        System.out.println("In expected_PriorityYouthHouseholds().... expected_priority -> " + expected_priority);

        /***優先戶分數1且是青年者中籤機率 (“1”分優先戶中獎後不青年戶就不可能中)
         *
         *  按照日勝生的抽籤規則,優先戶分數1且是青年者中籤機率 :
         *
         *  優先戶分數1抽中 青年戶沒抽中
         *  優先戶分數1備取（沒中）青年戶抽中
         *
         *  故唯一不可能發生的就是兩次都中籤
         *
         *  故須扣掉兩次都抽中
         * */

        //扣掉都不中
        double full_expected = ((double)1)-((1-expected_priority)*(1-expected_youth));
        /****************************************計算機率***********************************************/

        System.out.println("====================");
        System.out.println("預估備取之進入非青年戶之睦鄰戶 : " + _under41Good_neighborlinessHouseHoldNoLuck);
        System.out.println("預估備取之進入非青年戶之優先戶   :" + _under41priorityHouseHoldNoLuck);
        System.out.println("預估進入非青年戶名單的之人數   : " + youth_List);
        System.out.println("====================");

        return full_expected;
    }

    @Override
    //優先戶分數1且是非青年者中籤機率
    public double expected_PriorityNonYouthHouseholds(ArrayList<RegisterModel> _priorityHouseHold,
                                                     ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                     ArrayList<RegisterModel> _nonYouthHouseHold,
                                                     int expectedPHCount, int expectedYHCount){
        //宣告分類
        ArrayList<RegisterModel> _score1_over41 = new ArrayList<>(); //得1分且大於41歲的群組
        ArrayList<RegisterModel> _score1 = new ArrayList<>(); //得1分的群組
        ArrayList<RegisterModel> _score2 = new ArrayList<>(); //得2分的群組
        ArrayList<RegisterModel> _score3 = new ArrayList<>(); //得3分的群組

        //分類
        for(int i = 0; i < _priorityHouseHold.size(); i++){

            if(_priorityHouseHold.get(i).getScore().contains("1")){

                if(Integer.valueOf(_priorityHouseHold.get(i).getAge())>= 41){
                    _score1_over41.add(_priorityHouseHold.get(i));
                }

                _score1.add(_priorityHouseHold.get(i));

            }else if(_priorityHouseHold.get(i).getScore().contains("2")){
                _score2.add(_priorityHouseHold.get(i));
            }else if(_priorityHouseHold.get(i).getScore().contains("3")){
                _score3.add(_priorityHouseHold.get(i));
            }

        }

        //按照比例估算 優先戶1分者 >=41歲的 Ｘ人 <41歲的 Ｙ人
        double _over41priorityHouseHoldtakeShot  = ((double)get_All_over41PriorityHousehold(_priorityHouseHold).size() * 50)/142;
        //int _under41priorityHouseHoldtakeShot = ((double)get_All_under41PriorityHousehold(_priorityHouseHold).size() * 50)/142;


        //預估備取之進入青年戶之優先戶
        int _over41priorityHouseHoldNoLuck = _score1_over41.size() - (int) _over41priorityHouseHoldtakeShot;

        //按照比例估算 睦鄰戶 >= 41歲的 X人 < 41歲的 Ｙ人
        double _over41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        //int _under41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;

        //預估備取之進入非青年戶之睦鄰戶
        int _over41Good_neighborlinessHouseHoldNoLuck = get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size() - (int)_over41Good_neighborlinessHouseHoldtakeShot;

        //預估進入非青年戶名單的之人數
        int nonYouth_List = _over41priorityHouseHoldNoLuck + _over41Good_neighborlinessHouseHoldNoLuck + _nonYouthHouseHold.size();

//        System.out.println("得1分且大於等於41歲的群組: " + _score1_over41.size());
//        System.out.println("得1分者: " + _score1.size());
//        System.out.println("得2分者: " + _score2.size());
//        System.out.println("得3分者: " + _score3.size());

        /****************************************計算期望值***********************************************/
        //優先戶中籤 => 142人同時抽出50人 => 中籤率 50/142 約等於 0.35％
        double expected_priority = (double)50/_score1.size();

        //非青年戶中籤機率 => 高於41歲的沒中跑去青年戶 + 青年戶的人數人數
        double expected_nonYouth = ((double)30)/nonYouth_List;

        /***優先戶分數1且是青年者中籤機率 (“1”分優先戶中獎後不青年戶就不可能中)
         *
         *  按照日勝生的抽籤規則,優先戶分數1且是非青年者中籤機率 :
         *
         *  優先戶分數1抽中 非青年戶沒去抽
         *  優先戶分數1備取（沒中）非青年戶抽中
         *
         *  故唯一不可能發生的就是兩次都中籤
         *
         *  故須扣掉兩次都抽中
         * */

        //扣掉都不中
        double full_expected = ((double)1) - ((1-expected_priority)*(1-expected_nonYouth));
        /****************************************計算期望值***********************************************/

        System.out.println("====================");
        System.out.println("預估備取之進入非青年戶之睦鄰戶 : " + _over41Good_neighborlinessHouseHoldNoLuck);
        System.out.println("預估備取之進入非青年戶之優先戶   :" + _over41priorityHouseHoldNoLuck);
        System.out.println("預估進入非青年戶名單的之人數   : " + nonYouth_List);
        System.out.println("====================");


        return full_expected;
    }

    //睦鄰戶且是青年者中籤期望值
    @Override
    public double expected_Good_neighborlinessHouseHold_Youth(ArrayList<RegisterModel> _priorityHouseHold,
                                                             ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                             ArrayList<RegisterModel> _youthHouseHold,
                                                             int expectedGDNHCount, int expectedYHCount){

        //按照比例估算 睦鄰戶 >= 41歲的 X人 < 41歲的 Ｙ人
        double _over41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        double _under41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        //預估備取之進入青年戶之睦鄰戶
        int _under41Good_neighborlinessHouseHoldNoLuck = get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size() - (int)_under41Good_neighborlinessHouseHoldtakeShot;

        //按照比例估算 優先戶1分者 >=41歲的 Ｘ人 <41歲的 Ｙ人
        double _over41priorityHouseHoldtakeShot  = ((double)get_All_over41PriorityHousehold(_priorityHouseHold).size() * 50)/142;
        double _under41priorityHouseHoldtakeShot = ((double)get_All_under41PriorityHousehold(_priorityHouseHold).size() * 50)/142;

        //預估備取之進入青年戶之優先戶 -> 所有41歲以下的扣掉待在正取名單的人
        int _under41priorityHouseHoldNoLuck = get_All_under41PriorityHousehold(_priorityHouseHold).size() - (int)_under41priorityHouseHoldtakeShot;

        //預估進入青年戶名單的之人數
        int youth_List = _under41priorityHouseHoldNoLuck + _under41Good_neighborlinessHouseHoldNoLuck + _youthHouseHold.size();
        /****************************************計算期望值***********************************************/
        //睦鄰戶中籤 => 85人同時抽出15人 => 中籤率 15/85 約等於 0.17％
        double expected_priority = (double)15/_good_neighborlinessHouseHold.size();

        double expected_Youth = (double)165/youth_List;

        //扣掉不中
        //double full_expected = ((double)1)-(expected_priority*expected_Youth) - ((1-expected_priority)*(1-expected_Youth));
        double full_expected = ((double)1) - ((1-expected_priority)*(1-expected_Youth));
        /****************************************計算期望值***********************************************/
        System.out.println("====================");
        System.out.println("預估備取之進入青年戶之睦鄰戶 : " + _under41Good_neighborlinessHouseHoldNoLuck);
        System.out.println("預估備取之進入青年戶之優先戶   :" + _under41priorityHouseHoldNoLuck);
        System.out.println("預估進入青年戶名單的之人數   : " + youth_List);
        System.out.println("====================");

        return full_expected;

    }

    @Override
    //睦鄰戶且是非青年者中籤期望值
    public double expected_Good_neighborlinessHouseHold_NonYouth(ArrayList<RegisterModel> _priorityHouseHold,
                                                         ArrayList<RegisterModel> _good_neighborlinessHouseHold,
                                                         ArrayList<RegisterModel> _nonYouthHouseHold,
                                                         int expectedGDNHCount, int expectedYHCount){
        //按照比例估算 睦鄰戶 >= 41歲的 X人 < 41歲的 Ｙ人
        double _over41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        double _under41Good_neighborlinessHouseHoldtakeShot = ((double)get_All_under41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size()*15)/85;
        //預估備取之進入非青年戶之睦鄰戶
        int _over41Good_neighborlinessHouseHoldNoLuck = get_All_over41Good_neighborlinessHouseHold(_good_neighborlinessHouseHold).size() - (int)_over41Good_neighborlinessHouseHoldtakeShot;

        //按照比例估算 優先戶1分者 >=41歲的 Ｘ人 <41歲的 Ｙ人
        double _over41priorityHouseHoldtakeShot  = ((double)get_All_over41PriorityHousehold(_priorityHouseHold).size() * 50)/142;
        double _under41priorityHouseHoldtakeShot = ((double)get_All_under41PriorityHousehold(_priorityHouseHold).size() * 50)/142;

        //預估備取之進入非青年戶之優先戶 -> 所有41歲以下的扣掉待在正取名單的人
        int _over41priorityHouseHoldNoLuck = get_All_over41PriorityHousehold(_priorityHouseHold).size() - (int)_over41priorityHouseHoldtakeShot;

        //預估進入非青年戶名單的之人數
        int nonYouth_List = _over41priorityHouseHoldNoLuck + _over41Good_neighborlinessHouseHoldNoLuck + _nonYouthHouseHold.size();

        /****************************************計算期望值***********************************************/
        //睦鄰戶中籤 => 85人同時抽出15人 => 中籤率 15/85 約等於 0.17％
        double expected_priority = (double)15/_good_neighborlinessHouseHold.size();

        double expected_nonYouth = (double)30/nonYouth_List;

        //全部的可能 - (都不中)
        //double full_expected = ((double)1) - (expected_priority*expected_nonYouth) - ((1-expected_priority)*(1-expected_nonYouth));
        double full_expected = ((double)1) - ((1-expected_priority)*(1-expected_nonYouth));

        /****************************************計算期望值***********************************************/

        System.out.println("====================");
        System.out.println("預估備取之進入非青年戶之睦鄰戶 : " + _over41Good_neighborlinessHouseHoldNoLuck);
        System.out.println("預估備取之進入非青年戶之優先戶   :" + _over41priorityHouseHoldNoLuck);
        System.out.println("預估進入非青年戶名單的之人數   : " + nonYouth_List);
        System.out.println("====================");
        return full_expected;

    }

    @Override
    //集合所有抽籤筆數內容
    public ArrayList<ResultModel> assemble_Ballot_Result(String filePath){

        try{

            File aLR_Samples_Path     = new File(filePath);
            String[] containFileNames = aLR_Samples_Path.list();

            //建立一個ArrayList承接所有抽籤筆數之內容
            ArrayList<ResultModel> aLR_Result_List = new ArrayList<ResultModel>();

            for(String csvfileName:containFileNames){
                ICsvBeanReader getCSVFile = new CsvBeanReader(new InputStreamReader(new FileInputStream(filePath + csvfileName), "unicode"), CsvPreference.EXCEL_PREFERENCE);
                final String[] header = getCSVFile.getHeader(true);

                //declare lottery result model
                ResultModel resultModel;

                //單筆insert to ArrayList
                while((resultModel = getCSVFile.read(ResultModel.class, header)) != null){

                    aLR_Result_List.add(resultModel);

                }

                //Close I/O
                getCSVFile.close();

            }

            return aLR_Result_List;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in assemble_Lottery_Result().......");
            return null;
        }

    }

    @Override
    public ArrayList<CaculateResultModel> ballot_Result_Count(ArrayList<RegisterModel> all_Register_List, ArrayList<ResultModel> aLR_Result_List){

        try{

            int count = 0;

            //建一個ArrayList紀錄所有中籤者之中籤次數資料
            ArrayList<CaculateResultModel> crm_all_Result = new ArrayList<>();

            //5000人名單的for
            for(int i = 0; i < all_Register_List.size(); i++){

                //合併後抽籤結果(假設5000次抽籤每次抽300人, 會有1500000筆資料)
                for(int j = 0; j < aLR_Result_List.size(); j++){

                    if(aLR_Result_List.get(j).getIDNumber().contains(all_Register_List.get(i).getIDNumber())){
                        count++;
                    }

                }

                float probability_init = (float) count/5000;
                float probability = 100*probability_init;

                CaculateResultModel crm = new CaculateResultModel();

                crm.setRid(all_Register_List.get(i).getRid());
                crm.setEid(all_Register_List.get(i).getEid());
                crm.setRsid(all_Register_List.get(i).getRsid());
                crm.setRstatus(all_Register_List.get(i).getRstatus());
                crm.setIDNumber(all_Register_List.get(i).getIDNumber());
                crm.setRName(all_Register_List.get(i).getRName());
                crm.setRType(all_Register_List.get(i).getRType());
                crm.setScore(all_Register_List.get(i).getScore());
                crm.setAge(all_Register_List.get(i).getAge());
                crm.setCount(""+ count +"");
                crm.setProbability(""+ probability +"%");
                crm_all_Result.add(crm);
                count = 0;

            }

            for(int k = 0; k < crm_all_Result.size(); k++){
                System.out.println("Name: "+ crm_all_Result.get(k).getRName() +" ID: "+ crm_all_Result.get(k).getIDNumber() +"" + " count: "+ crm_all_Result.get(k).getCount() +"" + " Probability : " + crm_all_Result.get(k).getProbability() +"");
            }

            return crm_all_Result;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in ballot_Result_Count()...");

            return null;
        }
    }

    @Override
    public void convertResultToCSV(ArrayList<CaculateResultModel> result, String resultSavePath){

        //Delimiter used in CSV file
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        //CSV file header
        String FILE_HEADER = "rid,eid,rsid,rstatus,IDNumber,RName,RType,score,age,count,probability";

        OutputStreamWriter fileWriter = null;

        try{
            FileOutputStream fileStream = new FileOutputStream(new File(resultSavePath));
            fileWriter = new OutputStreamWriter(fileStream,"UTF-8");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a result list to the CSV file
            for(CaculateResultModel crm:result){

                fileWriter.append(String.valueOf(crm.getRid()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getEid()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getRsid()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getRstatus()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getIDNumber()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getRName()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getRType()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getScore()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getAge()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getCount()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(crm.getProbability()));
                fileWriter.append(NEW_LINE_SEPARATOR);

            }


            System.out.println("result file was created successfully !!!");



        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in convertResultToCSV()......");
        }
        finally {

            try{

                fileWriter.flush();
                fileWriter.close();

            }catch (IOException e){
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }

    }

}
