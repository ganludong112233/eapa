<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<!--分页组件-->
<#import "../macro/page.ftl" as mypage />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EAPA管理平台--异常统计</title>
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
                    <div class="panel panel-default" style="margin-top: 10px">
                        <!-- /.panel-heading <div class="panel-heading">异常统计记录</div> -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <div>
                                    <form class="form-horizontal  container-fluid one-line-form"
                                          id="addForm" name="addForm" action="list">
                                        <!-- row one -->
                                        <div class="row">
                                            <div class="form-group" >
                                                <label class="col-md-1 control-label">项目名称</label>
                                                <div class="col-md-2">
                                                    <select name="projectName" id="projectName" class="form-control">
                                                        <option value="-1">ALL</option>
                                                    <#list projectList as project>
                                                        <option value="${project.projectName}">${project.projectName}</option>
                                                    </#list>
                                                    </select>
                                                </div>

                                                <label class="col-md-1 control-label">起始日期</label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="startDate"
                                                           name="startDate" readonly value="${startDate}"/>

                                                </div>
                                                <label class="col-md-1 control-label">结束日期</label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="endDate"
                                                           name="endDate" readonly value="${endDate}"/>
                                                </div>
                                                <div class="col-md-1">
                                                    <input type="submit" class="btn btn-primary" value="查询"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div>
                                    <table class="table table-striped table-bordered table-hover"
                                           id="dataTables-example" style="text-align: center">
                                        <thead>
                                        <tr>
                                            <th style="text-align: center">项目名称</th>
                                            <th style="text-align: center">正式环境异常数</th>
                                            <th style="text-align: center">测试环境异常数</th>
                                            <th style="text-align: center">异常总数</th>
                                            <th style="text-align: center">异常趋势</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list resultList as item>
                                        <tr>
                                            <td>${item.projectName!''}</td>
                                            <td>${item.proExceptionTotal!''}</td>
                                            <td>${item.testExceptionTotal!''}</td>
                                            <td>${item.exceptionTotal!''}</td>
                                        <#--<td><a href="javascript:void(0)" action="ajax/exceptionTrend.json?projectName=${item.projectName}&articleId=${item.module}"-->
                                        <#--class="black" id="showDetailTrend">详情</a></td>-->
                                            <td>
                                                <ibutton class="btn btn-primary" id="detail" data-toggle="modal"
                                                        data-target="#exceptionDetailModal"
                                                        onclick="showDetailTrend('${item.projectName}')">
                                                    详情
                                                </ibutton>
                                            </td>
                                        <#--<td><button class="btn btn-primary" data-toggle="modal"-->
                                        <#--data-target="#exceptionDetailModal">-->
                                        <#--详情-->
                                        <#--</button></td>-->
                                        <#--<td><a href="<@spring.url '/admin/statistic/detail?projectName=${item.projectName}&module=${item.module}&startDate=${startDate}&endDate=${endDate}'/>">详情</a></td>-->

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
        <#--<@mypage.page pageItem=page param="startDate=${startDate}&endDate=${endDate}&" url="/admin/articles/commentList" />-->
            <!-- table info end -->
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- 模态框（Modal） -->
<div class="modal fade" id="exceptionDetailModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div style="width: 600px;" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    异常详情统计
                </h4>
            </div>
            <div class="modal-body">
           <ul id="ajaxModel" name="ajaxModel" class="list-group">

				</ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

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

            $("#projectName").val("${projectName}");
        });

        function showDetailTrend(projectName) {
            var searchTrendReq = new Object();
            searchTrendReq.projectName = projectName;
            //searchTrendReq.module = module;
            searchTrendReq.startDate = $("#startDate").val();
            searchTrendReq.endDate = $("#endDate").val();

            $.ajax({
                type: "POST",
                url: 'ajax/exceptionTrend.json',
                dataType: "json",
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(searchTrendReq),
                async: true,
                success: function (ajaxModel) {
                    if (ajaxModel.status == 0) {
                        if (ajaxModel.result != null) {
                            var json = JSON.stringify(ajaxModel.result);
                            var result = eval('(' + json + ')');
                            var exceptionTable = "";

                            exceptionTable += "<table class='table table-striped table-bordered table-hover' id='dataTables-example' >"
                                    + "<tr>"
                                    + "<th style='text-align: center' class='col-md-2'>统计日期</th>"
                                    + "<th style='text-align: center' class='col-md-2'>正式环境(异常总数/日)</th>"
                                    + "<th style='text-align: center' class='col-md-2'>测试环境(异常总数/日)</th>"
                                    + "</tr>";
                            for (var i = 0; i < result.length; i++) {
                                exceptionTable += "<tr>"
                                + "<td style='text-align: center' class='col-md-1'>"+ result[i].date + "</td>"
                                + "<td style='text-align: center' class='col-md-1'>"+ result[i].proExceptionDayTotal + "</td>"
                                + "<td style='text-align: center' class='col-md-1'>"+ result[i].testExceptionDayTotal + "</td>"
                                + "</tr>";
                            }
                            exceptionTable += "</table>";
                            $("#ajaxModel").html(exceptionTable);
                        } else {
                            $("#ajaxModel").html("没有统计详情！");
                        }
                    } else {
                        $("#ajaxModel").css("color", "red").html(ajaxModel.message);
                    }
                }
            });
        }
    </script>

</body>

</html>