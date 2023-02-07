package com.nextvoyager.conferences.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

/**
 * Utility class to handle pagination parameters in controller
 *
 * @author Stanislav Bozhevskyi
 */
public class PaginationUtil {

    private static final int[] paginationLimitList = {3,6,9,12,15,18};

    public static final String PAGINATION_PAGE_PARAMETER = "page";
    public static final String PAGINATION_LIMIT_PARAMETER = "limit";

    private PaginationUtil(){}

    public static int[] getPaginationLimitList() {
        return Arrays.copyOf(paginationLimitList, paginationLimitList.length);
    }

    public static int handlePaginationPageParameter(HttpServletRequest req) {
        String param = req.getParameter(PAGINATION_PAGE_PARAMETER);
        int defaultPaginationPage = 1;
        if (param == null) {
            return defaultPaginationPage;
        }else {
            try {
                int paramVal = Integer.parseInt(param);
                if (paramVal < 1) {
                    return defaultPaginationPage;
                } else {
                    return paramVal;
                }
            } catch (NumberFormatException exception) {
                return defaultPaginationPage;
            }
        }
    }

    public static int handlePaginationLimitParameter(HttpServletRequest req) {
        int defaultPaginationLimit = 6;
        return handlePaginationLimitParameter(req, defaultPaginationLimit);
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

    public static int getNumOfPages(Integer elementsCount, int limit) {
        return (int)Math.ceil((double)elementsCount/limit);
    }
}
