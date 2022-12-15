moment.locale('ko');


/*************************/
/*페이지 기능 추가 메서드*/
/*************************/

progressActivityList();
pastActivityList();


/************************************************************ */
/************************************************************ */
/* 연간,월간,주간 달력, 팀안에 개설된 활동 뷰 페이지 기능 코드*/
/************************************************************ */
/************************************************************ */

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

// 메인 페이지 주간 달력 추가
calendarWeek(new Date());

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
// 전체 활동 목록 갱신
$(".calendar-controls > .calendar-reList").on("click", function() {
	progressActivityList();
	pastActivityList();
	$("#progresspagingul").css("display","");
	$(".activityTitle").html("진행중인 활동"); 
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
	

// 선택한 해당 날짜 리스트를 테이블로 구현해주는 기능
function scSearchList(data) {

	const Table = document.querySelector('#dynamicTable');

	let str = "";

		str += '<thead>'
			+ '<tr>'
			+ '<th>날짜</th>'
			+ '<th>활동명</th>'
			+ '<th>멤버</th>'
			+ '<th>회비</th>'
			+ '<th>정보</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody>'
		for (let t of data) {
			str += '<tr>'
				+ '<td>' + moment(t.startTime).format('YY-MM-DD') + '</td>'
				+ '<td>' + t.play + '</td>'
				+ '<td class="activityUser">' + t.userId + '</td>'
				+ '<td>' + t.budget.toLocaleString(); +'</td>'
			str += '<td>' +
				'<button id="joinAc" class="btn btn-success" style="width:100px; height:30px; padding:0%;" th:href="@{ /activity/party?id={ id }'
				+ '(id = ' + t.teamId + ')' + '}">참여</button>'
				+ '</td>'
				+ '</tr>';
		}
		str += '</tbody>';


		Table.innerHTML = str;

}

// 선택한 날짜로 작동하는 기능
$(document).on("click", ".calendar table > tbody > tr > td", function(event) {
	
	$("calendar table > tbody > tr > td").removeAttr("click");
	
	event.stopPropagation();
	var eventTarget = event.target;
	while (eventTarget.tagName != "TD") {
		eventTarget = eventTarget.parentElement;
		$("span").removeClass("active");
		$("#progresspagingul").css("display","none");
	}
	if ($(eventTarget).attr("data-date") != undefined) {
		
		const loadTime = ($(eventTarget).attr("data-date"))
		
		$(".teamJoinDay").val(loadTime)
		
		const startTime= document.querySelector('#teamJoinDay').value;		
	  	
	  	$(".activityTitle").html(startTime + " 일"); 
	  	 axios
        .get("/scTimeSearch", 
        	{ params :
				{ startTime }
			})
        .then(response => {scSearchList(response.data)})
        .catch(err => { console.log(err) });
		
	    $(event.target).addClass("active");
	}
	
});


/********************************/
/*********테이블 페이징 *********/
/********************************/

let totalData; //총 데이터 수
let dataPerPage; //한 페이지에 나타낼 글 수
let pageCount = 5;//페이징에 나타낼 페이지 수
let globalCurrentPage = 1; //현재 페이지

// progress 
var proGlobalData; 
// past
var pastproGlobalData;


/** 
 * 진행중인 활동 목록 테이블/페이징 처리 실행 메서드
 * 
 */
function progressActivityList() {
    $(document).ready(function() {
        dataPerPage = 5;
        var Id = 1

        var param = { Id: Id };
        $.ajax({
            method: "get",
            url: "/team/progressList",
            param,
            dataType: "json",
            success: function(data) {
				console.log(data);	
                totalData = data.length - 1;
				proGlobalData = data;
				
                //글 목록 표시 호출 (테이블 생성)
                progressDisplayData(1, dataPerPage, data)
                //페이징 표시 호출
                progressPaging(totalData, dataPerPage, pageCount, 1)
            }
        });
    });
}
/**
 * 기한이 지난 활동 목록,페이징 처리 실행 메서드
 */
function pastActivityList() {
   
    $(document).ready(function() {
      
        dataPerPage = 5;
      
        var Id = 1

        var param = { Id: Id };
        $.ajax({
            method: "get",
            url: "/team/pastList",
            param,
            dataType: "json",
            success: function(data) {
				console.log(data);
                totalData = data.length - 1;
                pastproGlobalData = data;

                //글 목록 표시 호출 (테이블 생성)
                pastDisplayData(1, dataPerPage, data)
                //페이징 표시 호출
                pastPaging(totalData, dataPerPage, pageCount, 1)
            }
        });
    });
}


/** 
 * 기한이 지난 활동 목록 테이블 페이징 처리
 */
function pastPaging(totalData, dataPerPage, pageCount, currentPage) {

    totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수

    if (totalPage < pageCount) {
        pageCount = totalPage;
    }

    let pageGroup = Math.ceil(currentPage / pageCount); // 페이지 그룹
    let last = pageGroup * pageCount; //화면에 보여질 마지막 페이지 번호

    if (last > totalPage) {
        last = totalPage;
    }

    let first = last - (pageCount - 1); //화면에 보여질 첫번째 페이지 번호
    let next = last + 1;
    let prev = first - 1;

    let pageHtml = "";

    if (prev > 0) {
        pageHtml += "<li><a href='#' id='prev'> 이전 </a></li>";
    }

    //페이징 번호 표시 
    for (var i = first; i <= last; i++) {
        if (currentPage == i) {
            pageHtml +=
                "<li class='on'><a href='#' id='" + i + "'>" + i + "</a></li>";
        } else {
            pageHtml += "<li><a href='#' id='" + i + "'>" + i + "</a></li>";
        }
    }

    if (last < totalPage) {
        pageHtml += "<li><a href='#' id='next'> 다음 </a></li>";
    }

    $("#pastpagingul").html(pageHtml);


    //페이징 번호 클릭 이벤트 
    $("#pastpagingul li a").off().on('click',(function (event) {
        
        pastproGlobalData;

        let $id = $(this).attr("id");
        selectedPage = $(this).text();

        if ($id == "next") selectedPage = next;
        if ($id == "prev") selectedPage = prev;

        //전역변수에 선택한 페이지 번호를 담는다...
        globalCurrentPage = selectedPage;
        //페이징 표시 재호출
        pastPaging(totalData, dataPerPage, pageCount, selectedPage);
        //글 목록 표시 재호출
        pastDisplayData(selectedPage, dataPerPage, pastproGlobalData);
        
        // 마우스 스크롤 
        event.preventDefault();
        event.stopPropagation();
        return false;
    }));
}



/**
 * 기한이 지난(끝난) 활동 테이블 처리
 */
function pastDisplayData(currentPage, dataPerPage, data) {
    
    let chartHtml = "";
    var i;
    //Number로 변환하지 않으면 아래에서 +를 할 경우 스트링 결합이 되어버림.. 
    currentPage = Number(currentPage);
    dataPerPage = Number(dataPerPage);
    
    chartHtml += '<thead>'
		+ '<tr>'
		+ '<th>날짜</th>'
		+ '<th>활동명</th>'
		+ '<th>멤버</th>'
		+ '<th>회비</th>'
		+ '<th>정보</th>'
		+ '</tr>'
		+ '</thead>'
		+ '<tbody>'
		
    
	for (i = (currentPage - 1) * dataPerPage; i < (currentPage - 1) * dataPerPage + dataPerPage; i++) {
        
		if (data[i] == undefined) {
			break;
		}		
	
	const stTime = moment(data[i].startTime).format('YY-MM-DD');
 	  chartHtml += '<tr>'
				+ '<td>' + stTime + '</td>'
				+ '<td>' + data[i].play + '</td>'
				+ '<td class="activityUser">' + data[i].userId + '</td>'
				+ '<td>' + data[i].budget.toLocaleString(); +'</td>'
			chartHtml += '<td>' +
				'<a id="infoAc" class="btn btn-primary" style="width:100px; height:30px; padding:0%;">정보</a>'
				+ '</td>'
				+ '</tr>';
	}
	chartHtml += '</tbody>';

 	$("#dynamicEndTable").empty();
    $("#dynamicEndTable").append(chartHtml);


}
/** 
 * 진행중인 활동 테이블 페이징처리
 *  */
function progressPaging(totalData, dataPerPage, pageCount, currentPage) {

    totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수

    if (totalPage < pageCount) {
        pageCount = totalPage;
    }

    let pageGroup = Math.ceil(currentPage / pageCount); // 페이지 그룹
    let last = pageGroup * pageCount; //화면에 보여질 마지막 페이지 번호

    if (last > totalPage) {
        last = totalPage;
    }

    let first = last - (pageCount - 1); //화면에 보여질 첫번째 페이지 번호
    let next = last + 1;
    let prev = first - 1;

    let progressPageHtml = "";

    if (prev > 0) {
        progressPageHtml += "<li><a href='#' id='prev'> 이전 </a></li>";
    }

    //페이징 번호 표시 
    for (var i = first; i <= last; i++) {
        if (currentPage == i) {
            progressPageHtml +=
                "<li class='on'><a href='#' id='" + i + "'>" + i + "</a></li>";
        } else {
            progressPageHtml += "<li><a href='#' id='" + i + "'>" + i + "</a></li>";
        }
    }

    if (last < totalPage) {
        progressPageHtml += "<li><a href='#' id='next'> 다음 </a></li>";
    }

    $("#progresspagingul").html(progressPageHtml);


    //페이징 번호 클릭 이벤트 
    $("#progresspagingul li a").off().on('click',(function (event) {
        
        proGlobalData;

        let $id = $(this).attr("id");
        selectedPage = $(this).text();

        if ($id == "next") selectedPage = next;
        if ($id == "prev") selectedPage = prev;

        //전역변수에 선택한 페이지 번호를 담는다...
        globalCurrentPage = selectedPage;
        //페이징 표시 재호출
        progressPaging(totalData, dataPerPage, pageCount, selectedPage);
        //글 목록 표시 재호출
        progressDisplayData(selectedPage, dataPerPage, proGlobalData);
        
        // 마우스 스크롤 
        event.preventDefault();
        event.stopPropagation();
        return false;
    }));
}

/**
 * 진행중인 활동 목록 테이블 처리 
 * */ 
function progressDisplayData(currentPage, dataPerPage, data) {
    
    let chartHtml = "";
    var i;
    //Number로 변환하지 않으면 아래에서 +를 할 경우 스트링 결합이 되어버림.. 
    currentPage = Number(currentPage);
    dataPerPage = Number(dataPerPage);
    
    chartHtml += '<thead>'
        + '<tr>'
        + '<th>날짜</th>'
        + '<th>활동명</th>'
        + '<th>멤버</th>'
        + '<th>회비</th>'
        + '<th>정보</th>'
        + '</tr>'
        + '</thead>'
        + '<tbody>'
        
    
    for (i = (currentPage - 1) * dataPerPage; i < (currentPage - 1) * dataPerPage + dataPerPage; i++) {
        
        if (data[i] == undefined) {
            break;
        }       
    
    const stTime = moment(data[i].startTime).format('YY-MM-DD');
      chartHtml += '<tr>'
                + '<td>' + stTime + '</td>'
                + '<td>' + data[i].play + '</td>'
                + '<td class="activityUser">' + data[i].userId + '</td>'
                + '<td>' + data[i].budget.toLocaleString(); +'</td>'
                
                // if 안에 조건문은 임시, teamId가 일치하면서 해당 activityId가 비어있을경우 참여버튼 생성 
                if (data[i].teamId == 1) {
            chartHtml += '<td>' +
                '<a id="joinAc" class="btn btn-success" style="width:100px; height:30px; padding:0%;" href="/post/create">참여</a>'
                + '</td>'
                
                // 이미 가입된 회원일 경우 탈퇴버튼 생성.
                } else if (data[i].teamId != 1) {
            chartHtml += '<td>' +
                '<a id="joinAc" class="btn btn-danger" style="width:100px; height:30px; padding:0%;" href="/post/create">탈퇴</a>'
                + '</td>'   
                }
                + '</tr>';
                
                
    }
    
    const buttons = document.querySelectorAll('.btnPartyIn');
    buttons.forEach(btn => {
       btn.addEventListener('click', insertMyList) 
    });
    chartHtml += '</tbody>';

    $("#dynamicTable").empty();
    $("#dynamicTable").append(chartHtml);
}

function insertMyList(event) {
    
    const teamId = event.target.getAttribute('data-teamId');
    
    axios
    .get('/activity/paryIn' + teamId)
    .then(response => { console.log(response) })
    .catch(err => { console.log(err) })
    
}




/********************************************/
/********* activityUser 레이어 팝업창 *******/
/********************************************/
function openPop() {
    document.getElementById("popup_layer").style.display = "block";
	
}

//팝업 닫기
function closePop() {
    document.getElementById("popup_layer").style.display = "none";
}


