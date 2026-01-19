package com.example.opensearch.service;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.huggingface.tokenizers.Encoding;

import ai.onnxruntime.*;

import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.*;

@Service
public class EmbeddingService {

    private static final String MODEL_PATH = "D:/models/bge-m3/model.onnx";
    private static final String TOKENIZER_PATH = "D:/models/bge-m3/tokenizer.json";

    private final OrtEnvironment env;
    private final OrtSession session;
    private final HuggingFaceTokenizer tokenizer;

    public EmbeddingService() throws Exception {
        env = OrtEnvironment.getEnvironment();
        session = env.createSession(MODEL_PATH, new OrtSession.SessionOptions());
        tokenizer = HuggingFaceTokenizer.newInstance(Paths.get(TOKENIZER_PATH));
    }

    public List<Float> embed(String text) throws Exception {

        Encoding enc = tokenizer.encode(text);

        long[] inputIds = enc.getIds();
        long[] attentionMask = enc.getAttentionMask();

        OnnxTensor inputIdsTensor =
                OnnxTensor.createTensor(env, new long[][]{inputIds});

        OnnxTensor maskTensor =
                OnnxTensor.createTensor(env, new long[][]{attentionMask});

        Map<String, OnnxTensor> inputs = Map.of(
                "input_ids", inputIdsTensor,
                "attention_mask", maskTensor
        );

        OrtSession.Result result = session.run(inputs);

        float[][][] hidden =
                (float[][][]) result.get(0).getValue();

        float[] embedding = meanPool(hidden[0], attentionMask);
        normalize(embedding);

        List<Float> out = new ArrayList<>();
        for (float v : embedding) out.add(v);
        return out;
    }

    private float[] meanPool(float[][] tokenEmbeddings, long[] mask) {
        int dim = tokenEmbeddings[0].length;
        float[] pooled = new float[dim];
        int count = 0;

        for (int i = 0; i < tokenEmbeddings.length; i++) {
            if (mask[i] == 1) {
                for (int j = 0; j < dim; j++) {
                    pooled[j] += tokenEmbeddings[i][j];
                }
                count++;
            }
        }

        for (int j = 0; j < dim; j++) {
            pooled[j] /= count;
        }
        return pooled;
    }

    private void normalize(float[] v) {
        float sum = 0f;
        for (float x : v) sum += x * x;
        float norm = (float) Math.sqrt(sum);
        for (int i = 0; i < v.length; i++) v[i] /= norm;
    }
}
