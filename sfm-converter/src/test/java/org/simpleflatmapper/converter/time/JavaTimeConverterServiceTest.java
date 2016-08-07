package org.simpleflatmapper.converter.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.fail;
import static org.simpleflatmapper.converter.ConverterServiceTest.testConverter;


public class JavaTimeConverterServiceTest {

    @Test
    public void testJavaTimeToDate() throws Exception {
        long time = System.currentTimeMillis();
        final Date date = new Date(time);
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        testConverter(localDateTime, date);
        testConverter(localDateTime.toLocalTime(), date);
        testConverter(localDateTime.toLocalDate(), trunc(date));
        testConverter(date.toInstant(), date);
        testConverter(zonedDateTime, date);
        testConverter(zonedDateTime.toOffsetDateTime(), date);
        testConverter(zonedDateTime.toOffsetDateTime().toOffsetTime(), date);
        testConverter(YearMonth.of(2016, Month.FEBRUARY),
                new SimpleDateFormat("yyyyMMdd").parse("20160201"));
        testConverter(Year.of(2016),
                new SimpleDateFormat("yyyyMMdd").parse("20160101"));

    }

    @Test
    public void testDateToJavaTime() throws Exception {
        long time = System.currentTimeMillis();
        final Date date = new Date(time);
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        testConverter(date, localDateTime);
        testConverter(date, localDateTime.toLocalTime());
        testConverter(trunc(date), localDateTime.toLocalDate());
        testConverter(date, date.toInstant());
        testConverter(date, zonedDateTime);
        testConverter(date, zonedDateTime.toOffsetDateTime());
        testConverter(date, zonedDateTime.toOffsetDateTime().toOffsetTime());
        testConverter(new SimpleDateFormat("yyyyMMdd").parse("20160201"),
                YearMonth.of(2016, Month.FEBRUARY)
                );
        testConverter(new SimpleDateFormat("yyyyMMdd").parse("20160201"),
                Year.of(2016));

    }


    @Test
    public void testObjectToInstant() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.now();


        testObjectToInstant(null, null);
        testObjectToInstant(instant, instant);
        testObjectToInstant(instant.atZone(zoneId), instant);
        testObjectToInstant(instant.atZone(zoneId).toInstant(), instant);
        testObjectToInstant(instant.atZone(zoneId).toLocalDateTime(), instant);
        testObjectToInstant(instant.atZone(zoneId).toOffsetDateTime(), instant);
        testObjectToInstant(Date.from(instant), instant);
        testObjectToInstant(instant.toEpochMilli(), instant);

        try {
            testObjectToInstant("a string", instant);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToInstant(Object in, Instant out) throws Exception {
        testConverter(in, out, Object.class, Instant.class);
    }

    @Test
    public void testObjectToLocalDate() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now(zoneId);

        testObjectToLocalDate(null, null);
        testObjectToLocalDate(localDate, localDate);
        testObjectToLocalDate(localDate.atStartOfDay().atZone(zoneId), localDate);
        testObjectToLocalDate(localDate.atStartOfDay().atZone(zoneId).toInstant(), localDate);
        testObjectToLocalDate(localDate.atStartOfDay().atZone(zoneId).toOffsetDateTime(), localDate);
        testObjectToLocalDate(localDate.atStartOfDay(), localDate);
        testObjectToLocalDate(Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant()), localDate);

        try {
            testObjectToLocalDate("a string", localDate);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToLocalDate(Object in, LocalDate out) throws Exception {
        testConverter(in, out, Object.class, LocalDate.class);
    }

    @Test
    public void testObjectToLocalDateTime() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);

        testObjectToLocalDateTime(null, null);
        testObjectToLocalDateTime(localDateTime, localDateTime);

        testObjectToLocalDateTime(localDateTime.atZone(zoneId), localDateTime);
        testObjectToLocalDateTime(localDateTime.atZone(zoneId).toInstant(), localDateTime);
        testObjectToLocalDateTime(localDateTime.atZone(zoneId).toOffsetDateTime(), localDateTime);
        testObjectToLocalDateTime(Date.from(localDateTime.atZone(zoneId).toInstant()), localDateTime);

