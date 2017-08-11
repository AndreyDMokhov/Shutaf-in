app.factory('quizFactory', function () {
    var questions = [
        {
            question: "Which is the largest country in the world by population? Which is the largest country in the world by population?",
            options: ["India", "USA", "China", "Russia", "123", "321"],
            answer: 2
        },
        {
            question: "Question2",
            options: ["India", "USA", "China", "Russia", "123", "321"],
            answer: 0
        },
        {
            question: "Which is the largest country in the world by population? Which is the largest country in the world by population?",
            options: ["India", "USA", "China", "Russia", "123", "321"],
            answer: 2
        },
        {
            question: "Which is the largest country in the world by population? Which is the largest country in the world by population?",
            options: ["India", "USA", "China", "Russia", "123", "321"],
            answer: 2
        },
        {
            question: "When did the second world war end?",
            options: ["1945", "1939", "1944", "1942"],
            answer: 0
        },
        {
            question: "Which was the first country to issue paper currency?",
            options: ["USA", "France", "Italy", "China", "USA2", "France2", "Italy2", "China2"],
            answer: 3
        },
        {
            question: "Question1",
            options: ["answer1", "answer2", "answer3", "answer4"],
            answer: 0
        },
        {
            question: "Question2",
            options: ["answer12", "answer2"],
            answer: 0
        },
        {
            question: "Question3",
            options: ["answer1", "answer2", "answer3"],
            answer: 0
            // },
            // {
            //     question: "Question3",
            //     options: ["answer1", "answer2", "answer3", "answer4", "answer5", "answer6", "answer7"],
            //     answer: 0
            // },
            // {
            //     question: "Question4",
            //     options: ["answer1", "answer2", "answer3", "answer4"],
            //     answer: 0
            // },
            // {
            //     question: "Question5",
            //     options: ["answer1", "answer2", "answer3", "answer4"],
            //     answer: 0

            // {
            // 	question: "Who invented telephone?",
            // 	options: ["Albert Einstein", "Alexander Graham Bell", "Isaac Newton", "Marie Curie"],
            // 	answer: 1
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