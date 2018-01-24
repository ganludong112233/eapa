<#--
    分页宏
    名称: page
    参数: pageItem 分页对象
    参数: url 请求URL
    参数: param 附加参数,可以没有,有的话,则是param1=value1&param2=value2&的形式,因为后面还要接参数
-->
<#macro page pageItem url param="">
    <#if pageItem.totalRecord gt 0>
    <div class="row">
        <div class="col-sm-5">
            <div class="dataTables_info" style="padding-top: 7px" >
                总共<strong>${pageItem.totalPage}</strong>页,
                <strong>${pageItem.totalRecord}</strong>条记录,
                当前显示第 <strong>${pageItem.startPos + 1}</strong>至
                第 <strong>${pageItem.startPos + pageItem.pageSize}</strong> 条记录</div>
        </div>
        <div class="col-sm-7">
            <div class="dataTables_paginate">
                <ul class="pagination">
                    <!-- prev page -->
                    <#if pageItem.currentPage == 1 >
                        <li class="paginate_button previous disabled" tabindex="0">
                            <a href="javascript:void(0)">上一页</a>
                        </li>
                    <#else>
                        <li class="paginate_button previous" tabindex="0">
                            <a href="<@spring.url '${url}?${param}currPage=${pageItem.lastPage}&pageSize=${pageItem.pageSize}' />">上一页</a>
                        </li>
                    </#if>
                    <!--all page numbers -->

                    <!--对于页数较多的情况, 分情况处理 -->
                    <#assign startPage=(pageItem.currentPage - 5) />
                    <#assign endPage=(pageItem.currentPage + 4) />

                    <#if startPage lt 1>
                        <#assign startPage=1 />
                        <#assign endPage=10 />
                        <#if endPage gt pageItem.totalPage >
                            <#assign endPage=pageItem.totalPage />
                        </#if>
                    </#if>
                    <#if endPage gt pageItem.totalPage >
                        <#assign endPage=pageItem.totalPage />
                        <#assign startPage=endPage-9 />
                        <#if startPage lt 1>
                            <#assign startPage=1 />
                        </#if>
                    </#if>

                    <#list startPage .. endPage as pageIndex>
                        <#assign currCss="" />
                        <#if pageIndex == pageItem.currentPage>
                            <#assign currCss="active" />
                        </#if>
                        <li class="paginate_button ${currCss}" tabindex="0">
                            <a href="<@spring.url '${url}?${param}currPage=${pageIndex}&pageSize=${pageItem.pageSize}' />">${pageIndex}</a>
                        </li>
                    </#list>


                    <!--next page-->
                    <#if pageItem.currentPage == pageItem.totalPage >
                        <li class="paginate_button next disabled" tabindex="0">
                            <a href="javascript:void(0)">下一页</a>
                        </li>
                    <#else>
                        <li class="paginate_button next" tabindex="0">
                            <a href="<@spring.url '${url}?${param}currPage=${pageItem.nextPage}&pageSize=${pageItem.pageSize}' />">下一页</a>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
    </#if>
</#macro>