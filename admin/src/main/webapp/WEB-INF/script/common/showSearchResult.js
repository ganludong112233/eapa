/**
 * 显示搜索结果页,这个只是供后台测试使用
 * Created by machangsheng on 15/10/31.
 */

/**
 * 显示人员类的搜索结果
 * @param searchResult 搜索结果
 */
function showPersonResult(searchResult) {
    var statusArray = new Array("试用", "正式", "临时", "试用延期", "解聘", "离职", "退休", "无效");

    //搜索结果,以一个TABLE来显示
    var table = generateTableHead(searchResult);

    //以下各行都是显示具体的数据了
    var items = searchResult.rsultList;
    for (var i = 0; i < items.length; i++) {

        table += "<tr><td rowspan='5' width='20%'>ID:" + items[i].id + "<br/>照片:<img width='100px' height='100px' title='"
            + items[i].imagepath + "' /></td></tr>"
        //显示名称和级别
        var tr = "<tr>";
        tr += "<td width='10%'>姓名:</td><td width='30%'>" + items[i].username + "</td>";
        tr += "<td wdith='10%'>级别:</td><td width='30%'>" + items[i].seclevel + "</td>";
        tr += "</tr>";
        table += tr;

        //分部和部门
        tr = "<tr>";
        tr += "<td>分部:</td><td>" + items[i].subcompanyname + "</td>";
        tr += "<td>部门:</td><td>" + items[i].departmentname + "</td>";
        tr += "</tr>";
        table += tr;

        //手机和办公电话
        tr = "<tr>";
        tr += "<td>手机:</td><td>" + items[i].mobile + "</td>";
        tr += "<td>座机:</td><td>" + items[i].telephone + "</td>";
        tr += "</tr>";
        table += tr;

        //邮箱和状态
        tr = "<tr>";
        tr += "<td>邮箱:</td><td>" + items[i].email + "</td>";
        tr += "<td>状态:</td><td>" + statusArray[items[i].status] + "(" + items[i].status + ")</td>";
        tr += "</tr>";
        table += tr;
    }

    table += "</table>";

    $("#errMsg").html(table);
}
/**
 * 显示文档类的搜索结果
 * @param searchResult 结果参数
 */
function showDocumentResult(searchResult){

    //搜索结果,以一个TABLE来显示
    var table = generateTableHead(searchResult);

    //以下各行都是显示具体的数据了
    var items = searchResult.rsultList;
    for (var i = 0; i < items.length; i++) {
        //标题
        table += "<tr><td width='10%'>标题:</td><td ><a href='#' title='"+items[i].id+"'>"
            + items[i].name + "</a></td></tr>";
        //简介
        table += "<tr><td>简介:</td><td style='text-align: left'>" + items[i].summary + "</td></tr>";
        //日期
        table += "<tr><td>创建时间:</td><td>" + items[i].createTime + "</td></tr>";
        //加空行,来使得显示更易读
        table += "<tr><td colspan='2' style='height: 20px'></td></tr>";
    }
    table += "</table>";

    $("#errMsg").html(table);

}
/**
 * 显示流程类的搜索结果
 * @param searchResult 结果参数
 */
function showRequestResult(searchResult){
    //搜索结果,以一个TABLE来显示
    var table = generateTableHead(searchResult);

    //以下各行都是显示具体的数据了
    var items = searchResult.rsultList;
    for (var i = 0; i < items.length; i++) {
        //标题
        table += "<tr><td width='10%'>流程标题:</td><td colspan='3'><a href='#' title='"+items[i].id+"'>"
            + items[i].requestname + "</a></td></tr>";
        //发起人
        table += "<tr><td>发起人:</td><td style='text-align: left;width:40%'>" + items[i].faqr + "</td>";
        //日期
        table += "<td width='10%'>创建时间:</td><td>" + items[i].createTime + "</td></tr>";
        //加空行,来使得显示更易读
        table += "<tr><td colspan='4' style='height: 20px'></td></tr>";
    }
    table += "</table>";

    $("#errMsg").html(table);

}

/**
 * 显示所有的搜索结果
 * @param searchResult 结果参数
 */
