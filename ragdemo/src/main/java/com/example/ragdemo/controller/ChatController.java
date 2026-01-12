package com.example.ragdemo.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
 

@RestController
@RequestMapping("/api/chat") 
public class ChatController {

    private final OllamaChatModel ollamaChatModel;
    private final VectorStore vectorStore;

    public ChatController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.ollamaChatModel = ollamaChatModel;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String message) {
     
       return ChatClient.builder(ollamaChatModel)
        .build()
        .prompt()
        .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .user(message)
        .call()
        .content();
    }
}
