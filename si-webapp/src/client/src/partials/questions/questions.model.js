app.factory('quizFactory', function () {
    var questions = [
        {
            id:5,
            question: "Question1",
            options: ["answer1", "answer2", "answer3", "answer4"],
        },
        { id:6,
            question: "Question2",
            options: ["answer1", "answer2"],
        },
        {
            id:7,
            question: "Question3",
            options: ["answer1", "answer2", "answer3"],
            },
            {
                id:8,
                question: "Question4",
                options: ["answer1", "answer2", "answer3", "answer4"],
            },
            {
                id:9,
                question: "Question5",
                options: ["answer1", "answer2", "answer3", "answer4"],

        }
    ];

    return {
        getQuestion: function (id) {
            if (id < questions.length) {
                return questions[id];
            } else {
                return false;
            }
        }
    };
});