<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <!--顶部导航栏-->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">EAPA管理平台</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-left">
        <li id="pro_li" style="background-color: transparent">
            <a href="<@spring.url '/project/list' />"><i class="fa fa-desktop fa-fw"></i>&nbsp;项目管理</a>
        </li>
    </ul>
   
    <ul class="nav navbar-top-links navbar-left" role="tablist">
        <li role="presentation" class="dropdown " id="data_s">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <span class="fa fa-bar-chart-o"></span> 异常管理
            </a>
            <ul class="dropdown-menu" role="role">
                <li>
                    <a href="<@spring.url '/exception/list' />">&nbsp;异常记录</a>
                </li>
                <li>
                    <a href="<@spring.url '/statistic/list' />">&nbsp;异常统计</a>
                </li>
            </ul>
        </li>
    </ul>
    <ul class="nav navbar-top-links navbar-left" role="tablist">
        <li role="presentation" class="dropdown " id="data_s">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <span class="fa fa-bar-chart-o"></span> 性能统计
            </a>
            <ul class="dropdown-menu" role="role">
                <li>
                    <a href="<@spring.url '/performance/chart' />">&nbsp;概览</a>
                </li>
                <li>
                    <a href="<@spring.url '/performance/chart' />">&nbsp;流量分析</a>
                </li>
                <li>
                    <a href="<@spring.url '/performance/list' />">&nbsp;性能分析</a>
                </li>
            </ul>
        </li>
    </ul>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a>欢迎您&nbsp;&nbsp;&nbsp;&nbsp;<strong>
            <#if userInfo??>
            ${userInfo}
            <#else>
                未知用户
            </#if>
            </strong></a>
        </li>
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-searchUser fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-searchUser">
                <li><a href="<@spring.url '/logout' />"><i class="fa fa-sign-out fa-fw"></i>退出系统</a>
                </li>
            </ul>
            <!-- /.dropdown-searchUser -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->
</nav>
