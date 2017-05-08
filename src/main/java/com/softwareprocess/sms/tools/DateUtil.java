package com.softwareprocess.sms.tools;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author yangyue
 */
public class DateUtil {
	/**
	 * 转换日期格式
	 *
	 * @param dateStr
	 *            日期字符串
	 * @param origPattern
	 *            日期字符串的格式
	 * @param destPattern
	 *            要转换的日期格式
	 * @return 转换后的日期字符串
	 */
	public String convertFormat(String dateStr, String origPattern,
			String destPattern) {
		if (dateStr == null) {
			return "";
		}
		SimpleDateFormat origSdf = new SimpleDateFormat(origPattern);
		Date date = null;
		try {
			date = origSdf.parse(dateStr);
		} catch (ParseException e) {
			StringBuilder errMsg = new StringBuilder();
			errMsg.append("Can not convert");
			errMsg.append(dateStr);
			errMsg.append("to Date with");
			errMsg.append(origPattern);
			errMsg.append(".");
			throw new IllegalArgumentException(errMsg.toString(), e);
		}

		SimpleDateFormat destSdf = new SimpleDateFormat(destPattern);

		return destSdf.format(date);
	}

	/**
	 * 时间格式 h:m:s和秒数相加
	 *
	 * @param seconds
	 *            秒数
	 * @param hours
	 *            h:m:s
	 * @return h:m:s
	 */
	public String sum(String seconds, String hours) {
		return sumHour(Second2Hour(seconds), hours);
	}

	/**
	 * 两个时间格式 h:m:s相加
	 *
	 * @param hours1
	 *            h:m:s
	 * @param hours2
	 *            h:m:s
	 * @return h:m:s
	 */
	public String sumHour(String hours1, String hours2) {
		String[] h1 = hours1.split(":");
		String[] h2 = hours2.split(":");
		long add = 0;
		long sec = (Long.parseLong(h1[2]) + Long.parseLong(h2[2])) % 60;
		add = (Long.parseLong(h1[2]) + Long.parseLong(h2[2])) / 60;
		long min = (Long.parseLong(h1[1]) + Long.parseLong(h2[1]) + add) % 60;
		add = (Long.parseLong(h1[1]) + Long.parseLong(h2[1]) + add) / 60;
		long hour = Long.parseLong(h1[0]) + Long.parseLong(h2[0]) + add;
		return hour + ":" + min + ":" + sec;
	}

	/**
	 * 把秒数转换成时间格式 h:m:s
	 *
	 * @param seconds
	 * @return h:m:s
	 */
	public String Second2Hour(String seconds) {
		int s = Integer.parseInt(seconds);
		int hour = s / 3600;
		int min = (s - hour * 3600) / 60;
		int sec = s - hour * 3600 - min * 60;
		return hour + ":" + min + ":" + sec;
	}

