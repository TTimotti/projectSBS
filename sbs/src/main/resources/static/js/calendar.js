

// 달력 요일
var calendarDays = ["일", "월", "화", "수", "목", "금", "토"];

// 달력 요일 HTML
function calendarWeekHTML(options) {
    var html = "<thead><tr>";
    for (var index = 0; index < calendarDays.length; index++) {
        html += "<th";
        if (index == 0) {
            html += " class=\"sunday\"";
        } else if (index == 6) {
            html += " class=\"saturday\"";
        }
        html += ">" + calendarDays[index];
        if (options.showFullDayName) {
            html += "요일";
        }
        html += "</th>";
    }
    html += "</tr></thead>";
    return html;
}

// 달력 공휴일
var hashmapCalendarHoliday = [];
hashmapCalendarHoliday["1-1"] = {"title" : "새해"};
hashmapCalendarHoliday["3-1"] = {"title" : "삼일절"};
hashmapCalendarHoliday["5-5"] = {"title" : "어린이날"};
hashmapCalendarHoliday["6-6"] = {"title" : "현충일"};
hashmapCalendarHoliday["8-15"] = {"title" : "광복절"};
hashmapCalendarHoliday["10-3"] = {"title" : "개천절"};
hashmapCalendarHoliday["10-9"] = {"title" : "한글날"};
hashmapCalendarHoliday["12-25"] = {"title" : "성탄절"};

// 달력 공휴일 함수
function getCalendarHoliday(calendarYear, calendarMonth, calendarDay) {
    if (Object.keys(hashmapCalendarHoliday).length == 0) {
        return null;
    }
    
    // 공휴일(임시 공휴일 포함)
    var holidayInfo = hashmapCalendarHoliday[calendarYear + "-" + calendarMonth + "-" + calendarDay];
    if (holidayInfo == undefined || holidayInfo == null) {
        holidayInfo = hashmapCalendarHoliday[calendarMonth + "-" + calendarDay];
    }
    return holidayInfo ;
}

// 기본값 처리
function setCalendarOptions(options) {
    // 기본값 처리
    if (options.showDay == undefined || options.showDay == null || typeof options.showDay != "boolean") {
        options.showDay = true;
    }
    if (options.showFullDayName == undefined || options.showFullDayName == null || typeof options.showFullDayName != "boolean") {
        options.showFullDayName = false;
    }
    if (options.showToday == undefined || options.showToday == null || typeof options.showToday != "boolean") {
        options.showToday = true;
    }
    
    // 공휴일 처리
    if (options.arHoliday != undefined && options.arHoliday != null && Array.isArray(options.arHoliday)) {
        Object.assign(hashmapCalendarHoliday, options.arHoliday);
    }
}



// 주간 달력 생성 함수
function weekHTML(date, options) {
    // 데이터 검증
    if (date == undefined || date == null || typeof date != "object" || !date instanceof Date) {
        return "";
    }
    
    setCalendarOptions(options);
    
    // 달력 연도
    var calendarYear = date.getFullYear();
    // 달력 월
    var calendarMonth = date.getMonth() + 1;
    // 달력 일
    var calendarToday = date.getDate();
    
    var monthLastDate = new Date(calendarYear, calendarMonth, 0);
    // 달력 월의 마지막 일
    var calendarMonthLastDate = monthLastDate.getDate();

    // 달력 이전 월의 마지막 일
    var prevMonthLastDate = new Date(calendarYear, calendarMonth - 1, 0);

    // 달력 다음 월의 시작 일
    var nextMonthStartDate = new Date(calendarYear, calendarMonth, 1);
    
    // 달력 현재 요일
    var calendarMonthTodayDay = date.getDay();
    
    // 주간 배열
    var arWeek = ["", "", "", "", "", "", ""];
    
    var weekYear = calendarYear;
    var weekMonth = calendarMonth;
    var weekDay = calendarToday;
    // 현재 요일부터 주간 배열에 날짜를 추가
    for (var index = calendarMonthTodayDay; index < 7; index++) {
        arWeek[index] = weekYear +"-" + weekMonth + "-" + weekDay;
        weekDay++;
        // 날짜가 현재 월의 마지막 일보다 크면 다음 월의 1일로 변경
        if (weekDay > calendarMonthLastDate) {
            weekYear = nextMonthStartDate.getFullYear();
            weekMonth = nextMonthStartDate.getMonth() + 1;
            weekDay = 1;
        }
    }
    
    weekYear = calendarYear;
    weekMonth = calendarMonth;
    weekDay = calendarToday;
    // 현재 요일부터 꺼꾸로 주간 배열에 날짜를 추가
    for (var index = calendarMonthTodayDay - 1; index >= 0; index--) {
        weekDay--;
        // 날짜가 현재 월의 1일이면 작으면 이전 월의 마지막 일로 변경
        if (weekDay == 0) {
            weekYear = prevMonthLastDate.getFullYear();
            weekMonth = prevMonthLastDate.getMonth() + 1;
            weekDay = prevMonthLastDate.getDate();
        }
        arWeek[index] = weekYear +"-" + weekMonth + "-" + weekDay;
    }
        
    // 오늘
    var today = new Date();
    
    var html = "";
    html += "<table>";
    if (options.showDay) {
        html += calendarWeekHTML(options);
    }
    html += "<tbody>";
    html += "<tr>";
    for (var index = 0; index < 7; index++) {
        var arWeekDate = arWeek[index].split("-");
        var year = arWeekDate[0];
        var month = arWeekDate[1];
        var day = arWeekDate[2];
        html += "<td data-date=\"" + year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day +  "\">";
        html += "<span";
        if (options.showToday && year == today.getFullYear() && month == today.getMonth() + 1
            && day == today.getDate()) {
            html += " class=\"today\"";
        } else {
            var holiday = false;
            var holidayInfo = getCalendarHoliday(year, month, day);
            if (holidayInfo != undefined && holidayInfo != null) {
                html += " class=\"holiday\"";
                holiday = true;
            }
            if (!holiday) {
                if (index == 0) {
                    html += " class=\"sunday\"";
                } else if (index == 6) {
                    html += " class=\"saturday\"";
                }
            }
        }
        var holidayInfo = getCalendarHoliday(year, month, day);
        if (holidayInfo != undefined && holidayInfo != null) {
            html += " title=\"" + holidayInfo.title + "\"";
        }
        html += ">" + day + "</span>";
        html += "</td>";
    }
    html += "</tbody>";
    html += "</table>";
    return html;
}



