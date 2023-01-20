package com.nextvoyager.conferences.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class PaginationUtil {

    private static final int[] paginationLimitList = {3,6,9,12};

    private static int DEFAULT_PAGINATION_PAGE = 1;
    private static int DEFAULT_PAGINATION_LIMIT = 6;

    public static final String PAGINATION_PAGE_PARAMETER = "page";
    public static final String PAGINATION_LIMIT_PARAMETER = "limit";

    private PaginationUtil(){};

    public static int[] getPaginationLimitList() {
        return Arrays.copyOf(paginationLimitList, paginationLimitList.length);
    }

    public static int handlePaginationPageParameter(HttpServletRequest req) {
        String param = req.getParameter(PAGINATION_PAGE_PARAMETER);
        if (param == null) {
            return DEFAULT_PAGINATION_PAGE;
        }else {
            try {
                int paramVal = Integer.parseInt(param);
                if (paramVal < 1) {
                    return DEFAULT_PAGINATION_PAGE;
                } else {
                    return paramVal;
                }
            } catch (NumberFormatException exception) {
                return DEFAULT_PAGINATION_PAGE;
            }
        }
    }

    public static int handlePaginationLimitParameter(HttpServletRequest req) {
        return handlePaginationLimitParameter(req, DEFAULT_PAGINATION_LIMIT);
    }

    public static int handlePaginationLimitParameter(HttpServletRequest req, int defaultLimit) {
        String param = req.getParameter(PAGINATION_LIMIT_PARAMETER);
        if (param == null) {
            return defaultLimit;
        }else {
            try {
                int paramVal = Integer.parseInt(param);
                if (paramVal < 1 || Arrays.binarySearch(paginationLimitList,paramVal) < 0) {
                    return defaultLimit;
                } else {
                    return paramVal;
                }
            } catch (NumberFormatException exception) {
                return defaultLimit;
            }
        }
    }
}
