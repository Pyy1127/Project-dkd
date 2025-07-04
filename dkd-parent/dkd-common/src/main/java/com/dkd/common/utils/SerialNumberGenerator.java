package com.dkd.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成每日递增序号的工具类
 */
public class SerialNumberGenerator {
    // 存储每个日期对应的计数器
    private static final ConcurrentHashMap<String, AtomicInteger> COUNTERS = new ConcurrentHashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 生成基于当天日期的唯一序号，格式：yyyyMMdd + 四位递增数字
     * 
     * @param prefix 前缀（可选）
     * @return 生成的序号
     */
    public static String generateDailySerial(String prefix) {
        LocalDate today = LocalDate.now();
        String dateKey = today.format(DATE_FORMATTER);
        
        // 获取或创建当天的计数器
        AtomicInteger counter = COUNTERS.computeIfAbsent(dateKey, k -> new AtomicInteger(1));
        
        // 生成序号并返回
        int serialNumber = counter.getAndIncrement();
        return prefix + dateKey + String.format("%04d", serialNumber);
    }

    /**
     * 重置指定日期的计数器
     * 
     * @param dateKey 日期键（格式：yyyyMMdd）
     */
    public static void resetCounter(String dateKey) {
        COUNTERS.put(dateKey, new AtomicInteger(1));
    }

    /**
     * 清除所有计数器
     */
    public static void clearAllCounters() {
        COUNTERS.clear();
    }
}