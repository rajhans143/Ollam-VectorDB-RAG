package com.example.ragdemo.ingestion;

import java.util.List;

import org.springframework.ai.document.Document;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class DocumentIngestionService implements CommandLineRunner {

    @Value("CLASSPATH:pdf/BiharVotingResult2025.pdf")
    private Resource resource;

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    @Override
    public void run(String... args) throws Exception {
        //Read the PDF document  
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        //Create the document chunks
        TextSplitter splitter = new TokenTextSplitter();
        
        List<Document> documents = splitter.split(reader.read());

        vectorStore.accept(documents);

        
        // store them in the vector store

        //reader.read().forEach(document -> {
        //    System.out.println("Document Content: " + document.getContent());
        //});

    }
    

}
