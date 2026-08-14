function checkAnswers() {
    var form = document.getElementById("quiz-form");
    if (form == null) {
        return true;
    }

    var total = parseInt(form.getAttribute("data-total"), 10);
    var answered = form.querySelectorAll("input[type=radio]:checked").length;

    if (answered < total) {
        return confirm("You have not answered all questions. Are you sure you want to submit?");
    }
    return true;
}

window.onload = function () {
    var form = document.getElementById("quiz-form");
    if (form != null) {
        form.onsubmit = checkAnswers;
    }
};
