<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<!--分页组件-->
<#import "../macro/page.ftl" as mypage />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EAPA管理平台</title>
    <!-- Bootstrap Core CSS -->
    <#include "../include/header.ftl" />
    <style type="text/css">
        a, span {
            margin-left: 10px;
        }

        a.currRank {
            font-weight:bold;
            color:blue
        }
    </style>
</head>

<body>

<div id="wrapper">

<#include "../include/navbar.ftl" />
<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>

    <div id="page-wrapper">
        <!-- here is the content -->
        <div class="row">
            <!-- /.col-lg-12 -->

            <!-- table info begin -->
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
               
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <form class="form-horizontal  container-fluid one-line-form"
                                      id="addForm" name="addForm" action="chart">
                                    <!-- row one -->
                                    <div class="row">
                                        <div class="form-group">
                                        <label class="col-md-1 control-label">起始日期</label>
                                                <div class="col-md-1">
                                                    <input type="text" style="width:110px" class="form-control" id="startDate"
                                                           name="startDate" readonly value="${startDate!''}"/>

                                                </div>
                                                <label class="col-md-1 control-label">结束日期</label>
                                                <div class="col-md-2">
                                                    <input type="text" style="width:110px" class="form-control" id="endDate"
                                                           name="endDate" readonly value="${endDate!''}"/>
                                                </div>
											<label class="col-md-1 control-label">最近</label>
                                            <div style="width:150px" class="col-md-1">
                                                    <select name="time" id="time" class="form-control">
                                                        <option value="1">30 minutes</option>
                                                        <option value="1">1 hour</option>
                                                        <option value="1">12 hour</option>
                                                    </select>
                                                </div>
                                            <label class="col-md-1 control-label">项目名称</label>
                                            <div class="col-md-2">
                                                    <select name="projectName" id="projectName" class="form-control">
                                                        <option value="-1">ALL</option>
                                                    <#list projectList as project>
                                                        <option value="${project.projectName}">${project.projectName}</option>
                                                    </#list>
                                                    </select>
                                                </div>
                                            <div class="col-md-1">
                                                <input type="submit" class="btn btn-primary" value="统计"
                                                       style="margin-right: 10px" />
                                                <#--<input type="button" class="btn btn-success" value="导出"-->
                                                       <#--style="margin-right: 10px" onclick="exportData()"/>-->
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <span id="apiStatisticList" class="hide">${apiStatisticList!''}</span>
                                <span id="perfstatTrendList" class="hide">${perfstatTrendList!''}</span>
                                <div>
	                                <div id="main" style="width: 45%;height: 500px; float:left"></div>
	                                <div id="topright" style="width: 45%;height: 500px;float:left"></div>
	                            </div>
                            </div>
                            <!-- /.table-responsive -->
                            <div class="col-md-6 control-label">
                            <label class="col-md-4 control-label">访问量最多前10个接口</label></br>
	                            <table class="table table-striped table-bordered table-hover" style="margin-left:10px;width: 80%" id="dataTables-example">
	                                <thead>
	                                <tr>
	                                    <th style="text-align: center" class="col-md-2">序号</th>
	                                    <th style="text-align: center" class="col-md-2">url</th>
	                                    <th style="text-align: center" class="col-md-2">次数</th>
	                                    <th style="text-align: center" class="col-md-2">平均响应时间</th>
	                                </tr>
	                                </thead>
	                                <tbody>
	                                 <#if resultList.apiStatisticList??>
			                              <#list resultList.apiStatisticList as item>
			                                  <tr>
			                                    <td style="text-align: center" class="col-md-2">${item_index}</td>
			                                    <td style="text-align: center" class="col-md-2">${item.api}</td>
			                                    <td style="text-align: center" class="col-md-2">${item.callCount}</td>
			                                    <td style="text-align: center" class="col-md-2">${item.avgCostTime}</td>
			                                  </tr>
		                                  </#list>
	                                  </#if>
	                                 </tbody>
	                            </table>
	                            </div> 
	                            <div class="col-md-6 control-label">
	                            <label class="col-md-4 control-label">性能监控信息(近五天)</label></br>
	                             <table class="table table-striped table-bordered table-hover" style="margin-left:10px;width: 80%" id="dataTables">
	                                <thead>
	                                <tr>
	                                    <th style="text-align: center" class="col-md-2">日期</th>
	                                    <th style="text-align: center" class="col-md-2">PV</th>
	                                    <th style="text-align: center" class="col-md-2">TPM</th>
	                                    <th style="text-align: center" class="col-md-2">高峰时间点
	                                    </th>
	                                </tr>
	                                </thead>
	                                <tbody>
	                                 <#if resultList.perfstatTrendList??>
			                              <#list resultList.perfstatTrendList as item>
	                                 <tr>
	                                    <td style="text-align: center" class="col-md-2">${item.time}</td>
	                                    <td style="text-align: center" class="col-md-2">${item.callCount}</td>
	                                    <td style="text-align: center" class="col-md-2">${item.tpm}</td>
	                                    <td style="text-align: center" class="col-md-2">${item.peakTime}</td>
	                                 </tr>
	                                  </#list>
	                                  </#if>
	                                </tbody>
	                            </table>
	                       </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <!-- table info end -->
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<#include "../include/footer.ftl" />
<!-- Bootstrap Core JavaScript -->