	/**
	 * 当前日期加上天数
	 *
	 * @param time
	 *            当前日期
	 * @param pattern
	 *            日期格式
	 * @param day
	 *            加上的天数
	 * @return 当前日期加上天数的日期
	 * @throws Exception
	 */
	public String addDay(String time, String pattern, int day) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(time));
		c.add(Calendar.DATE, day);
		return df.format(c.getTime());
	}

	/**
	 * 当前日期加上分钟数
	 *
	 * @param time
	 *            当前日期
	 * @param pattern
	 *            日期格式
	 * @param day
	 *            加上的天数
	 * @return 当前日期加上分钟数的日期
	 * @throws Exception
	 */
	public String addMinute(String time, String pattern, int day)
			throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(time));
		c.add(Calendar.MINUTE, day);
		return df.format(c.getTime());
	}

	/**
	 * 当前日期加上天数，日期格式为yyyy-MM-dd
	 *
	 * @param date
	 *            当前日期
	 * @param day
	 *            加上的天数
	 * @return 当前日期加上天数的日期
	 * @throws Exception
	 */
	public String getTime(String date, int day) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sf.parse(date));
		c.add(Calendar.DATE, day);
		return sf.format(c.getTime());
	}

	/**
	 * 当前日期加上月数
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月数
	 * @return 当前日期加上月数的日期
	 * @throws Exception
	 */
	public String getTimeMonth(String date, int month) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sf.parse(date));
		c.add(Calendar.MONTH, month);
		return sf.format(c.getTime());
	}

	/**
	 * 日期转换为中文的星期
	 *
	 * @param date
	 *            日期
	 * @return 中文的星期
	 * @throws Exception
	 */
	public String dateToWeekDay(String date) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "星期一");
		map.put("2", "星期二");
		map.put("3", "星期三");
		map.put("4", "星期四");
		map.put("5", "星期五");
		map.put("6", "星期六");
		map.put("0", "星期日");
		return map.get(String.valueOf(sf.parse(date).getDay())).toString();
	}

	/**
	 * 得到两个日期之间的日期列表
	 *
	 * @param beginTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @return 日期列表yyyy-MM-dd
	 * @throws Exception
	 */
	public List<String> getTimeSpan(String beginTime, String endTime)
			throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(sf.parse(beginTime));
		while (!sf.parse(endTime).before(cal.getTime())) {
			list.add(sf.format(cal.getTime()));
			cal.add(cal.DATE, 1);
		}
		return list;
	}

	/**
	 * 得到两个时间之间的小时
	 *
	 * @param beginTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @param strdate
	 *            指定天
	 * @return 小时列表
	 * @throws Exception
	 */
	public List<String> getTimeSpanHour(String beginTime, String endTime,
			String strdate) throws Exception {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		List<String> list = new ArrayList<String>();
		String beginDate = beginTime.substring(0, 8);
		String endDate = endTime.substring(0, 8);
		if (Long.valueOf(strdate) > Long.valueOf(beginDate)) {
			beginTime = strdate + "000000";
		}
		if (Long.valueOf(strdate) < Long.valueOf(endDate)) {
			endTime = strdate + "235959";
		}
		int beginIndex = Integer.parseInt(beginTime.substring(8, 10));
		int endIndex = Integer.parseInt(endTime.substring(8, 10));
		for (int i = beginIndex; i < endIndex; i++) {
			list.add(strdate + decimalFormat.format(i) + "00");
		}
		if (Integer.parseInt(endTime.substring(10)) != 0) {
			list.add(strdate + decimalFormat.format(endIndex) + "00");
		}
		return list;
	}

	/**
	 * 得到当前日期加上或减去月份数的那个月的第一天
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月份数
	 * @return 当前日期加上或减去月份数的那个月的第一天
	 */
	public String getFristDayOfMonth(Date date, int month) {
		Date d = new Date();
		d.setTime(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, month);
		d = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.DAY_OF_MONTH, 1);
		return sf.format(c.getTime());
	}

	/**
	 * 得到当前日期加上或减去月份数的那个月的第一天
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月份数
	 * @return 当前日期加上或减去月份数的那个月的第一天
	 */
	public String getFristDayOfMonth(String date, int month) {
		Date d = new Date();
		try {
			d.setTime(formatDate(date, "yyyy-MM-dd").getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, month);
		d = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.DAY_OF_MONTH, 1);
		return sf.format(c.getTime());
	}

	/**
	 * 得到当前日期加上或减去月份数的那个月的第一天
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月份数
	 * @return 当前日期加上或减去月份数的那个月的第一天MM/dd/yyyy
	 */
	public String getFristDayOfMonth2(Date date, int month) {
		Date d = new Date();
		d.setTime(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, month);
		d = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		c.set(Calendar.DAY_OF_MONTH, 1);
		return sf.format(c.getTime());
	}

	/**
	 * 得到当前日期加上或减去月份数的那个月的最后一天
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月份数
	 * @return 当前日期加上或减去月份数的那个月的最后一天
	 */
	public String getLastDayOfMonth(Date date, int month) {
		Date d = new Date();
		d.setTime(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, month);
		d = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
		return sf.format(c.getTime());
	}

	/**
	 * 得到当前日期加上或减去月份数的那个月的最后一天
	 *
	 * @param date
	 *            当前日期
	 * @param month
	 *            月份数
	 * @return 当前日期加上或减去月份数的那个月的最后一天MM/dd/yyyy
	 */
	public String getLastDayOfMonth2(Date date, int month) {
		Date d = new Date();
		d.setTime(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, month);
		d = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
		return sf.format(c.getTime());
	}

	/**
	 * 获取上周的周一和周日日期.
	 *
	 * @return 周一日期,周日日期
	 */
	public String getDayofLastWeek() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date today1 = new Date();
		try {
			today1 = sf.parse("2013-03-24");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today1);
		String monday;
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		monday = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		cal.add(Calendar.DATE, 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		monday = monday + ","
				+ new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		return monday;
	}

	/**
	 * 获取同比上上周日期.
	 *
	 * @return 上周周一日期，上周周日日期
	 */
	public String getDayofLast2Week() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date today1 = new Date();
		try {
			today1 = sf.parse("2013-03-24");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(today1);
		String monday;
		cal1.add(Calendar.DATE, -14);
		cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		monday = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
		cal1.add(Calendar.DATE, 7);
		cal1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		monday = monday + ","
				+ new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
		return monday;
	}

	/**
	 * 指定年和月，加上指定的天数
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param relativeRadioNum
	 *            指定的天数
	 * @return 得到的日期
	 */
	public String[] getTimes(String year, String month, String relativeRadioNum) {
		String[] time = new String[2];
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DecimalFormat df = new DecimalFormat("00");
			Date d = sf.parse(year + df.format(Integer.valueOf(month)));
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.SECOND, -1);
			String beginTime = sf1.format(c.getTime());
			c.setTime(d);
			for (int i = 0; i < Integer.parseInt(relativeRadioNum); i++) {
				c.add(Calendar.MONTH, -1);
			}
			String endTime = sf1.format(c.getTime());
			time[0] = endTime;
			time[1] = beginTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 指定年和月，加上指定的月数
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param relativeRadioNum
	 *            指定的月数
	 * @return 得到的日期
	 */
	public String[][] getNumMonthTimes(String year, String month,
			String relativeRadioNum) {
		String[][] time = new String[Integer.parseInt(relativeRadioNum) + 1][2];
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat("00");
			Date d = sf.parse(year + df.format(Integer.valueOf(month)));
			for (int i = 0; i < Integer.parseInt(relativeRadioNum) + 1; i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.MONTH, 1);
				c.add(Calendar.SECOND, -1);
				String endTime = sf1.format(c.getTime());
				c.setTime(d);
				String begtinTime = sf1.format(c.getTime());
				c.setTime(d);
				c.add(Calendar.MONTH, -1);
				d = c.getTime();
				time[i][0] = begtinTime;
				time[i][1] = endTime;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 指定开始日期和结束日期，分割日期
	 *
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param num
	 *            分割日期的段数
	 * @return 分割后的日期，List<Map<String, String>> 每个Map代表一个分割后的日期段
	 * @throws Exception
	 */
	public List<Map<String, String>> getSplitTime(String beginDate,
			String endDate, int num) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> dayList = getDayList(beginDate, endDate);
		String preDate = null;
		String curDate = null;
		int split = dayList.size() / num;
		split = split == 0 ? 1 : split;
		for (int i = 0; i < dayList.size(); i = i + split) {
			Map<String, String> map = new HashMap<String, String>();
			if (i == 0) {
				preDate = dayList.get(i);
			} else {
				curDate = dayList.get(i);
				if (i == split) {
					map.put("beginDate", preDate);
					map.put("endDate", curDate);
					if (split == 1) {
						map.put("beginDate", preDate);
						map.put("endDate", preDate);
						result.add(map);
						map = new HashMap<String, String>();
						map.put("beginDate", curDate);
						map.put("endDate", curDate);
						result.add(map);
					}
					result.add(map);
				} else {
					if (i + split >= dayList.size()) {
						map.put("beginDate", getTime(preDate, 1));
						map.put("endDate", dayList.get(dayList.size() - 1));
					} else {
						map.put("beginDate", getTime(preDate, 1));
						map.put("endDate", curDate);
					}
					result.add(map);
				}
				preDate = curDate;
			}
		}
		return result;
	}


	/**
	 * 获得指定日期往前后N天的日期列表
	 *
	 * @param date
	 *            指定日期
	 * @param num
	 *            天数
	 * @return
	 */
	public List<String> getDayList(String date, int num) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			int add = 1;
			if (num < 0) {
				num = -num;
				add = -1;
			}
			c.setTime(sf.parse(date));
			for (int i = 0; i < num; i++) {
				list.add(sf.format(c.getTime()));
				c.add(Calendar.DATE, add);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 得到开始日期和结束日期
	 *
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 日期列表
	 */
	public List<String> getDayList(String beginDate, String endDate) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date end = sf.parse(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(sf.parse(beginDate));
			while (c.getTime().before(end)) {
				list.add(sf.format(c.getTime()));
				c.add(Calendar.DATE, 1);
			}
			list.add(sf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 指定年和月，加上月份数的月列表
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param relativeRadioNum
	 *            月份数
	 * @return 月列表 yyyy-MM
	 */
	public List<String> getMonthList(String year, String month,
			String relativeRadioNum) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
		DecimalFormat df = new DecimalFormat("00");
		try {
			Date d = sf.parse(year + df.format(Integer.valueOf(month)));
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			for (int i = 0; i < Integer.parseInt(relativeRadioNum) + 1; i++) {
				list.add(sf1.format(c.getTime()));
				c.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 设置处理日期.
	 *
	 * @param n 天前
	 * @return 日期
	 */
	public Date getDate(int n) {
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(c.DATE, -n);
		Date time = c.getTime();
		return time;
	}

	/**
	 * 指定两天相差的天数
	 *
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return 两天相差的天数
	 */
	public int getDateDiff(String date1, String date2) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		int diff = 0;
		try {
			diff = (int) ((sf.parse(date2).getTime() - sf.parse(date1)
					.getTime()) / 1000 / 3600 / 24) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 格式化指定日期
	 *
	 * @param date
	 *            日期
	 * @param srcFormat
	 *            当前日期格式
	 * @param targetFormat
	 *            要转换的日期格式
	 * @return 格式化的指定日期
	 * @throws Exception
	 */
	public String formatDate(String date, String srcFormat, String targetFormat)
			throws Exception {
		SimpleDateFormat src = new SimpleDateFormat(srcFormat);
		SimpleDateFormat target = new SimpleDateFormat(targetFormat);
		return target.format(src.parse(date));
	}

	/**
	 * 格式化指定日期
	 *
	 * @param date
	 *            日期
	 * @param targetFormat
	 *            要转换的日期格式
	 * @return 格式化的指定日期
	 * @throws Exception
	 */
	public String formatDate(Date date, String targetFormat) throws Exception {
		SimpleDateFormat target = new SimpleDateFormat(targetFormat);
		return target.format(date);
	}

	/**
	 * 转换为日期
	 *
	 * @param date
	 *            日期
	 * @param srcFormat
	 *            当前日期格式
	 * @return 转换的日期
	 * @throws Exception
	 */
	public Date formatDate(String date, String srcFormat) throws Exception {
		SimpleDateFormat src = new SimpleDateFormat(srcFormat);
		return src.parse(date);
	}

	/**
	 * 得到指定日期的月的第一天
	 *
	 * @param date
	 *            指定日期
	 * @return 当前日期的月的第一天
	 * @throws Exception
	 */
	public String getFirstDayOnMonth(String date) throws Exception {
		return formatDate(date, "yyyy-MM-dd", "yyyy-MM-01");
	}

	/**
	 * 得到当前日期的月的第一天
	 *
	 * @return 当前日期的月的第一天
	 * @throws Exception
	 */
	public String getFirstDayOnCurMonth() throws Exception {
		return getFristDayOfMonth(new Date(), 0);
	}

	/**
	 * 得到当天
	 *
	 * @return 当天yyyy-MM-dd
	 * @throws Exception
	 */
	public String getCurDayOnCurMonth() throws Exception {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	public String addSecondTime(String time, long seconds) throws Exception {
		Date date = formatDate(time, "yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, (int) seconds);
		return formatDate(c.getTime(), "yyyyMMddHHmmss");
	}

	/**
	 * 时间加上秒
	 *
	 * @param time
	 * @param srcFormat
	 * @param destFormat
	 * @param seconds
	 * @return
	 * @throws Exception
	 */
	public String addSecondTime(String time, String srcFormat,
			String destFormat, long seconds) throws Exception {
		Date date = formatDate(time, srcFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, (int) seconds);
		return formatDate(c.getTime(), destFormat);
	}


	/**
	 * 时间间隔
	 *
	 * @param beginTime
	 * @param endTime
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public Long getTimeLength(String beginTime, String endTime, String format)
			throws Exception {
		return (formatDate(endTime, format).getTime() - formatDate(beginTime,
				format).getTime()) / 1000;
	}

	public List<String> getMonthDateList(String begindate, String enddate) {
		List<String> datelist = new ArrayList<String>();
		int beginyear = Integer.parseInt(begindate.split("-")[0].toString());
		int beginmonth = Integer.parseInt(begindate.split("-")[1].toString());
		int endyear = Integer.parseInt(enddate.split("-")[0].toString());
		int endmonth = Integer.parseInt(enddate.split("-")[1].toString());
		for (int i = beginyear; i <= endyear; i++) {
			int year = i;
			int firstmonth = 1;
			int lastmonth = 12;
			if (year == beginyear) {
				firstmonth = beginmonth;
			}
			if (year == endyear) {
				lastmonth = endmonth;
			}
			for (int j = firstmonth; j <= lastmonth; j++) {
				datelist.add(year + "-" + (j < 10 ? "0" + j : j));
			}
		}
		return datelist;
	}

	/**
	 * 将yyyy-MM-dd HH24:MI:SS转换为yyyyMMddHH24MISS
	 *
	 * @param input
	 * @return result
	 */
	public String getAllNumDate(String input) {
		String temp[] = input.split(" ");
		String tempdate[] = temp[0].split("-");
		String temptime[] = temp[1].split(":");
		String result = "";
		for (int i = 0; i < tempdate.length; i++) {
			result += tempdate[i];
		}
		for (int j = 0; j < temptime.length; j++) {
			result += temptime[j];
		}
		return result;
	}

	/**
	 * 将时间戳转换为指定格式
	 *
	 * @param timestampString
	 *            输入时间戳
	 * @param formats
	 *            输出格式
	 * @return
	 */
	public String convertTimeStampToDate(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats)
				.format(new Date(timestamp));
		return date;
	}
	
	/**
	 * 取得某一月份的天数
	 * 
	 * @param yearMonth
	 * @return
	 */
	public int getMonthDays(String yearMonth) {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM"); // 如果写成年月日的形式的话，要写小d，如："yyyy-MM-dd"
		try {
			rightNow.setTime(simpleDate.parse(yearMonth)); // 要计算你想要的月份，改变这里即可
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	
	/**
	 * 获取当前日期时间
	 * @param format 格式
	 * @return
	 */
	public String getCurrentTime(String format){
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        String dateNowStr = sdf.format(d);  
		return dateNowStr;
	}

	/**
	 * 获取两个之间之间的分钟数
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param format 时间字符串格式
	 * @return
	 */
	public long getMinuteBetweenTwoTime(String beginTime,String endTime,String format) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date beginTimeDate = simpleDateFormat.parse(beginTime);
		Date endTimeDate = simpleDateFormat.parse(endTime);
		return (endTimeDate.getTime()-beginTimeDate.getTime())/1000/60;
	}


	public static void main(String[] args) {
		DateUtil dateUtil = new DateUtil();
		try {
			System.out.println(dateUtil.getMinuteBetweenTwoTime("2017-04-18 18:10:37", "2017-04-18 18:12:05","yyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