// 월간 달력 생성 함수
function calendarHTML(date, options) {
    // 데이터 검증
    if (date == undefined || date == null || typeof date != "object" || !date instanceof Date) {
        return "";
    }
    
    setCalendarOptions(options);
    
    // 달력 연도
    var calendarYear = date.getFullYear();
    // 달력 월
    var calendarMonth = date.getMonth() + 1;
    // 달력 일
    var calendarToday = date.getDate();
    
    var monthLastDate = new Date(calendarYear, calendarMonth, 0);
    // 달력 월의 마지막 일
    var calendarMonthLastDate = monthLastDate.getDate();
    
    var monthStartDay = new Date(calendarYear, date.getMonth(), 1);
    // 달력 월의 시작 요일
    var calendarMonthStartDay = monthStartDay.getDay();
    
    // 주 카운트
    var calendarWeekCount = Math.ceil((calendarMonthStartDay + calendarMonthLastDate) / 7);
    
    // 오늘
    var today = new Date();
    
    var html = "";
    html += "<table>";
    if (options.showDay) {
        html += calendarWeekHTML(options);
    }
    html += "<tbody>";
    // 위치
    var calendarPos = 0;
    // 날짜
    var calendarDay = 0;
    for (var index1 = 0; index1 < calendarWeekCount; index1++) {
        html += "<tr>";
        for (var index2 = 0; index2 < 7; index2++) {
            html += "<td";
            if (calendarMonthStartDay <= calendarPos && calendarDay < calendarMonthLastDate) {
                calendarDay++;
                html += " data-date=\"" + calendarYear + "-" + (calendarMonth < 10 ? "0" : "") + calendarMonth + "-" + (calendarDay < 10 ? "0" : "") + calendarDay +  "\">";
                html += "<span";
                if (options.showToday && calendarYear == today.getFullYear() && calendarMonth == today.getMonth() + 1
                    && calendarDay == today.getDate()) {
                    html += " class=\"today\"";
                } else {
                    var holiday = false;
                    var holidayInfo = getCalendarHoliday(calendarYear, calendarMonth, calendarDay);
                    if (holidayInfo != undefined && holidayInfo != null) {
                        html += " class=\"holiday\"";
                        holiday = true;
                    }
                    if (!holiday) {
                        if (index2 == 0) {
                            html += " class=\"sunday\"";
                        } else if (index2 == 6) {
                            html += " class=\"saturday\"";
                        }
                    }
                }
                var holidayInfo = getCalendarHoliday(calendarYear, calendarMonth, calendarDay);
                if (holidayInfo != undefined && holidayInfo != null) {
                    html += " title=\"" + holidayInfo.title + "\"";
                }
                html += ">" + calendarDay + "</span>";
            } else {
                html += ">";
            }
            html += "</td>";
            calendarPos++;
        }
        html += "</tr>";
    }
    html += "</tbody>";
    html += "</table>";
    return html;
}





var hashmapTemporaryHoliday = [];
hashmapTemporaryHoliday["2022-3-9"] = {"title" : "20대 대통령 선거일"};



