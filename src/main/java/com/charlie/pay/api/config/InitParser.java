package com.charlie.pay.api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charlie.pay.api.model.RoomInfo;
import com.charlie.pay.api.model.UserInfo;
import com.charlie.pay.api.repository.RoomInfoRepository;
import com.charlie.pay.api.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitParser {

    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Autowired
    private RoomInfoRepository roomInfoRepository;
    
    private List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    
    private List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
    
    private String fileNm = "init.csv";

    @PostConstruct
    public void sampleDataInit(){

    	//User
    	for(long i=1000000001; i<1000000011; i++) {
    		UserInfo userInfo = new UserInfo(i, 100000000);
    		userInfoList.add(userInfo);
    	}
    	userInfoRepository.saveAll(userInfoList);
    	
    	//Room And User
        CSVParser csvParser = null;
        try {
        	FileInputStream fis = new FileInputStream(fileNm);
            csvParser = CSVParser.parse(fis, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withFirstRecordAsHeader());
            if(csvParser != null) {
            	parseData(csvParser.getRecords());
            	roomInfoRepository.saveAll(roomInfoList);
            }
        }catch(IOException e) {
            log.error("sampleDataInit.IOException - ", e.getMessage());
        }finally {
        	if(csvParser != null && !csvParser.isClosed()){
                try {csvParser.close();} catch (IOException e) {log.error("sampleDataInit.CSVParser.close.IOException - ", e.getMessage());}
            }
		}
    }
    
    private void parseData(List<CSVRecord> csvRecordList) throws IOException{
		for(CSVRecord csvRecord : csvRecordList){
			RoomInfo roomInfo = new RoomInfo(csvRecord.get(0), Long.valueOf(csvRecord.get(1))); 
			roomInfoList.add(roomInfo);
		}
    }

}
