# BFHL Spring Boot REST API

A production-ready REST API for mathematical operations and Google Gemini AI integration.

## Features
- **POST /bfhl**: Handles math operations and AI queries.
- **GET /health**: Health check endpoint.
- **Math Operations**: Fibonacci, Prime numbers, LCM, HCF.
- **AI Integration**: Google Gemini API for single-word answers.
- **Robustness**: Global exception handling and input validation.

## Local Setup

### Prerequisites
- Java 17 or higher
- Maven (optional, if running from command line)

### Running the App
1. Set the Gemini API Key:
   ```bash
   export GEMINI_API_KEY=your_actual_api_key
   ```
2. Run using Maven:
   ```bash
   mvn spring-boot:run
   ```
   Or using the executable jar:
   ```bash
   java -jar target/api-0.0.1-SNAPSHOT.jar
   ```

## API Documentation

### POST `/bfhl`
Accepts a JSON body with **exactly one** of the following keys:
- `fibonacci` (integer): Returns the first `n` Fibonacci numbers.
- `prime` (list of integers): Returns the primes from the list.
- `lcm` (list of integers): Returns the Least Common Multiple.
- `hcf` (list of integers): Returns the Highest Common Factor.
- `AI` (string): Queries Gemini for a single-word answer.

**Sample Request:**
```json
{
  "fibonacci": 5
}
```

**Sample Response:**
```json
{
  "is_success": true,
  "official_email": "gungun@chitkara.edu.in",
  "data": [0, 1, 1, 2, 3]
}
```

## Deployment Steps (Render / Railway)

### Render
1. Connect your GitHub repository to Render.
2. Select **Web Service**.
3. Environment: **Docker** or **Java**.
4. Set Build Command: `./mvnw clean package`.
5. Set Start Command: `java -jar target/*.jar`.
6. Add Environment Variable: `GEMINI_API_KEY`.

### Railway
1. Connect GitHub repo.
2. Railway will automatically detect the Spring Boot project.
3. Add `GEMINI_API_KEY` in the Variables tab.
4. Deploy!
