package com.example.exactSemantic.config;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class AiConfig {

    @Bean
    public TransformersEmbeddingModel embeddingModel() {
        // Hum MetadataMode ko constructor mein bhej rahe hain
        // Aur resources ko explicitly set kar rahe hain
        TransformersEmbeddingModel model = new TransformersEmbeddingModel(MetadataMode.ALL);

        model.setModelResource(new FileSystemResource("D:\\VIJAYADITYA\\Downloads\\exactSemantic\\model.onnx"));
        model.setTokenizerResource(new FileSystemResource("D:\\VIJAYADITYA\\Downloads\\exactSemantic\\tokenizer.json"));
        model.setModelOutputName("token_embeddings");
        return model;
    }
}