package com.pet.server.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListUtils {

    public static Map<String, List<Map<String, Object>>> toMapList(List<Map<String, Object>> mData, String mKey, boolean isInt){
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
        if(mData.size() > 0){
            for(int i = 0 ; i < mData.size(); i++){
                String key = "";
                if(isInt){
                    key =  String.valueOf((Integer) mData.get(i).get(mKey));
                }else{
                    key = ((String) mData.get(i).get(mKey)).trim()+"";
                }

                if(result.get(key) == null){
                    List<Map<String, Object>> tmpList = new ArrayList<>();
                    tmpList.add(mData.get(i));
                    result.put(key, tmpList);
                }else{
                    result.get(key).add(mData.get(i));
                }
            }
        }
        return result;
    }
}
