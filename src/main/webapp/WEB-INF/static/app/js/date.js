<script>

	function getBoforeDate(before){
		var now = new Date();
		now.setDate(now.getDate()-before);
		return now;
	}
	
	function getBoforeMonth(beforeMonth,day){
		var now = new Date();
		now.setDate(day);
		now.setMonth(now.getMonth()-beforeMonth);
		return now;
	}
	
	$(function(){
		
		var now = new Date();
		
		$('.date').daterangepicker({
		    "showWeekNumbers": true,
		    "showISOWeekNumbers": true,
		    "ranges": {
		        "今天": [
		            now,
		            now
		        ],
		        "昨天": [
					getBoforeDate(1),
					getBoforeDate(1)
		        ],
		        "最近7天": [
					getBoforeDate(7),
					now
		        ],
		        "最近30天": [
		            getBoforeDate(30),
		            now
		        ],
		        "本月": [
		            getBoforeMonth(0,1),
		            getBoforeMonth(0,31)
		        ],
		        "上个月": [
					getBoforeMonth(1,1),
					getBoforeMonth(1,31)
		        ],
		        "最近三个月": [
						getBoforeMonth(2,1),
						getBoforeMonth(0,31)
			        ]
		    },
		    "locale": {
		        "format": "YYYY/MM/DD",
		        "separator": "-",
		        "applyLabel": "应用",
		        "cancelLabel": "取消",
		        "fromLabel": "From",
		        "toLabel": "To",
		        "customRangeLabel": "自定义",
		        "weekLabel": "W",
		        "daysOfWeek": [
		            "日",
		            "一",
		            "二",
		            "三",
		            "四",
		            "五",
		            "六"
		        ],
		        "monthNames": [
		            "一月",
		            "二月",
		            "三月",
		            "四月",
		            "五月",
		            "六月",
		            "七月",
		            "八月",
		            "九月",
		            "十月",
		            "十一月",
		            "十二月"
		        ],
		        "firstDay": 1
		    },
		    "alwaysShowCalendars": true,
		    "autoUpdateInput":false,
		    "opens": "right",
		    "buttonClasses": "btn btn-sm"
		}, function(start, end, label) {
		  console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
		});
		
		$('.date').on('apply.daterangepicker', function(ev, picker) {
            $(this).val(picker.startDate.format('YYYY/MM/DD') + ' - ' + picker.endDate.format('YYYY/MM/DD'));
        });

        $('.date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
        });
		
	});
</script>