        try {
            testObjectToLocalDateTime("a string", localDateTime);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToLocalDateTime(Object in, LocalDateTime out) throws Exception {
        testConverter(in, out, Object.class, LocalDateTime.class);
    }

    @Test
    public void testObjectToLocalTime() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime localTime = LocalTime.now(zoneId);
        ZoneOffset offset = zoneId.getRules().getOffset(localTime.atDate(LocalDate.now()));

        testObjectToLocalTime(null, null);
        testObjectToLocalTime(localTime, localTime);

        testObjectToLocalTime(localTime.atDate(LocalDate.now()), localTime);
        testObjectToLocalTime(localTime.atDate(LocalDate.now()).atZone(zoneId), localTime);
        testObjectToLocalTime(localTime.atDate(LocalDate.now()).atOffset(offset), localTime);
        testObjectToLocalTime(localTime.atDate(LocalDate.now()).toLocalTime(), localTime);
        testObjectToLocalTime(localTime.atDate(LocalDate.now()).toInstant(offset), localTime);
        testObjectToLocalTime(Date.from(localTime.atDate(LocalDate.now()).toInstant(offset)), localTime);

        try {
            testObjectToLocalTime("a string", localTime);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToLocalTime(Object in, LocalTime out) throws Exception {
        testConverter(in, out, Object.class, LocalTime.class);
    }

    @Test
    public void testObjectToOffsetDateTime() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime offsetDateTime = OffsetDateTime.now(zoneId);

        testObjectToOffsetDateTime(null, null);
        testObjectToOffsetDateTime(offsetDateTime, offsetDateTime);

        testObjectToOffsetDateTime(offsetDateTime.toLocalDateTime(), offsetDateTime);
        testObjectToOffsetDateTime(offsetDateTime.toInstant(), offsetDateTime);
        testObjectToOffsetDateTime(offsetDateTime.atZoneSameInstant(zoneId), offsetDateTime);
        testObjectToOffsetDateTime(Date.from(offsetDateTime.toInstant()), offsetDateTime);

        try {
            testObjectToOffsetDateTime("a string", offsetDateTime);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToOffsetDateTime(Object in, OffsetDateTime out) throws Exception {
        testConverter(in, out, Object.class, OffsetDateTime.class);
    }

    @Test
    public void testObjectToOffsetTime() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        OffsetTime offsetTime = OffsetTime.now(zoneId);

        testObjectToOffsetTime(null, null);
        testObjectToOffsetTime(offsetTime, offsetTime);

        testObjectToOffsetTime(offsetTime.atDate(LocalDate.now()), offsetTime);
        testObjectToOffsetTime(offsetTime.atDate(LocalDate.now()).atZoneSimilarLocal(zoneId), offsetTime);
        testObjectToOffsetTime(offsetTime.atDate(LocalDate.now()).toLocalDateTime(), offsetTime);
        testObjectToOffsetTime(offsetTime.atDate(LocalDate.now()).toLocalTime(), offsetTime);
        testObjectToOffsetTime(offsetTime.atDate(LocalDate.now()).toInstant(), offsetTime);
        testObjectToOffsetTime(Date.from(offsetTime.atDate(LocalDate.now()).toInstant()), offsetTime);

        try {
            testObjectToOffsetTime("a string", offsetTime);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToOffsetTime(Object in, OffsetTime out) throws Exception {
        testConverter(in, out, Object.class, OffsetTime.class);
    }

    @Test
    public void testObjectToYear() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        Date now = new Date();
        Year year = Year.from(now.toInstant().atZone(zoneId));

        testObjectToYear(null, null);
        testObjectToYear(year, year);
        testObjectToYear(year.atMonth(6).atEndOfMonth().atTime(1, 0).atZone(zoneId), year);
        testObjectToYear(now, year);
        testObjectToYear(year.getValue(), year);

        try {
            testObjectToYear("a string", year);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToYear(Object in, Year out) throws Exception {
        testConverter(in, out, Object.class, Year.class);
    }

    @Test
    public void testObjectToYearMonth() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        Date now = new Date();
        YearMonth yearMonth = YearMonth.from(now.toInstant().atZone(zoneId));

        testObjectToYearMonth(null, null);
        testObjectToYearMonth(yearMonth, yearMonth);

        testObjectToYearMonth(yearMonth.atEndOfMonth().atTime(1, 0).atZone(zoneId), yearMonth);
        testObjectToYearMonth(now, yearMonth);
        testObjectToYearMonth(yearMonth.getYear() * 100 + yearMonth.getMonthValue(), yearMonth);

        try {
            testObjectToYearMonth("a string", yearMonth);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToYearMonth(Object in, YearMonth out) throws Exception {
        testConverter(in, out, Object.class, YearMonth.class);
    }

    @Test
    public void testObjectToZonedDateTime() throws Exception {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

        testObjectToZonedDateTime(null, null);
        testObjectToZonedDateTime(zonedDateTime, zonedDateTime);

        testObjectToZonedDateTime(zonedDateTime.toInstant(), zonedDateTime);
        testObjectToZonedDateTime(zonedDateTime.toLocalDateTime(), zonedDateTime);
        testObjectToZonedDateTime(zonedDateTime.toOffsetDateTime(), zonedDateTime);
        testObjectToZonedDateTime(Date.from(zonedDateTime.toInstant()), zonedDateTime);

        try {
            testObjectToZonedDateTime("a string", zonedDateTime);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testObjectToZonedDateTime(Object in, ZonedDateTime out) throws Exception {
        testConverter(in, out, Object.class, ZonedDateTime.class);
    }

    private Date trunc(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        return cal.getTime();
    }

}