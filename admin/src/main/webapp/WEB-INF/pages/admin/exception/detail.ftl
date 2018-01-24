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
    <link href="../../styles/datepicker.css" rel="stylesheet" type="text/css" />

</head>

<body>

<div id="wrapper">

    <#include "../include/navbar.ftl" />
    <#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
    <div id="page-wrapper">
        <!-- here is the content -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">异常信息</h1>
            </div>
            <!-- /.col-lg-12 -->

            <!-- table info begin -->
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">异常信息详情</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <tr>
                                            <th style="text-align: center" class="col-md-1">项目名称</th>
                                            <td class="col-md-5">${exception.projectName!''}</td>
                                            <th style="text-align: center" class="col-md-1">子项目名称</th>
                                            <td class="col-md-5">${exception.module!''}</td>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center" class="col-md-1">异常名称</th>
                                            <td class="col-md-5">${exception.exceptionName!''}</td>
                                            <th style="text-align: center" class="col-md-1">发生时间</th>
                                            <td class="col-md-5">${(exception.occurTime?number_to_datetime)!""}</td>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center" class="col-md-1">处理类</th>
                                            <td class="col-md-5">${exception.handlerClass!''}</td>
                                            <th style="text-align: center" class="col-md-1">处理方法</th>
                                            <td class="col-md-5">${exception.handlerMethod!''}</td>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center" class="col-md-1">请求方法</th>
                                            <td class="col-md-5">${exception.requestMethod!''}</td>
                                            <th style="text-align: center" class="col-md-1">环境</th>
                                            <td class="col-md-5">${exception.envirionment!''}</td>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center" class="col-md-1">IP</th>
                                            <td class="col-md-5">${exception.ip!''}</td>
                                            <th style="text-align: center" class="col-md-1">备注</th>
                                            <td class="col-md-5">${exception.extraInfomation!''}</td>
                                        </tr>

                                </table>
                                <table cellpadding="1" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <th style="text-align: center" class="col-md-1">URI</th>
                                        <td class="col-md-11">${exception.uri!''}</td>
                                    </tr>
                                    <tr>
                                        <th style="text-align: center" class="col-md-1">详细信息</th>
                                        <td class="col-md-11">${exception.errorMsg!''}</td>
                                    </tr>
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
            <!-- table info end -->
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<#include "../include/footer.ftl" />
<!-- Bootstrap Core JavaScript -->
<script src="../../script/bootstrap/bootstrap-datepicker.js"></script>
</body>

</html>