function showAllResult(searchResult){
    var table = generateTableHead(searchResult);

    var table = "<table class='table table-striped table-bordered table-hover'>";
    //第一行,显示总信息
    table += "<tr><td colspan='5' style='text-align: center'>";
    table += "共找到<strong>" + searchResult.numFound
        + "</strong>条记录, 用时<strong>" + searchResult.qtime + "</strong>毫秒";
    if(searchResult.numFound == 0){
        table +=" </td></tr></table>";
        $("#errMsg").html(table);
        return;
    }

    var rsultList = searchResult.rsultList;

    //1. 处理facet
    var facetItem = rsultList[rsultList.length - 1].facet;
    table += "&nbsp;&nbsp;其中:";
    for(var j=0;j<facetItem.length;j++) {
        table += facetItem[j].facetKey + "<strong>" + facetItem[j].facetValue + "</strong>&nbsp;&nbsp;";
    }
    table += "</td></tr></table>";

    //2. 处理具体的每个文档
    for(var k = 0; k < rsultList.length - 1; k++){
        var item = rsultList[k];

        var items = item.groupList;
        table += "<table class='table table-striped table-bordered table-hover'>";

        if(item.groupKey == "人员"){
            var statusArray = new Array("试用", "正式", "临时", "试用延期", "解聘", "离职", "退休", "无效");

            table += "<tr><td colspan='5'>人员信息";
            if(item.numFound > 5) {
                table += "<a href='#'>查看更多</a>";
            }
            table += "</td></tr>";
            for (var i = 0; i < items.length; i++) {
                table += "<tr><td rowspan='5' width='20%'>ID:" + items[i].id + "<br/>照片:<img width='100px' height='100px' title='"
                    + items[i].imagepath + "' /></td></tr>"
                //显示名称和级别
                var tr = "<tr>";
                tr += "<td width='10%'>姓名:</td><td width='30%'>" + items[i].username + "</td>";
                tr += "<td wdith='10%'>级别:</td><td width='30%'>" + items[i].seclevel + "</td>";
                tr += "</tr>";
                table += tr;

                //分部和部门
                tr = "<tr>";
                tr += "<td>分部:</td><td>" + items[i].subcompanyname + "</td>";
                tr += "<td>部门:</td><td>" + items[i].departmentname + "</td>";
                tr += "</tr>";
                table += tr;

                //手机和办公电话
                tr = "<tr>";
                tr += "<td>手机:</td><td>" + items[i].mobile + "</td>";
                tr += "<td>座机:</td><td>" + items[i].telephone + "</td>";
                tr += "</tr>";
                table += tr;

                //邮箱和状态
                tr = "<tr>";
                tr += "<td>邮箱:</td><td>" + items[i].email + "</td>";
                tr += "<td>状态:</td><td>" + statusArray[items[i].status] + "(" + items[i].status + ")</td>";
                tr += "</tr>";
                table += tr;
            }


        }else if(item.groupKey == "流程"){
            table += "<tr><td colspan='4'>流程信息";
            if(item.numFound > 5) {
                table += "<a href='#'>查看更多</a>";
            }
            table += "</td></tr>";

            for(var i=0;i<items.length; i++){
                //标题
                table += "<tr><td width='10%'>流程标题:</td><td colspan='3'><a href='#' title='"+items[i].id+"'>"
                    + items[i].requestname + "</a></td></tr>";
                //发起人
                table += "<tr><td>发起人:</td><td style='text-align: left;width:40%'>" + items[i].faqr + "</td>";
                //日期
                table += "<td width='10%'>创建时间:</td><td>" + items[i].createTime + "</td></tr>";
                //加空行,来使得显示更易读
                table += "<tr><td colspan='4' style='height: 20px'></td></tr>";
            }
        }else if(item.groupKey == "文档"){
            table += "<tr><td colspan='2'>文档信息";
            if(item.numFound > 5) {
                table += "<a href='#'>查看更多</a>";
            }
            table += "</td></tr>";
            for(var i=0;i<items.length; i++){
                //标题
                table += "<tr><td width='10%'>标题:</td><td ><a href='#' title='"+items[i].id+"'>"
                    + items[i].name + "</a></td></tr>";
                //简介
                table += "<tr><td>简介:</td><td style='text-align: left'>" + items[i].summary + "</td></tr>";
                //日期
                table += "<tr><td>创建时间:</td><td>" + items[i].createTime + "</td></tr>";
                //加空行,来使得显示更易读
                table += "<tr><td colspan='2' style='height: 20px'></td></tr>";
            }

        }else{
            continue;
        }

        table +="</table>";
    }
    //这就是一个结束了,不要忘记前端显示
    $("#errMsg").html(table);
}

/**
 * 构造表头及第一行
 * @param searchResult 表头
 * @returns {string}
 */
function generateTableHead(searchResult){
    //搜索结果,以一个TABLE来显示
    var table = "<table class='table table-striped table-bordered table-hover'>";
    //第一行,显示总信息
    table += "<tr><td colspan='5' style='text-align: center'>";
    table += "共找到<strong>" + searchResult.numFound
        + "</strong>条记录, 用时<strong>" + searchResult.qtime + "</strong>毫秒";
    table += "</td></tr>";

    return table;
}

