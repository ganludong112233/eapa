<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<#import "/spring.ftl" as spring/>
<!--分页组件-->
<#import "../macro/page.ftl" as mypage />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Eapa管理平台--项目管理</title>
    <!-- Bootstrap Core CSS -->
<#include "../include/header.ftl" />
    <link href="<@spring.url'/styles/datepicker.css'/>" rel="stylesheet" type="text/css"/>
<style>
.li_style {list-style-type:none;text-align: left;}
.div-a{ float:left;width:49%;margin:0px; } 
.div-b{ float:left;width:49%;margin:0px; } 
</style>
</head>

<body>

<div id="wrapper">

<#include "../include/navbar.ftl" />
<#setting datetime_format="yyyy-MM-dd HH:mm:ss.SSS"/>
    <div id="page-wrapper">
        <!-- here is the content -->
        <div class="row">
            <!-- /.col-lg-12 -->

            <!-- table info begin -->
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default" style="margin-top: 10px">
                        <!-- /.panel-heading  <div class="panel-heading">项目信息</div> -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <form class="form-horizontal  container-fluid one-line-form"
                                      id="addForm" name="addForm" action="list">
                                    <!-- row one -->
                                    <div style="margin-left: 6%" class="row">
                                        <label class="col-md-1 control-label">项目ID</label>
                                        <div class="col-md-2">
                                            <input type="text" class="form-control" id="projectId"
                                                   name="projectId" value="${projectId}" autocomplete="off"/>
                                        </div>

                                        <label class="col-md-1 control-label">项目名称</label>
                                        <div class="col-md-2">
                                                <select name="projectName" id="projectName" style="width:110px"  class="form-control" onchange="projectIdChange()">
                                                    <option value="">ALL</option>
                                                </select>
                                        </div>

                                        <label class="col-md-1 control-label">模块名称</label>
                                        <div class="col-md-2">
                                                <select name="moduleId" id="moduleId" style="width:110px"  class="form-control" onchange="projectIdChange()">
                                                    <option value="">ALL</option>
                                                </select>
                                        </div>

                                        <div class="col-md-2">
                                            <input type="submit" class="btn btn-primary" value="查询"
                                                   style="margin-right:20px"/>
                                            <ibutton class="btn btn-primary" data-toggle="modal"
                                                     data-target="#createProject"  onclick='modifyProject("","","")' 
                                            >创建</ibutton>
                                        </div>
                                    </div>
                                </form>
                                <table style="margin-top: 20px" class="table table-striped table-bordered table-hover"
                                       id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th style="text-align: center" class="col-md-1">项目</th>
                                        <th style="text-align: center" class="col-md-5" >模块信息</th>
                                        <th hidden></th>
                                        <th style="text-align: center" class="col-md-1">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        
                                    <#list resultList as item>
                                    <tr>

                                        <td align="center">
                                        <li class="li_style"><b>项目id</b>:${item.projectId!''}</li>
                                        <li class="li_style"><b>项目名称</b>:${item.projectName!''}</li>
                                        </td>
                                        <td align="center">
                                        	<div class="div-a">
                                        		<li class="li_style"><b>模块名称</b>:admin</li>
                                        		<li class="li_style"><b>邮箱</b>:test1@tcl.com</li>
                                        	</div>
                                        		<div class="div-b">
                                        		<li class="li_style"><b>模块名称</b>:api</li>
                                        		<li class="li_style"><b>邮箱</b>:test2@tcl.com</li>
                                        		</div>
                                        </td>	
                                        <td align="center">
                                        <a href="#"  data-toggle="modal" data-target="#createProject" onclick='modifyProject("${item.projectId}","${item.projectName}","")' >修改</a>
                                            <a href="<@spring.url '/project/delete?projectId=${item.projectId}'/>">删除</a>
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
        </div>
    </div>
</div>
<script type="application/javascript">   
	function modifyProject(projectId,projectName,toEmails){
		 $("#projectName_c").val(projectName);
		 $("#toEmails_c").val(toEmails);
		 $("#projectId_c").val(projectId);
	}	
</script>

<!-- 模态框（Modal） -->
<div class="modal fade" id="createProject" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    创建项目
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal  container-fluid one-line-form"
                      id="addForm" name="addForm" action="add">
                    <!-- row one -->
                   
                    <div style="margin-top: 10px" class="row">
                        <label class="col-md-2 control-label">项目名称</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="projectName_c"
                                   name="projectName_c" value=""/>
                        </div>
                    </div>

                    <!-- row two -->
                    <div style="margin-top: 10px" class="row">
                        <label class="col-md-2 control-label">收件人</label>
                        <div class="col-md-8">
                            <textarea class="form-control" rows="3" cols="50" id="toEmails_c" name="toEmails_c"
                                      placeholder="sili@tcl.com,qinhe@tcl.com"></textarea>
                        </div>
                    </div>

                <!-- row three -->
                <div style="margin-top: 10px" class="row"  hidden="true">
                    <label class="col-md-2 control-label">收信人</label>
                    <div class="col-md-8">
                        <textarea class="form-control" rows="3" cols="50" id="toPhones_c" name="toPhones_c"></textarea>
                    </div>
                </div>

                <!-- row four -->
                <div style="margin-top: 10px" class="row"  hidden="true">
                    <label class="col-md-2 control-label">异常警告</label>
                    <div class="col-md-8">
                        <textarea class="form-control" rows="3" cols="50" id="warnException_c" name="warnException_c"></textarea>
                    </div>
                </div>

                    <button type="button" style="float:right" class="btn btn-primary" data-dismiss="modal"
                            onclick="submitBtn()">提交
                    </button>
                     <span id="projectId_c" name="projectId_c" style="visibility:hidden"/>
                    <script type="application/javascript">
                        function submitBtn() {

                            var project = new Object();

                            project.projectId = $("#projectId_c").val();
                            project.projectName = $("#projectName_c").val();
                            project.toEmails = $("#toEmails_c").val();
                            //project.toPhones = $("#toPhones_c").val();
                            project.warnException = $("#warnException_c").val();
							var ajaxurl="ajax/projcetAdd.json";
                            if(project.projectId !=null&&project.projectId!=""){
                            	ajaxurl="ajax/modify.json";
                            }
                            $.ajax({
                                type: "POST",
                                url: ajaxurl,
                                dataType: "json",
                                contentType: 'application/json;charset=UTF-8',
                                data: JSON.stringify(project),
                                async: true,
                                success: function (ajaxModel) {
                                    if (ajaxModel.status == 0) {
                                        alert("success");
                                        window.location.reload(); 
                                        }
                                    else{
                   					 alert(ajaxModel.message);
                					}
                					}
                            });
                           
                        };
                       
                    </script>
                <#--<input type="submit" class="btn btn-default" >提交</input>-->
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<#include "../include/footer.ftl" />
<script type="application/javascript">
    $(document).ready(function () {
        $("#pro_li").css("backgroundColor", "#eee");
    });


</script>

</body>

</html>
