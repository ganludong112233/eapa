
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<#import "../admin/macro/taglib.ftl" as tag />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TStream统计管理平台</title>
    <!-- Bootstrap Core CSS -->
    <link href="${contextPath}/styles/bootstrap.min.css" rel="stylesheet" type="text/css" />

    <style type="text/css">
        .statusErrorIcon {
            font-size: 25px;
            color: #a94442;
            margin-right: 10px;
        }
    </style>

</head>

<body>
<div id="wrapper">

    <!-- Navigation -->
    <div id="page-wrapper">
        <div class="row">
            <label class="col-lg-1">&nbsp;</label>
            <div class="col-lg-10">
                <h1 class="page-header" style="text-align: center">访问错误</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row" style="text-align: center">
            <label class="col-lg-1">&nbsp;</label>
            <div class="col-lg-10">
                <div class="panel panel-default">
                    <div class="panel-heading" style="text-align: left">${title}</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body"
                        <div class="dataTable_wrapper">
                            <div class="alert alert-danger">
                                            <span class="glyphicon glyphicon-remove-sign statusErrorIcon"></span>
                                            <strong>${message}</strong>
                                            &nbsp;&nbsp;<a href="<@spring.url '/admin/index'/>">首页</a>
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
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

</body>

</html>
