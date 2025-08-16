package com.projectsugarglider;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projectsugarglider.kca.api.EntptypeDivApi;
import com.projectsugarglider.kca.api.GoodTotalDivApi;
import com.projectsugarglider.kca.api.GoodUnitDivApi;


@SpringBootTest
public class KcaTest {

    @Autowired
    private EntptypeDivApi entp;

    @Autowired
    private GoodTotalDivApi total;

    @Autowired
    private GoodUnitDivApi unit;


    @Test
    void apiTest(){
        entp.entpTypeCall();
        total.totalDivCall();
        unit.unitDivCall();
    }
}
