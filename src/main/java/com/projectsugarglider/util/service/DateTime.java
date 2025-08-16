package com.projectsugarglider.util.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateTime {

    private static final DateTimeFormatter YYYYMMDDHHMM = 
    DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private static final DateTimeFormatter YYYYMMDD =
        DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    public OffsetDateTime utcTime (){
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    public OffsetDateTime kstTime(OffsetDateTime dateTime){
        return dateTime
            .atZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .toOffsetDateTime();
    }

    public OffsetDateTime kstNow(){
        return OffsetDateTime.now(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .toOffsetDateTime();
    }

    public OffsetDateTime kstPlusDays(long days) {
        return kstNow().plusDays(days);
    }

    /*
     * OffsetDateTime 날짜값을 
     * 
     * - String year  %04d
     * - String month %02d
     * - String day   %02d
     * - String time  %02d00
     * 
     * 으로 바꿔주는 함수
     */
    public String[] toYYYYMMDDHHMM(OffsetDateTime dt){
        String y = String.format("%04d", dt.getYear());
        String m = String.format("%02d", dt.getMonthValue());
        String d = String.format("%02d", dt.getDayOfMonth());
        String t = String.format("%02d00", dt.getHour());
        return new String[]{y, m, d, t};
    }

    /*
     * String 날짜값을 OffsetDateTime으로 바꿔주는 함수
     * KST기준YYYYMMDDHHMM으로 바뀜
     */
    public OffsetDateTime parseKst(String date, String time) {
    return LocalDateTime
        .parse(date + time, YYYYMMDDHHMM)
        .atZone(KST)
        .toOffsetDateTime();
    }

    public int timeYear(OffsetDateTime dateTime) {
        return dateTime.getYear();
    }

    public int lastYear(OffsetDateTime dateTime) {
        return dateTime.minusYears(1).getYear();
    }

    public int timeMonth(OffsetDateTime dateTime) {
        return dateTime.getMonthValue();
    }

    public int timeDay(OffsetDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    public Date timeDate(OffsetDateTime dateTime){
        return Date.from(dateTime.toInstant());
    }
    
    /**
     * 이전주 금요일(월요일 시작 주 기준)을 KST 기준 YYYYMMDD 문자열로 반환
     */
    public String previousWeekFridayYyyyMmDd() {
        LocalDate todayKst = kstNow().toLocalDate();
        LocalDate currentWeekMonday = todayKst.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate previousWeekFriday = currentWeekMonday.minusDays(3); // 월요일 - 3일 = 이전주 금요일
        return previousWeekFriday.format(YYYYMMDD);
    }
    public String previousMonthFridayYyyyMmDd() {
        LocalDate todayKst = kstNow().toLocalDate();
        LocalDate currentWeekMonday = todayKst.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate previousWeekFriday = currentWeekMonday.minusDays(31); // 월요일 - 31일 = 4주전 금요일
        return previousWeekFriday.format(YYYYMMDD);
    }

}
