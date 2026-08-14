package com.tangailquiz.util;

import com.tangailquiz.model.Question;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Reads a question form and checks that required fields are filled.
 */
public class QuestionForm {

    public static Question fromRequest(HttpServletRequest request) {
        Question q = new Question();
        q.setQuestionText(QuizUtil.clean(request.getParameter("questionText")));
        q.setOptionA(QuizUtil.clean(request.getParameter("optionA")));
        q.setOptionB(QuizUtil.clean(request.getParameter("optionB")));
        q.setOptionC(QuizUtil.clean(request.getParameter("optionC")));
        q.setOptionD(QuizUtil.clean(request.getParameter("optionD")));
        q.setCorrectOption(QuizUtil.clean(request.getParameter("correctOption")).toUpperCase());
        q.setCategory(QuizUtil.clean(request.getParameter("category")));
        q.setDifficulty(QuizUtil.clean(request.getParameter("difficulty")));
        q.setExplanation(QuizUtil.clean(request.getParameter("explanation")));
        q.setSourceUrl(QuizUtil.clean(request.getParameter("sourceUrl")));
        q.setActive(request.getParameter("active") != null);
        return q;
    }

    public static String validate(Question q) {
        if (QuizUtil.isBlank(q.getQuestionText())) {
            return "Question text is required.";
        }
        if (QuizUtil.isBlank(q.getOptionA()) || QuizUtil.isBlank(q.getOptionB())
                || QuizUtil.isBlank(q.getOptionC()) || QuizUtil.isBlank(q.getOptionD())) {
            return "All four options are required.";
        }
        if (!QuizUtil.isOption(q.getCorrectOption())) {
            return "Correct option must be A, B, C, or D.";
        }
        if (QuizUtil.isBlank(q.getCategory())) {
            return "Category is required.";
        }
        if (QuizUtil.isBlank(q.getDifficulty())) {
            return "Difficulty is required.";
        }
        return null;
    }
}
