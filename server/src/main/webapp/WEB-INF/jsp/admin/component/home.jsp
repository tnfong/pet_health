<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/admin/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/css.jsp" %>
<body>
	<%@ include file="/WEB-INF/jsp/admin/common/loader.jsp" %>

    <div id="app" class="app app-header-fixed app-sidebar-fixed">
    	<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
    	<%@ include file="/WEB-INF/jsp/admin/common/sidebar.jsp" %>

		<div id="content" class="app-content">
			
			<!-- Panel list -->
			<div class="panel panel-inverse">
				<div class="panel-heading">
			    	<h4 class="panel-title">Doanh số</h4>
				    <div class="panel-heading-btn">
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand"><i class="fa fa-expand"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-success" data-toggle="panel-reload"><i class="fa fa-redo"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse"><i class="fa fa-minus"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove"><i class="fa fa-times"></i></a>
				    </div>
				</div>
				<div class="panel-body">
					<div id="analysis-month" class="analysis-month w-100" style="height: calc(100vh - 200px);">
						
					</div>
				</div>
			</div>
			<!-- * Panel list -->
		</div>
    </div>

	<%@ include file="/WEB-INF/jsp/admin/common/js.jsp" %>
	<script>
    	$(document).ready(function(){
    		loadDataAnalysis()
    	})

    	async function loadDataAnalysis(){
    		const res = await analysis.get()
    		const data = res.data
    		if(data.length > 0){
				var titles = [], series = []
				data.forEach((el) => {
					titles.push(el.time)
					series.push(el.totalPrice)
				})
    			loadWageChart("analysis-month", series, titles)
    		}
    	}

    	function loadWageChart(view, data, titles){
    		Highcharts.chart(view, {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: ''
			    },
			    xAxis: {
			        categories: titles
				},
			    plotOptions: {
			        area: {
			            marker: {
			                enabled: false,
			                symbol: 'circle',
			                radius: 2,
			                states: {
			                    hover: {
			                        enabled: true
			                    }
			                }
			            }
			        }
			    },
			    tooltip: {
			        shared: true,
			        headerFormat: '<span style="font-size:12px"><b>{point.key}</b></span><br>'
			    },
			    series: [
			    	{
			    		name: "KPI",
			    		data: data
			    	}
			    ]
			});
    	}
	</script>
</body>
</html>
