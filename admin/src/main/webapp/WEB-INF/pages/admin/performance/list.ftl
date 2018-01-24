<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<!--分页组件-->
<#import "../macro/page.ftl" as mypage />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EAPA管理平台--系统监控统计</title>
    <!-- Bootstrap Core CSS -->
<#include "../include/header.ftl" />
   <link href="<@spring.url'/styles/datepicker.css'/>" rel="stylesheet" type="text/css"/>

</head>

<body>

<div id="wrapper">

<#include "../include/navbar.ftl" />
<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
    <div id="page-wrapper">
        <!-- here is the content -->
        <div class="row">
            <!-- table info begin -->
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default" style="margin-top: 5px">
                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div>
                                    <form class="form-horizontal  container-fluid one-line-form"
                                          id="addForm" name="addForm" action="list">
                                        <!-- row one -->
                                        <div class="row">
                                            <div class="form-group" >
                                                <label class="col-md-1 control-label">监控项目</label>
                                                <div class="col-md-1">
                                                    <select name="projectName" id="projectName" class="form-control">
                                                        <option value="-1">ALL</option>
                                                    <#list projectList as project>
                                                        <option value="${project.projectId}">${project.projectName}</option>
                                                    </#list>
                                                    </select>
                                                </div>
											<label style="width:60px" class="col-md-1 control-label">环境</label>
                                            <div class="col-md-1">
                                                <select name="env" id="env" class="form-control">
                                                    <option value="">ALL</option>
                                                    <option value="TEST">TEST</option>
                                                    <option value="PRO">PRO</option>
                                                </select>
                                            </div>
                                            <label class="col-md-1 control-label">Page Size</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                       name="pageSize" value="${params.pageSize}" />
                                            </div>
                                             <label style="width:20px" class="col-md-1 control-label">ip</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                name="ip" value="${params.ip!''}" />
                                            </div>
                                             <label style="width:20px" class="col-md-1 control-label">api</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                name="api" value="${params.api!''}" />
                                            </div>
                                            <label class="col-md-1 control-label">平均访问时间</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                name="avgCostTime" value="${params.avgCostTime!''}" />
                                            </div>
                                            <label class="col-md-1 control-label">调用次数</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                name="callCount" value="${params.callCount!''}" />
                                            </div>
                                        </div>

                                        <div class="row">
                                         <label class="col-md-1 control-label">类名</label>
                                            <div class="col-md-2">
                                                <input type="text" class="form-control"
                                                name="className" value="${params.className!''}" />
                                            </div>
                                         <label class="col-md-1 control-label">方法名</label>
                                            <div class="col-md-1">
                                                <input type="text" class="form-control"
                                                name="methodName" value="${params.methodName!''}" />
                                            </div>
                                            
                                                <label class="col-md-1 control-label">起始日期</label>
                                                <div class="col-md-2">
                                                    <input type="text" style="width:110px" class="form-control" id="startDate"
                                                           name="startDate" readonly value="${startDate}"/>

                                                </div>
                                                <label class="col-md-1 control-label">结束日期</label>
                                                <div class="col-md-2">
                                                    <input type="text" style="width:110px" class="form-control" id="endDate"
                                                           name="endDate" readonly value="${endDate}"/>
                                                </div>
                                                <div class="col-md-1">
                                                    <input type="submit" class="btn btn-primary" value="查询"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div style="margin-top: 5px">
                                    <table class="table table-striped table-bordered table-hover"
                                           id="dataTables-example" style="text-align: center">
                                        <thead>
                                        <tr>
                                            <th style="text-align: center">项目名称</th>
                                            <th style="text-align: center">项目环境</th>
                                            <th style="text-align: center">IP</th>
                                            <th style="text-align: center">接口名称</th>
                                            <th style="text-align: center">类名.方法名</th>
                                            <th style="text-align: center">签名</th>
                                            <th style="text-align: center">请求次数</th>
                                            <th style="text-align: center">最大访问时间</th>
                                            <th style="text-align: center">最小访问时间</th>
                                            <th style="text-align: center">平均访问时间</th>
                                            <th style="text-align: center">记录起始时间</th>
                                            <th style="text-align: center">记录结束时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list page.recordList as item>
                                        <tr>
                                            <td>${item.projectName!''}</td>
                                            <td>${item.env!''}</td>
                                            <td>${item.ip!''}</td>
                                            <td>${item.api!''}</td>
                                            <td>${item.className!''}.${item.methodName!''}()</td>
                                            <td>${item.signature!''}</td>
                                            <td>${item.callCount!''}</td>
                                            <td>${item.maxCostTime!''}</td>
                                            <td>${item.minCostTime!''}</td>
                                            <td>${item.avgCostTime!''}</td>
                                            <td>${(item.startTime?number_to_datetime)!''}</td>
                                            <td>${(item.endTime?number_to_datetime)!''}</td>
                                        </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
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
       <@mypage.page pageItem=page param="startDate=${startDate}&endDate=${endDate}&" url="/performance/list" />
            <!-- table info end -->
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<#include "../include/footer.ftl" />
    <!-- Bootstrap Core JavaScript -->
    <script src="<@spring.url'/script/bootstrap/bootstrap-datepicker.js'/>"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#data_s").css("backgroundColor", "#eee");

            $("#startDate").datepicker({
                format: 'yyyy-mm-dd'
            });

            $("#endDate").datepicker({
                format: 'yyyy-mm-dd'
            });
           
            $("#projectName").val("${params.projectId!''}");
            
        });

    </script>

</body>

</html>