package com.atguigu.atcrowdfunding.utils;

import com.github.pagehelper.PageInfo;


/**
 * @author zbystart
 * @create 2020-12-18 下午 7:01
 */
public class PageUtils {
    public static <T> String getPage(PageInfo<T> pageInfo,String path,String queryCondition){

        if (queryCondition == null){
            // 没有查询条件
            return noHasQueryConditionPageFoot(pageInfo,path);
        }
        // 有查询条件
        return hasQueryConditionPageFoot(pageInfo,path,queryCondition);
    }

    private static <T> String noHasQueryConditionPageFoot(PageInfo<T> pageInfo,String path) {
        StringBuilder stringBuilder = new StringBuilder();
        //上一页
        if (pageInfo.isHasPreviousPage()){
            stringBuilder.append("<li ><a class='page' href='"+ path + "/" +pageInfo.getPrePage()+"'>上一页</a></li>");
        }else {
            stringBuilder.append("<li class='disabled'><a>上一页</a></li>");
        }
        //中间的页码
        int[] pageNums = pageInfo.getNavigatepageNums();
        for (int i : pageNums){
            if (pageInfo.getPageNum() == i){
                stringBuilder.append("<li class='active'>" +
                        "<a page='"+ i +"' queryCondition=''>"+ i
                        +  "<span class='sr-only'>(current)</span>" +
                        "</a>" +
                        "</li>");
            }else {
                stringBuilder.append("<li ><a class='page' href='"+ path + "/" +i+"'>"+i+"</a></li>");
            }
        }
        //下一页
        if (pageInfo.isHasNextPage()){
            stringBuilder.append("<li ><a class='page' href='"+ path + "/"+pageInfo.getNextPage()+"'>下一页</a></li>");

        }else {
            stringBuilder.append("<li class='disabled'><a>下一页</a></li>");
        }
        return stringBuilder.toString();
    }

    private static <T> String hasQueryConditionPageFoot(PageInfo<T> pageInfo,String path,String queryCondition) {
        StringBuilder stringBuilder = new StringBuilder();
        //上一页
        if (pageInfo.isHasPreviousPage()){
            stringBuilder.append("<li ><a class='page' href='"+ path + "/" +pageInfo.getPrePage() + "?queryCondition=" + queryCondition + "'>上一页</a></li>");
        }else {
            stringBuilder.append("<li class='disabled'><a>上一页</a></li>");
        }
        //中间的页码
        int[] pageNums = pageInfo.getNavigatepageNums();
        for (int i : pageNums){
            if (pageInfo.getPageNum() == i){
                stringBuilder.append("<li class='active'>" +
                        "<a page='"+ i +"' queryCondition = '"+ queryCondition +"'>" + i
                        +  "<span class='sr-only'>(current)</span>" +
                        "</a>" +
                        "</li>");
            }else {
                stringBuilder.append("<li ><a class='page' href='"+ path + "/" +i+ "?queryCondition=" + queryCondition + " '>"+i+"</a></li>");
            }
        }
        //下一页
        if (pageInfo.isHasNextPage()){
            stringBuilder.append("<li ><a class='page' href='"+ path + "/"+pageInfo.getNextPage()+ "?queryCondition=" + queryCondition + " '>下一页</a></li>");

        }else {
            stringBuilder.append("<li class='disabled'><a>下一页</a></li>");
        }
        return stringBuilder.toString();
    }

}
