package by.vironit.training.basumatarau.instantMessengerApp.validator;

import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;

public class RequestHandler {
    private RequestHandler() {
    }

    public static boolean isGet(HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equalsIgnoreCase("get");
    }

    public static boolean isUpdate(HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equalsIgnoreCase("update");
    }

    public static boolean isPost(HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    public static String getString(HttpServletRequest request, String param)
            throws ValidationException {
        return getString(request, param, ".*");
    }

    public static String getString(HttpServletRequest request, String param, String pattern)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        if (attrValue != null && attrValue.matches(pattern)) {
            return attrValue;
        }
        throw new ValidationException(
                new ParseException(
                String.format("Field %s doesn't match the security pattern", param),
                0)
        );
    }

    public static Integer getInt(HttpServletRequest request, String param)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        try {
            return Integer.parseInt(attrValue);
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    new ParseException(
                    String.format("Field %s doesn't match the number format pattern", param),
                    0)
            );
        }
    }

    public static Long getLong(HttpServletRequest request, String param)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        try {
            return Long.parseLong(attrValue);
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    new ParseException(
                    String.format("Field %s doesn't match the number format pattern", param),
                    0)
            );
        }
    }

    public static Double getDouble(HttpServletRequest request, String param)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        try {
            return Double.parseDouble(attrValue);
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    new ParseException(
                    String.format("Field %s doesn't match the number format pattern", param),
                    0)
            );
        }
    }


    public static Float getFloat(HttpServletRequest request, String param)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        try {
            return Float.parseFloat(attrValue);
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    new ParseException(
                    String.format("Field %s doesn't match the number format pattern", param),
                    0)
            );
        }
    }

    public static Timestamp getTimestamp(HttpServletRequest request, String param)
            throws ValidationException {
        String attrValue = request.getParameter(param);
        try {
            return Timestamp.valueOf(attrValue);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    new ParseException(
                    String.format("Field %s doesn't match the Timestamp format pattern", param),
                    0)
            );
        }
    }

}
