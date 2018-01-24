<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<!--分页组件-->
<#import "../macro/page.ftl" as mypage />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EAPA管理平台--异常性能监控</title>
    <!-- Bootstrap Core CSS -->
    <#include "../include/header.ftl" />
    <link href="<@spring.url '/styles/datepicker.css' />" rel="stylesheet" type="text/css" />
    
    
    <style type="type/css">
      .breakText {
      	word-break:break-all;
      }
    </style>
    

<script>
function projectIdChange(){
            var projectId=$("#projectId ").val();
            $("#modules").empty();
            $("#modules").append("<option value=''>ALL</option>");
            if(projectId==""){
               return;
            }
            $.ajax({  
                url: 'ajax/moduleSearch.json?projectId='+projectId,    //后台webservice里的方法名称  
                type: "GET",  
                dataType: "json",  
                contentType: "application/json",  
                traditional: true,  
                success: function (data) { 
                	var  option; 
                	var modules=data.result;
                    for (var i in modules) {
                    if(i!==null&&i!==""){
                            option=new Option(modules[i], modules[i]);
	                        $("#modules ").append(option);                       
                    }  
                    }
                   
                },  
                error: function (msg) {  
                    alert("出错了！");  
                }  
            });            
          
       }
</script>
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
                    <div class="panel panel-default" style="margin-top: 10px">
                        <!-- /.panel-heading <div class="panel-heading" >异常信息</div> -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <form class="form-horizontal  container-fluid one-line-form"
                                      id="addForm" name="addForm" action="list">
                                    <!-- row one -->
                                    <div class="row">
                                        <div class="form-group">
                                        <label  class="col-md-1 control-label">项目</label>
                                            <div class="col-md-1">
                                                <select name="projectId" id="projectId" style="width:110px"  class="form-control" onchange="projectIdChange()">
                                                    <option value="">ALL</option>
                                                     <#list projects as project>
                                                     <option value="${project.projectId}">${project.projectName}</option>
                                                      </#list>
                                                </select>
                                            </div>
                                           
                                            <label class="col-md-1 control-label">环境</label>
                                            <div class="col-md-1">
                                                <select name="env" id="env" class="form-control">
                                                    <option value="">ALL</option>
                                                    <option value="DEV">DEV</option>
                                                    <option value="TEST">TEST</option>
                                                    <option value="PROD">PROD</option>
                                                </select>
                                            </div>
                                            <label class="col-md-1 control-label">Page Size</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                       name="pageSize" value="${page.pageSize}" />
                                            </div>
                                             <label class="col-md-1 control-label">异常内容</label>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control"
                                                name="errorMsg" value="${params.errorMsg!''}" />
                                            </div>
                                            
                                            
                                        </div>

                                        <div class="row">
                                        
                                            <label class="col-md-1 control-label">起始日期</label>
                                            <div class="col-md-1">
                                                <input type="text"  style="width:110px" class="form-control" id="startDate"
                                                       name="startDate" readonly value="${startDate!''}" />

                                            </div>
                                            <label class="col-md-1 control-label">结束日期</label>
                                            <div class="col-md-1">
                                                <input type="text"  style="width:110px" class="form-control" id="endDate"
                                                       name="endDate" readonly value="${endDate!''}" />
                                            </div>
                                            <label class="col-md-1 control-label">访问次数</label>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control"
                                                name="exceptionName" value="${params.callCount!''}" />
                                            </div>
                                            <label  class="col-md-1 control-label">平均响应时间</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                       name="ip" value="${params.avgCostTime!''}" />
                                            </div>
                                            <div class="col-md-1">
                                                <input type="submit" class="btn btn-primary" value="查询"
                                                style="margin-right: 10px" />
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <table style="margin-top: 20px" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>项目名称</th>
                                            <th>异常名称</th>
                                            <th>异常内容</th>
                                            <th>发生时间</th>
                                            <th>处理类</th>
                                            <th>处理方法</th>
                                            <th>环境</th>
                                            <th>IP</th>
                                            <th>详情</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <#list page.recordList as exception>
                                            <tr>
                                                <td>${exception.projectName}</td>
                                                <td>${exception.exceptionName}</td>
                                                <td>${exception.errorMsg}</td>
                                                <td>${(exception.occurTime?number_to_datetime)!"无"}</td>
                                                <td>${exception.handlerClass!''}</td>
                                                <td>${exception.handlerMethod!''}</td>
                                                <td>${exception.env!''}</td>
                                                <td>${exception.ip}</td>
                                                <td><button class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick='showDetail("+${exception.id}+")'>
													查看详情
													</button>
												</td>

                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <@mypage.page pageItem=page param="startDate=${page.startDate!''}&endDate=${page.endDate!''}&exceptionName=${params.exceptionName!''}&errorMsg=${params.errorMsg!''}&module=${params.module!''}&env=${params.env!''}&ip=${params.ip!''}&"
             url="/exception/list" />
            <!-- table info end -->
        </div>
    </div>
    <!-- /#page-wrapper -->
