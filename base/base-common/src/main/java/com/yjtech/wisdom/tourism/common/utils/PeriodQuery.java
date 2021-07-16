package com.yjtech.wisdom.tourism.common.utils;

import java.time.LocalDate;
import java.util.Objects;

public class PeriodQuery {
    private Long scenicId;
    private Integer type;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer lowerYear;
    private Integer higherYear;
    private Integer lowerMonth;
    private Integer higherMonth;
    private Integer lowerDay;
    private Integer higherDay;
    private String begin;
    private String end;

    public Integer getLowerYear() {
        if (Objects.nonNull(this.lowerYear)) {
            return this.lowerYear;
        } else {
            LocalDate date = this.getBeginDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.lowerYear = date.getYear();
                return this.lowerYear;
            }
        }
    }

    public Integer getHigherYear() {
        if (Objects.nonNull(this.higherYear)) {
            return this.higherYear;
        } else {
            LocalDate date = this.getEndDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.higherYear = date.getYear();
                return this.higherYear;
            }
        }
    }

    public Integer getLowerMonth() {
        if (Objects.nonNull(this.lowerMonth)) {
            return this.lowerMonth;
        } else {
            LocalDate date = this.getBeginDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.lowerMonth = date.getMonthValue();
                return this.lowerMonth;
            }
        }
    }

    public Integer getHigherMonth() {
        if (Objects.nonNull(this.higherMonth)) {
            return this.higherMonth;
        } else {
            LocalDate date = this.getEndDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.higherMonth = date.getMonthValue();
                return this.higherMonth;
            }
        }
    }

    public Integer getLowerDay() {
        if (Objects.nonNull(this.lowerDay)) {
            return this.lowerDay;
        } else {
            LocalDate date = this.getBeginDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.lowerDay = date.getDayOfMonth();
                return this.lowerDay;
            }
        }
    }

    public Integer getHigherDay() {
        if (Objects.nonNull(this.higherDay)) {
            return this.higherDay;
        } else {
            LocalDate date = this.getEndDate();
            if (Objects.isNull(date)) {
                return null;
            } else {
                this.higherDay = date.getDayOfMonth();
                return this.higherDay;
            }
        }
    }

    public LocalDate getBeginDate() {
        return CommonPreconditions.checkStringEmpty(this.begin) ? null : this.getLocalDate(this.begin);
    }

    public LocalDate getEndDate() {
        return CommonPreconditions.checkStringEmpty(this.end) ? null : this.getLocalDate(this.end);
    }

    public boolean containToday() {
        LocalDate beginDate = this.getBeginDate();
        LocalDate endDate = this.getEndDate();
        LocalDate today = LocalDate.now();
        return today.isAfter(beginDate) && today.isBefore(endDate) || today.isEqual(beginDate) || today.isEqual(endDate);
    }

    public boolean containCurMonth() {
        LocalDate beginDate = this.getBeginDate();
        LocalDate endDate = this.getEndDate().plusMonths(1L);
        LocalDate beginDateForMonth = LocalDate.of(beginDate.getYear(), beginDate.getMonth(), 1);
        LocalDate endDateForMonth = LocalDate.of(endDate.getYear(), endDate.getMonth(), 1);
        LocalDate today = LocalDate.now();
        return today.isAfter(beginDateForMonth) && today.isBefore(endDateForMonth) || today.isEqual(beginDateForMonth) || today.isEqual(endDateForMonth);
    }

    public boolean containCurYear() {
        int beginYear = this.getBeginDate().getYear();
        int endYear = this.getEndDate().plusYears(1L).getYear();
        int curYear = LocalDate.now().getYear();
        return curYear >= beginYear && curYear < endYear;
    }

    public Type getTypeEnum() {
        return Type.getTypeEnum(this.type);
    }

    private LocalDate getLocalDate(String s) {
        Type type = this.getTypeEnum();
        LocalDate localDate;
        switch (type) {
            case DAY:
                CommonPreconditions.checkDate(s, "wrong date format.");
                localDate = DateTimeUtil.stringToLocalDate(s);
                break;
            case YEAR:
                CommonPreconditions.checkDateYYYY(s, "wrong date format.");
                s = s + "-01-01";
                localDate = DateTimeUtil.stringToLocalDate(s);
                break;
            case MONTH:
                CommonPreconditions.checkDateYYYYMM(s, "wrong date format.");
                s = s + "-01";
                localDate = DateTimeUtil.stringToLocalDate(s);
                break;
            default:
                localDate = null;
        }

        return localDate;
    }

    public static PeriodQueryBuilder builder() {
        return new PeriodQueryBuilder();
    }

    public PeriodQueryBuilder toBuilder() {
        return (new PeriodQueryBuilder()).scenicId(this.scenicId).type(this.type).year(this.year).month(this.month).day(this.day).hour(this.hour).minute(this.minute).second(this.second).lowerYear(this.lowerYear).higherYear(this.higherYear).lowerMonth(this.lowerMonth).higherMonth(this.higherMonth).lowerDay(this.lowerDay).higherDay(this.higherDay).begin(this.begin).end(this.end);
    }

    public Long getScenicId() {
        return this.scenicId;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getYear() {
        return this.year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public Integer getDay() {
        return this.day;
    }

    public Integer getHour() {
        return this.hour;
    }

    public Integer getMinute() {
        return this.minute;
    }

    public Integer getSecond() {
        return this.second;
    }

    public String getBegin() {
        return this.begin;
    }

    public String getEnd() {
        return this.end;
    }

    public void setScenicId(final Long scenicId) {
        this.scenicId = scenicId;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public void setMonth(final Integer month) {
        this.month = month;
    }

    public void setDay(final Integer day) {
        this.day = day;
    }

    public void setHour(final Integer hour) {
        this.hour = hour;
    }

    public void setMinute(final Integer minute) {
        this.minute = minute;
    }

    public void setSecond(final Integer second) {
        this.second = second;
    }

    public void setLowerYear(final Integer lowerYear) {
        this.lowerYear = lowerYear;
    }

    public void setHigherYear(final Integer higherYear) {
        this.higherYear = higherYear;
    }

    public void setLowerMonth(final Integer lowerMonth) {
        this.lowerMonth = lowerMonth;
    }

    public void setHigherMonth(final Integer higherMonth) {
        this.higherMonth = higherMonth;
    }

    public void setLowerDay(final Integer lowerDay) {
        this.lowerDay = lowerDay;
    }

    public void setHigherDay(final Integer higherDay) {
        this.higherDay = higherDay;
    }

    public void setBegin(final String begin) {
        this.begin = begin;
    }

    public void setEnd(final String end) {
        this.end = end;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PeriodQuery)) {
            return false;
        } else {
            PeriodQuery other = (PeriodQuery) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label203:
                {
                    Object this$scenicId = this.getScenicId();
                    Object other$scenicId = other.getScenicId();
                    if (this$scenicId == null) {
                        if (other$scenicId == null) {
                            break label203;
                        }
                    } else if (this$scenicId.equals(other$scenicId)) {
                        break label203;
                    }

                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                Object this$year = this.getYear();
                Object other$year = other.getYear();
                if (this$year == null) {
                    if (other$year != null) {
                        return false;
                    }
                } else if (!this$year.equals(other$year)) {
                    return false;
                }

                label182:
                {
                    Object this$month = this.getMonth();
                    Object other$month = other.getMonth();
                    if (this$month == null) {
                        if (other$month == null) {
                            break label182;
                        }
                    } else if (this$month.equals(other$month)) {
                        break label182;
                    }

                    return false;
                }

                label175:
                {
                    Object this$day = this.getDay();
                    Object other$day = other.getDay();
                    if (this$day == null) {
                        if (other$day == null) {
                            break label175;
                        }
                    } else if (this$day.equals(other$day)) {
                        break label175;
                    }

                    return false;
                }

                label168:
                {
                    Object this$hour = this.getHour();
                    Object other$hour = other.getHour();
                    if (this$hour == null) {
                        if (other$hour == null) {
                            break label168;
                        }
                    } else if (this$hour.equals(other$hour)) {
                        break label168;
                    }

                    return false;
                }

                Object this$minute = this.getMinute();
                Object other$minute = other.getMinute();
                if (this$minute == null) {
                    if (other$minute != null) {
                        return false;
                    }
                } else if (!this$minute.equals(other$minute)) {
                    return false;
                }

                label154:
                {
                    Object this$second = this.getSecond();
                    Object other$second = other.getSecond();
                    if (this$second == null) {
                        if (other$second == null) {
                            break label154;
                        }
                    } else if (this$second.equals(other$second)) {
                        break label154;
                    }

                    return false;
                }

                Object this$lowerYear = this.getLowerYear();
                Object other$lowerYear = other.getLowerYear();
                if (this$lowerYear == null) {
                    if (other$lowerYear != null) {
                        return false;
                    }
                } else if (!this$lowerYear.equals(other$lowerYear)) {
                    return false;
                }

                label140:
                {
                    Object this$higherYear = this.getHigherYear();
                    Object other$higherYear = other.getHigherYear();
                    if (this$higherYear == null) {
                        if (other$higherYear == null) {
                            break label140;
                        }
                    } else if (this$higherYear.equals(other$higherYear)) {
                        break label140;
                    }

                    return false;
                }

                Object this$lowerMonth = this.getLowerMonth();
                Object other$lowerMonth = other.getLowerMonth();
                if (this$lowerMonth == null) {
                    if (other$lowerMonth != null) {
                        return false;
                    }
                } else if (!this$lowerMonth.equals(other$lowerMonth)) {
                    return false;
                }

                Object this$higherMonth = this.getHigherMonth();
                Object other$higherMonth = other.getHigherMonth();
                if (this$higherMonth == null) {
                    if (other$higherMonth != null) {
                        return false;
                    }
                } else if (!this$higherMonth.equals(other$higherMonth)) {
                    return false;
                }

                label119:
                {
                    Object this$lowerDay = this.getLowerDay();
                    Object other$lowerDay = other.getLowerDay();
                    if (this$lowerDay == null) {
                        if (other$lowerDay == null) {
                            break label119;
                        }
                    } else if (this$lowerDay.equals(other$lowerDay)) {
                        break label119;
                    }

                    return false;
                }

                label112:
                {
                    Object this$higherDay = this.getHigherDay();
                    Object other$higherDay = other.getHigherDay();
                    if (this$higherDay == null) {
                        if (other$higherDay == null) {
                            break label112;
                        }
                    } else if (this$higherDay.equals(other$higherDay)) {
                        break label112;
                    }

                    return false;
                }

                Object this$begin = this.getBegin();
                Object other$begin = other.getBegin();
                if (this$begin == null) {
                    if (other$begin != null) {
                        return false;
                    }
                } else if (!this$begin.equals(other$begin)) {
                    return false;
                }

                Object this$end = this.getEnd();
                Object other$end = other.getEnd();
                if (this$end == null) {
                    if (other$end != null) {
                        return false;
                    }
                } else if (!this$end.equals(other$end)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PeriodQuery;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $scenicId = this.getScenicId();
        result = result * 59 + ($scenicId == null ? 43 : $scenicId.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $year = this.getYear();
        result = result * 59 + ($year == null ? 43 : $year.hashCode());
        Object $month = this.getMonth();
        result = result * 59 + ($month == null ? 43 : $month.hashCode());
        Object $day = this.getDay();
        result = result * 59 + ($day == null ? 43 : $day.hashCode());
        Object $hour = this.getHour();
        result = result * 59 + ($hour == null ? 43 : $hour.hashCode());
        Object $minute = this.getMinute();
        result = result * 59 + ($minute == null ? 43 : $minute.hashCode());
        Object $second = this.getSecond();
        result = result * 59 + ($second == null ? 43 : $second.hashCode());
        Object $lowerYear = this.getLowerYear();
        result = result * 59 + ($lowerYear == null ? 43 : $lowerYear.hashCode());
        Object $higherYear = this.getHigherYear();
        result = result * 59 + ($higherYear == null ? 43 : $higherYear.hashCode());
        Object $lowerMonth = this.getLowerMonth();
        result = result * 59 + ($lowerMonth == null ? 43 : $lowerMonth.hashCode());
        Object $higherMonth = this.getHigherMonth();
        result = result * 59 + ($higherMonth == null ? 43 : $higherMonth.hashCode());
        Object $lowerDay = this.getLowerDay();
        result = result * 59 + ($lowerDay == null ? 43 : $lowerDay.hashCode());
        Object $higherDay = this.getHigherDay();
        result = result * 59 + ($higherDay == null ? 43 : $higherDay.hashCode());
        Object $begin = this.getBegin();
        result = result * 59 + ($begin == null ? 43 : $begin.hashCode());
        Object $end = this.getEnd();
        result = result * 59 + ($end == null ? 43 : $end.hashCode());
        return result;
    }

    public String toString() {
        return "PeriodQuery(scenicId=" + this.getScenicId() + ", type=" + this.getType() + ", year=" + this.getYear() + ", month=" + this.getMonth() + ", day=" + this.getDay() + ", hour=" + this.getHour() + ", minute=" + this.getMinute() + ", second=" + this.getSecond() + ", lowerYear=" + this.getLowerYear() + ", higherYear=" + this.getHigherYear() + ", lowerMonth=" + this.getLowerMonth() + ", higherMonth=" + this.getHigherMonth() + ", lowerDay=" + this.getLowerDay() + ", higherDay=" + this.getHigherDay() + ", begin=" + this.getBegin() + ", end=" + this.getEnd() + ")";
    }

    public PeriodQuery(final Long scenicId, final Integer type, final Integer year, final Integer month, final Integer day, final Integer hour, final Integer minute, final Integer second, final Integer lowerYear, final Integer higherYear, final Integer lowerMonth, final Integer higherMonth, final Integer lowerDay, final Integer higherDay, final String begin, final String end) {
        this.scenicId = scenicId;
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.lowerYear = lowerYear;
        this.higherYear = higherYear;
        this.lowerMonth = lowerMonth;
        this.higherMonth = higherMonth;
        this.lowerDay = lowerDay;
        this.higherDay = higherDay;
        this.begin = begin;
        this.end = end;
    }

    public PeriodQuery() {
    }

    public static class PeriodQueryBuilder {
        private Long scenicId;
        private Integer type;
        private Integer year;
        private Integer month;
        private Integer day;
        private Integer hour;
        private Integer minute;
        private Integer second;
        private Integer lowerYear;
        private Integer higherYear;
        private Integer lowerMonth;
        private Integer higherMonth;
        private Integer lowerDay;
        private Integer higherDay;
        private String begin;
        private String end;

        PeriodQueryBuilder() {
        }

        public PeriodQueryBuilder scenicId(final Long scenicId) {
            this.scenicId = scenicId;
            return this;
        }

        public PeriodQueryBuilder type(final Integer type) {
            this.type = type;
            return this;
        }

        public PeriodQueryBuilder year(final Integer year) {
            this.year = year;
            return this;
        }

        public PeriodQueryBuilder month(final Integer month) {
            this.month = month;
            return this;
        }

        public PeriodQueryBuilder day(final Integer day) {
            this.day = day;
            return this;
        }

        public PeriodQueryBuilder hour(final Integer hour) {
            this.hour = hour;
            return this;
        }

        public PeriodQueryBuilder minute(final Integer minute) {
            this.minute = minute;
            return this;
        }

        public PeriodQueryBuilder second(final Integer second) {
            this.second = second;
            return this;
        }

        public PeriodQueryBuilder lowerYear(final Integer lowerYear) {
            this.lowerYear = lowerYear;
            return this;
        }

        public PeriodQueryBuilder higherYear(final Integer higherYear) {
            this.higherYear = higherYear;
            return this;
        }

        public PeriodQueryBuilder lowerMonth(final Integer lowerMonth) {
            this.lowerMonth = lowerMonth;
            return this;
        }

        public PeriodQueryBuilder higherMonth(final Integer higherMonth) {
            this.higherMonth = higherMonth;
            return this;
        }

        public PeriodQueryBuilder lowerDay(final Integer lowerDay) {
            this.lowerDay = lowerDay;
            return this;
        }

        public PeriodQueryBuilder higherDay(final Integer higherDay) {
            this.higherDay = higherDay;
            return this;
        }

        public PeriodQueryBuilder begin(final String begin) {
            this.begin = begin;
            return this;
        }

        public PeriodQueryBuilder end(final String end) {
            this.end = end;
            return this;
        }

        public PeriodQuery build() {
            return new PeriodQuery(this.scenicId, this.type, this.year, this.month, this.day, this.hour, this.minute, this.second, this.lowerYear, this.higherYear, this.lowerMonth, this.higherMonth, this.lowerDay, this.higherDay, this.begin, this.end);
        }

        public String toString() {
            return "PeriodQuery.PeriodQueryBuilder(scenicId=" + this.scenicId + ", type=" + this.type + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", lowerYear=" + this.lowerYear + ", higherYear=" + this.higherYear + ", lowerMonth=" + this.lowerMonth + ", higherMonth=" + this.higherMonth + ", lowerDay=" + this.lowerDay + ", higherDay=" + this.higherDay + ", begin=" + this.begin + ", end=" + this.end + ")";
        }
    }

    public static enum Type {
        HOUR(0),
        DAY(1),
        MONTH(2),
        YEAR(3),
        TOTAL(4);

        private int type;

        private Type(int type) {
            this.type = type;
        }

        public static Type getTypeEnum(int type) {
            Type[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Type typeEnum = var1[var3];
                if (type == typeEnum.type) {
                    return typeEnum;
                }
            }

            return null;
        }

        public int getType() {
            return this.type;
        }
    }
}