<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script src="<@spring.url'/script/bootstrap/bootstrap-datepicker.js'/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
    $("#data_s").css("backgroundColor", "#eee");

            $("#startDate").datepicker({
                format: 'yyyy-mm-dd'
            });

            $("#endDate").datepicker({
                format: 'yyyy-mm-dd'
            });
        initLeftTableData();
        initRightTableData();
    });
    	var urls = new Array();
        var normal = new Array();
		var slow = new Array();
		var abnormal =new Array();
		var average = new Array();
		
		function initLeftTableData(){
		    	var apiStatisticList=JSON.parse($("#apiStatisticList").text());
		        var tdHtml = "";
		        for(var i=0; i<apiStatisticList.length;i++){
		            tdHtml +="<tr>";
		            tdHtml +="<td align='center'>" + i + "</td>";
		            tdHtml +="<td align='center'>" + apiStatisticList[i].api + "</td>";
		            tdHtml +="<td align='center'>" + apiStatisticList[i].callCount + "</td>";
		            tdHtml +="<td align='center'>" + apiStatisticList[i].avgCostTime + "</td>";
		            tdHtml +="</tr>";
		            urls.push(i+1);
		            var responseSpeedsMap=apiStatisticList[i].reponseSpeedsMap;
		            if(typeof(responseSpeedsMap.normal)=="undefined"){
		            	mormal.push(0);
		            }else{
		           		normal.push(responseSpeedsMap.normal);
		           	}
		           	if(typeof(responseSpeedsMap.slow)=="undefined"){
		           		slow.push(0);
		           	}else{
		            	slow.push(responseSpeedsMap.slow);
		            }
		            if(typeof(responseSpeedsMap.abnormal)== "undefined"){
		            	abnormal.push(0);
		            }else{
		            	abnormal.push(responseSpeedsMap.abnormal);
		            }
		            if(typeof(responseSpeedsMap.average)=="undefined"){
		            	average.push(0);
		            }else{
		            	average.push(responseSpeedsMap.average);
		            }
		        
		        }
		
		        //$("#dataTables-example > tbody").html(tdHtml); 
		        //$("#dataTables > tbody").html(tdHtml);
		}
		var timeArray = new Array();
		var callCountArray = new Array();
		function initRightTableData(){
			   var perfstatTrendList=JSON.parse($("#perfstatTrendList").text());
			   for(var k = 0; k < perfstatTrendList.length; k++){
			        timeArray.push(perfstatTrendList[k].time);
			        callCountArray.push(perfstatTrendList[k].callCount);
			   } 
		}
		 
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });

    // 使用
    require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line' // 使用拆线图
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main'));
                var topRightChart=ec.init(document.getElementById('topright'));
                var option = {
                    //title
                    title : {
                        text: /*$('#startDate').val() + '-' + $('#startDate').val() + */'接口访问量统计'
                    },
                    tooltip: {
                        show: true,
                        trigger: 'axis'
                    },
                     legend: {
        					data:['正常','缓慢','异常']
    				},
                    //工具栏
                    toolbox: {
                        show : false,
                        feature : {
                            dataView : {show: true, readOnly: true},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,//启用拖放重计算
                    xAxis : [
                        {
                            type : 'category',
                            data :urls
                        }
                    ],
                    yAxis : [
                        {
                        	name : '访问量',
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'异常',
                            type:'bar',
                            stack: '搜索引擎',
                            data:abnormal
                        },
                         {
				            name:'缓慢',
				            type:'bar',
				            stack: '搜索引擎',
				            data:slow
				        },
				        {
				            name:'正常',
				            type:'bar',
				            stack: '搜索引擎',
				            data:normal
				        },
				        {
				            name:'平均',
				            type:'line',
				            stack: '搜索引擎',
				            data:average,
				            markPoint : {
                                data : [
                                    {type : 'max', name: '当日最高'},
                                    {type : 'min', name: '当日最低'}
                                ]
                            },
                            markLine : {
                                data : [
                                    {type : 'average', name: '当日平均'}
                                ]
                            }
				        }
                    ]
                };
                
                // 为echarts对象加载数据
             
                myChart.setOption(option);
               
			                
			    toprightoption = {
				    title: {
				        text: '每日访问量'
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['每日访问量']
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: timeArray
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: [
				        {
				            name:'访问总量',
				            type:'line',
				            stack: '总量',
				            data:callCountArray
				        }
				        
				    ]
			};
			// 为echarts对象加载数据
                topRightChart.setOption(toprightoption);
            }
    );
</script>

</body>

</html>