<!-- 模态框（Modal） -->

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div style="width:900px" class="modal-dialog">
		<div  class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					异常详情
				</h4>
			</div>
			<div class="modal-body" >
				<ul id="ajaxModel" name="ajaxModel" class="list-group">

				</ul>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->

</div>
</div>
<!-- /#wrapper -->

<#include "../include/footer.ftl" />
<!-- Bootstrap Core JavaScript -->
<script src="<@spring.url'/script/bootstrap/bootstrap-datepicker.js'/>"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $("#exc_li").css("backgroundColor", "#eee");
        
	    if($("#startDate").val()==null){
	    	var timestamp = Date.parse(new Date());
	    	$("#startDate").val(timestamp);
	    }
	    
        $("#startDate").datepicker({
            format: 'yyyy-mm-dd'
        });
        
        if($("#endDate").val()==null){
	    	var timestamp = Date.parse(new Date());
	    	$("#endDate").val(timestamp);
	    }
        $("#endDate").datepicker({
            format: 'yyyy-mm-dd'
        });
        
       $("#projectId").val("${params.projectId!''}");
       $("#modules").val("${params.module!''}");
       $("#env").val("${params.env!''}");
    });
    
     
</script>

<script type="application/javascript">

	function processUndefined(dataValue){
		if (dataValue === undefined)
		{
		   return "";
		}
		return dataValue;
	}

	function showDetail(excepionInfoId ){
		if(excepionInfoId==null){
		  return;
		}
 	    /**
         * ajax方式处理
         * */
         
        $.ajax({
            type: "GET",
            url: 'ajax/detail.json?excepionInfoId='+excepionInfoId,
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',

            success: function (ajaxModel) {
                if (ajaxModel.status == 0) {

                    if(ajaxModel.result!= null) {
                    var jsonObj = eval('(' + ajaxModel.result + ')');

               
                    var requestLog="";  						                  
                           requestLog +="<table class='table table-striped table-bordered' id='dataTables-example' sytle='width:90%'>"+
                                        "<tr>"+
                                            "<th style='text-align: center;min-width:90px'>项目名称</th>"+
                                            "<td >"+processUndefined(jsonObj.projectName)+"</td>"+
                                            "<th style='text-align: center;min-width:75px' class='col-md-1'>子项目</th>"+
                                            "<td >"+processUndefined(jsonObj.module)+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                           "<th style='text-align: center'>异常名称</th>"+
                                            "<td >"+processUndefined(jsonObj.exceptionName)+"</td>"+
                                            "<th style='text-align: center' class='col-md-1'>发生时间</th>"+
                                            "<td >"+processUndefined(unix_to_datetime(jsonObj.occurTime))+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<th style='text-align: center'>处理类/方法</th>"+
                                            "<td  colspan=3>"+processUndefined(jsonObj.handlerClass)+"."+processUndefined(jsonObj.handlerMethod)+"()</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<th style='text-align: center'>URI</th>"+
                                            "<td  colspan=3>"+"</td>"+
                                        "</tr>"+
                                        
                                        "<tr>"+
                                            "<th style='text-align: center'>请求方法</th>"+
                                            "<td >"+processUndefined(jsonObj.requestMethod)+"</td>"+
                                            "<th style='text-align: center' class='col-md-1'>环境</th>"+
                                            "<td >"+processUndefined(jsonObj.env)+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<th style='text-align: center'>IP</th>"+
                                            "<td  colspan=3>"+processUndefined(jsonObj.ip)+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                        	"<th style='text-align: center'>请求参数</th>"+
                                            "<td  colspan=3 style='word-break:break-all;'>"+processUndefined(jsonObj.parameter)+"</td>"+
 										"</tr>"+
 										"<tr>"+
                                        	"<th style='text-align: center'>请求头部</th>"+
                                            "<td  colspan=3 style='word-break:break-all;'>"+processUndefined(jsonObj.headers)+"</td>"+
 										"</tr>"+
                                        "<tr>"+
                                        	"<th style='text-align: center'>异常信息</th>"+
                                            "<td  colspan=3 style='word-break:break-all;'>"+processUndefined(jsonObj.errorMsg)+"</td>"+
 										"</tr>"+
 										"<tr>"+
                                        	"<th style='text-align: center'>其他信息</th>"+
                                            "<td  colspan=3 >"+processUndefined(jsonObj.extraInfomation)+"</td>"+
 										"</tr>"+
                                "</table>";

                        $("#ajaxModel").html(requestLog);
                    }else{
                        $("#ajaxModel").html("没有找到请求日志详情！");
                    }
                } else {
                    $("#ajaxModel").css("color", "red").html(ajaxModel.message);
                }

            }
	 });
}

function unix_to_datetime(unix) {
    var now = new Date(parseInt(unix));
    return now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}
</script>
</body>

</html>
