# Embedding

**Brief**: A Spring Boot application implementing local transformer-based embedding generation with MongoDB Atlas vector store integration for semantic search capabilities.

## Project Overview

This project delivers a comprehensive embedding solution utilizing local transformer models through Spring AI's transformer integration. Unlike cloud-based embedding services, this application processes text locally using pre-trained transformer models, providing enhanced privacy and reduced latency. The system integrates with MongoDB Atlas Vector Search to enable efficient similarity-based retrieval and semantic search operations.

### Key Features

- **Local Transformer Models**: Utilizes Spring AI Transformers for on-premise embedding generation
- **MongoDB Atlas Vector Store**: Leverages MongoDB Atlas's native vector search capabilities
- **Privacy-First Architecture**: Processes sensitive data locally without external API calls
- **High Performance**: Optimized for batch processing and real-time embedding generation
- **Scalable Storage**: MongoDB's distributed architecture for large-scale vector operations

### Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17 (LTS)
- **AI Framework**: Spring AI 1.0.0-M1
- **Embedding Engine**: Spring AI Transformers
- **Vector Database**: MongoDB Atlas Vector Search
- **Build System**: Apache Maven
- **Code Enhancement**: Project Lombok

### Architecture Components

```
src/main/java/com/example/vectordb/
├── config/           # Configuration for transformers and MongoDB
├── controller/       # REST API endpoints for embedding operations
├── service/          # Business logic and embedding processing
├── Emebedding/       # Core embedding models and utilities
└── EmebeddingApplication.java
```

### Core Dependencies

- `spring-boot-starter-web` - RESTful web services
- `spring-boot-starter-data-mongodb` - MongoDB integration
- `spring-ai-transformers-spring-boot-starter` - Local transformer models
- `spring-ai-mongodb-atlas-store-spring-boot-starter` - Vector store integration
- `lombok` - Code simplification

### Business Use Cases

1. **Enterprise Search**: Implement semantic search across corporate documents
2. **Content Classification**: Automatically categorize and tag content
3. **Similarity Detection**: Identify duplicate or similar content
4. **Knowledge Discovery**: Extract insights from unstructured text data
5. **Recommendation Systems**: Power content and product recommendations

### Technical Advantages

- **Data Privacy**: All processing occurs locally, ensuring sensitive data remains on-premise
- **Cost Efficiency**: Eliminates per-request API costs associated with cloud embedding services
- **Customization**: Ability to fine-tune models for domain-specific requirements
- **Offline Capability**: Functions without internet connectivity once models are downloaded
- **Consistent Performance**: Predictable latency without external service dependencies

### Configuration Requirements

- Transformer model specifications and local model paths
- MongoDB Atlas connection strings and vector index configurations
- Memory allocation settings for model loading
- Batch processing parameters for optimal throughput

This application serves as a robust foundation for organizations requiring high-performance, privacy-conscious embedding solutions with enterprise-grade vector search capabilities.