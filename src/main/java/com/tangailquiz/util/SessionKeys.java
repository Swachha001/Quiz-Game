package com.tangailquiz.util;

/**
 * Names of values we store in the HTTP session.
 * Using constants avoids typing the same string in many files.
 */
public class SessionKeys {

    public static final String PLAYER_ID = "playerId";
    public static final String PLAYER_NAME = "playerName";
    public static final String QUIZ_QUESTIONS = "quizQuestions";
    public static final String QUIZ_START_TIME = "quizStartTime";
    public static final String LAST_ATTEMPT_ID = "lastAttemptId";
    public static final String ADMIN_LOGGED_IN = "adminLoggedIn";

    private SessionKeys() {
    }
}
