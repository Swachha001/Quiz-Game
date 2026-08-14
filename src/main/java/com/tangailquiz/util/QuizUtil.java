package com.tangailquiz.util;

/**
 * Small helpers used by servlets.
 * Kept plain so the flow is easy to follow.
 */
public class QuizUtil {

    public static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    public static boolean isBlank(String value) {
        return clean(value).isEmpty();
    }

    public static boolean isOption(String value) {
        if (value == null) {
            return false;
        }
        return value.equals("A") || value.equals("B") || value.equals("C") || value.equals("D");
    }

    public static String messageForScore(double percentage) {
        if (percentage >= 90) {
            return "Excellent! You know Tangail very well!";
        }
        if (percentage >= 70) {
            return "Very Good!";
        }
        if (percentage >= 50) {
            return "Good effort!";
        }
        return "Keep learning about Tangail!";
    }
}
