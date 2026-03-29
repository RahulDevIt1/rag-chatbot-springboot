# AI RAG Chatbot (Spring Boot)

This is a backend project where I built a simple **document-based chatbot** using Spring Boot.
The idea is to upload a PDF, store its content, and then ask questions based on that data.

Instead of sending the entire document to an AI model every time, the system first finds the most relevant parts of the document and then generates an answer from those.

---

## What this project does

* Upload a PDF file
* Extract text from it
* Break the text into smaller chunks
* Store chunks in MongoDB along with embeddings
* Convert user query into embedding
* Find similar chunks using cosine similarity
* Send those chunks to an AI model (Ollama)
* Return a contextual answer

---

## Tech used

* Java + Spring Boot
* Spring Security (JWT)
* MongoDB
* Ollama (local models)
* Maven

---

## How it works (simple flow)

1. Upload a file → `/ai/upload`
2. File is processed and stored in DB
3. User asks a question → `/ai/ask`
4. System finds relevant chunks
5. AI model generates answer using those chunks

---

## Running locally

### 1. Start MongoDB

Make sure MongoDB is running on:

```
mongodb://localhost:27017/rag_app
```

---

### 2. Start Ollama

Install Ollama and run:

```
ollama serve
ollama pull nomic-embed-text
ollama pull phi3
```

---

### 3. Run the project

```
mvn spring-boot:run
```

---

## APIs

* `POST /auth/login` → get token
* `POST /ai/upload` → upload PDF
* `GET /ai/search?query=...` → basic search
* `GET /ai/ask?query=...` → chatbot response

---

## Notes

* Embeddings are generated locally using Ollama
* No external paid API is required
* Everything runs on local system

---

## What I learned from this

* How RAG (Retrieval-Augmented Generation) works in practice
* Handling large text using chunking
* Using embeddings + similarity search
* Integrating AI models with backend services
* Managing security using JWT

---

## Possible improvements

* Add frontend UI
* Use a vector database instead of manual similarity
* Add streaming responses
* Support multiple files

---

## API Documentation

Swagger UI is available after running the application:

http://localhost:8080/swagger-ui.html

You can test all APIs directly from the browser.

Steps:
1. Call /auth/login to get JWT token
2. Click "Authorize" in Swagger
3. Paste token as: Bearer <your_token>
4. Access secured APIs like upload, search, ask

## Author

Rahul Dev
