package cn.com.niub.utils;

import org.apache.commons.lang3.StringUtils;

public class PageUtils {

    private static final String PAGE_SIZE =  "10";

    private static final String PAGE_NO = "1";

    public static <T> Pagination<T> newPagination(String currentPageStr, String pagesizeStr) {
        if(StringUtils.isBlank(currentPageStr)){
            currentPageStr = PAGE_NO;
        }
        if(StringUtils.isBlank(pagesizeStr)){
            pagesizeStr = PAGE_SIZE;
        }

        Pagination<T> page = new Pagination<T>();
        int currentPage = Integer.parseInt(currentPageStr);
        int pagesize = Integer.parseInt(pagesizeStr);
        if (currentPageStr != null && !"".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        int pageStartNo = (currentPage - 1) * pagesize;
        page.setPageSize(pagesize);
        page.setPageNum(currentPage);
        page.setStartRow(pageStartNo);
        return page;
    }
}
