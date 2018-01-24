<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>eapa管理平台</title>
    <!-- Bootstrap Core CSS -->
    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="styles/bootstrapValidator.min.css?v=1.0" rel="stylesheet" type="text/css" />

    <style type="text/css">
        .leftLabel {
            text-align: right;
            line-height: 34px;
        }

        .loginTitle {
            font-size: 28px;
            font-family: "Microsoft YaHei";
            text-align: center;
            margin: 10px;
        }

        .panel-default > .panel-heading {
            background-color: #33C3A8;
        }

        .login-panel {
            margin-top: 20%;
        }
    </style>

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="login-panel panel panel-default">
                <div class="panel-heading ">
                    <h3 class="panel-title loginTitle">EAPA管理平台登录</h3>
                </div>
                <div class="panel-body">
                    <form method="post" id="loginForm" class="form-horizontal" action="doLogin">
                        <div class="form-group">
                            <label class="col-md-3 leftLabel">用户名:</label>

                            <div class="col-md-8">
                                <input class="form-control" placeholder="Username" name="userName" type="text"
                                       autofocus>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 leftLabel">密码:</label>

                            <div class="col-md-8">
                                <input class="form-control" placeholder="Password" name="password" type="password"
                                       value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">&nbsp;</label>
                            <label class="col-md-8">
                                <#--<input name="remember" type="checkbox" value="Remember Me">&nbsp;&nbsp;记住我-->
                                <span style="color:red">${errorInfo}</span>
                            </label>
                        </div>
                        <!-- Change this to a button or input when using this as a form -->
                        <div class="form-group">
                            <label class="col-md-3">&nbsp;</label>

                            <div class="col-md-8">
                                <input type="submit" class="btn btn-normal btn-success" value="登录"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="script/jquery/jquery-1.11.3.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="script/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="script/bootstrap/bootstrapValidator.min.js?v=1.0"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $("#loginForm").bootstrapValidator({
            message: '信息有误',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            live: "disabled",
            fields: {
                username: {
                    message: '用户名填写错误!',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 5,
                            max: 30,
                            message: '用户名的长度应该在5-30之间'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '用户名只能由字母,数字和下划线组成'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '密码长度应该在6-30之间'
                        }
                    }
                }
            }
        });
/*
        $("#submitBtn").click(function () {
            $("#loginForm").bootstrapValidator('validate').submit();
        });*/
    });
</script>
</body>

</html>
