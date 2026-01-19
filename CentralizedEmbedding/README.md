# CentralizedEmbedding

**Brief**: A Spring Boot microservice that provides centralized text embedding generation using OpenAI's embedding models with MongoDB persistence.

## Project Overview

This project implements a centralized embedding service built on Spring Boot 3.2.0 and Spring AI framework. It serves as a unified gateway for converting textual data into high-dimensional vector representations using OpenAI's state-of-the-art embedding models. The service is designed to support semantic search, similarity matching, and machine learning workflows across enterprise applications.

### Key Features

- **OpenAI Integration**: Leverages Spring AI OpenAI starter for seamless embedding model integration
- **MongoDB Persistence**: Stores generated embeddings with efficient indexing capabilities
- **RESTful API**: Exposes clean HTTP endpoints for embedding generation and retrieval
- **Scalable Architecture**: Built with microservice principles for horizontal scaling
- **Development Optimized**: Includes hot-reload capabilities and comprehensive logging

### Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17 (LTS)
- **AI Framework**: Spring AI 1.0.0-M1
- **Database**: MongoDB with Spring Data
- **Build System**: Apache Maven
- **Code Enhancement**: Project Lombok
- **Development Tools**: Spring Boot DevTools

### Architecture Components

```
src/main/java/com/example/centralized/CentralizedEmbedding/
├── service/           # Core business logic for embedding operations
├── controller/        # REST API endpoints
├── config/           # Configuration classes
└── model/            # Data models and entities
```

### Core Dependencies

- `spring-boot-starter-web` - RESTful web services
- `spring-boot-starter-data-mongodb` - MongoDB integration
- `spring-ai-openai-spring-boot-starter` - OpenAI embedding models
- `lombok` - Boilerplate code reduction
- `spring-boot-devtools` - Development productivity tools

### Business Use Cases

1. **Document Processing**: Convert documents to searchable vector representations
2. **Semantic Search**: Enable context-aware search functionality
3. **Content Recommendation**: Power recommendation engines with semantic similarity
4. **Data Analytics**: Support ML pipelines with pre-computed embeddings
5. **Knowledge Management**: Organize and retrieve information semantically

### Configuration Requirements

- OpenAI API credentials and model configuration
- MongoDB connection parameters and database settings
- Spring AI embedding model specifications
- Application-specific embedding dimensions and parameters

This service acts as the foundational layer for AI-powered text processing capabilities within the application ecosystem.