package com.shutafin;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class DummyUsersRunner {

    private GenerateDummyUsersService generateDummyUsersService;
    private GenerateDummyQuestionsService generateDummyQuestionsService;
    private GenerateDummyUsersInfoService generateDummyUsersInfoService;

    public DummyUsersRunner(GenerateDummyUsersService generateDummyUsersService,
                            GenerateDummyQuestionsService generateDummyQuestionsService,
                            GenerateDummyUsersInfoService generateDummyUsersInfoService) {
        this.generateDummyUsersService = generateDummyUsersService;
        this.generateDummyQuestionsService = generateDummyQuestionsService;
        this.generateDummyUsersInfoService = generateDummyUsersInfoService;
    }

    public void run(int countUsers, int countThreads) {

        long start = System.currentTimeMillis();
        int newCountThreads = countUsers < countThreads ? countUsers : countThreads;
        int countUsersInThread = countUsers / newCountThreads;

        List<CompletableFuture<String>> futureList = addUsers(newCountThreads, countUsersInThread, countUsers);
        waitForThreads(futureList);

        futureList = addUsersInfo(newCountThreads, countUsersInThread, countUsers);
        waitForThreads(futureList);

        futureList = addQuestions(newCountThreads, countUsersInThread, countUsers);
        waitForThreads(futureList);

        long finish = System.currentTimeMillis();
        System.out.println("Finish in " + (finish - start) + " ms");

    }

    private List<CompletableFuture<String>> addUsers(int newCountThreads, int countUsersInThread, int countUsers) {
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = 0; i < newCountThreads; i++) {
            int userFrom = i * countUsersInThread + 1;
            int userTo = (i == newCountThreads - 1) ? countUsers + 1 : userFrom + countUsersInThread;
            futureList.add(generateDummyUsersService.generateUsers(userFrom, userTo));
        }
        return futureList;
    }

    private List<CompletableFuture<String>> addUsersInfo(int newCountThreads, int countUsersInThread, int countUsers) {
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = 0; i < newCountThreads; i++) {
            int userIdFrom = i * countUsersInThread + 1;
            int userIdTo = (i == newCountThreads - 1) ? countUsers + 1 : userIdFrom + countUsersInThread;
            futureList.add(generateDummyUsersInfoService.generateUsersInfo(userIdFrom, userIdTo));
        }
        return futureList;
    }

    private List<CompletableFuture<String>> addQuestions(int newCountThreads, int countUsersInThread, int countUsers) {
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = 0; i < newCountThreads; i++) {
            int userIdFrom = i * countUsersInThread + 1;
            int userIdTo = (i == newCountThreads - 1) ? countUsers + 1 : userIdFrom + countUsersInThread;
            futureList.add(generateDummyQuestionsService.generateQuestions(userIdFrom, userIdTo));
        }
        return futureList;
    }

    private void waitForThreads(List<CompletableFuture<String>> futureList) {
        for (CompletableFuture<String> future : futureList) {
            CompletableFuture.anyOf(future).join();
        }
    }

}