// 주간 달력 
function calendarWeek(date) {
    $(".calendar").removeClass("month").removeClass("year");
    $(".calendar").addClass("week");
    // 년월
    $(".calendar-yearmonth").html(date.getFullYear() + "년 " + (date.getMonth() + 1) + "월");
    
    var options = {
        showDay : true,
        showFullDayName : true,
        showToday : true,
        arHoliday : hashmapTemporaryHoliday
    };
    
    var html = weekHTML(date, options);
    $("#calendar").attr("data-date", date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate());
    $("#calendar").html(html);
}

// 월간 달력
function calendarMonth(date) {

    $(".calendar").removeClass("year").removeClass("week");
    $(".calendar").addClass("month");
    // 년월
    $(".calendar-yearmonth").html(date.getFullYear() + "." + (date.getMonth() + 1));
    
    var options = {
        showDay : true,
        showFullDayName : true,
        showToday : true,
        arHoliday : hashmapTemporaryHoliday
    };
    
    var html = calendarHTML(date, options);
    $("#calendar").attr("data-date", date.getFullYear() + "-" + (date.getMonth() + 1));
    $("#calendar").html(html);
}

// 메인 페이지 주간 달력 추가
calendarWeek(new Date());


//  연간 달력
function calendarYear(date) {
    
    $(".calendar").removeClass("month").removeClass("week");
    $(".calendar").addClass("year");
    // 년
    $(".calendar-yearmonth").html(date.getFullYear() + "년");
        
    var options = {
        showDay : true,
        showToday : true,
        arHoliday : hashmapTemporaryHoliday
    };
    
    var html = "";
    html += "<table>";
    html += "<tbody>";
    for (var index1 = 0; index1 < 4; index1++) {
        html += "<tr>";
        for (var index2 = 0; index2 < 3; index2++) {
            html += "<td>";
            html += "<div class=\"calendar-month\">" + (index1 * 3 + index2 + 1) + "월</div>";
            html += calendarHTML(new Date(date.getFullYear(), (index1 * 3 + index2), 1), options);
            html += "</td>";
        }
        html += "</tr>";
    }
    html += "</tbody>";
    html += "</table>";
    
    $("#calendar").attr("data-date", date.getFullYear());
    $("#calendar").html(html);
} 


// 클릭 이벤트 // 

// 이전 이동 버튼
$(".calendar-controls > .calendar-prev").on("click", function() {
    if ($(".calendar").hasClass("year")) {
        var year = $("#calendar").attr("data-date");
        calendarYear(new Date(parseInt(year) - 1, 1, 1));
    } else if ($(".calendar").hasClass("month")) {
        var yearmonth = $("#calendar").attr("data-date").split("-");
        calendarMonth(new Date(parseInt(yearmonth[0]), parseInt(yearmonth[1]) - 2, 1));
    } else if ($(".calendar").hasClass("week")) {
        var yearmonthday = $("#calendar").attr("data-date").split("-");
        calendarWeek(new Date(parseInt(yearmonthday[0]), parseInt(yearmonthday[1]) - 1, parseInt(yearmonthday[2]) - 7));
    }
});

// 다음 이동 버튼
$(".calendar-controls > .calendar-next").on("click", function() {
    if ($(".calendar").hasClass("year")) {
        var year = $("#calendar").attr("data-date");
        calendarYear(new Date(parseInt(year) + 1, 1, 1));
    } else if ($(".calendar").hasClass("month")) {
        var yearmonth = $("#calendar").attr("data-date").split("-");
        calendarMonth(new Date(parseInt(yearmonth[0]), parseInt(yearmonth[1]), 1));
    } else if ($(".calendar").hasClass("week")) {
        var yearmonthday = $("#calendar").attr("data-date").split("-");
        calendarWeek(new Date(parseInt(yearmonthday[0]), parseInt(yearmonthday[1]) - 1, parseInt(yearmonthday[2]) + 7));
    }
});

// 오늘 이동 버튼
$(".calendar-controls > .calendar-today").on("click", function() {
    if ($(".calendar").hasClass("year")) {
        calendarYear(new Date());
    } else if ($(".calendar").hasClass("month")) {
        calendarMonth(new Date());
    } else if ($(".calendar").hasClass("week")) {
        calendarWeek(new Date());
    }
});
// 달력 뷰 버튼

$(".calendar-views > button").on("click", function(event) {
    $(".calendar-views > button").each(function() {
        $(this).removeClass("active");
    });
    if ($(event.target).hasClass("calendar-view-year")) {
        calendarYear(new Date());
    } else if ($(event.target).hasClass("calendar-view-month")) {
        calendarMonth(new Date());
    } else if ($(event.target).hasClass("calendar-view-week")) {
        calendarWeek(new Date());
    }
    $(event.target).addClass("active");
});

// 날짜 클릭
$(document).on("click", ".calendar table > tbody > tr > td", function(event) {
    event.stopPropagation();
    var eventTarget = event.target;
    while (eventTarget.tagName != "TD") {
        eventTarget = eventTarget.parentElement;
    }
    if ($(eventTarget).attr("data-date") != undefined) {
        alert($(eventTarget).attr("data-date"));
    }
});