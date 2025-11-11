# Bajaj Finserv Health | Qualifier 1 | Java Solution

This is a Spring Boot app that automatically:
1. Sends a POST request to generate a webhook.
2. Chooses a question (based on regNo last two digits — odd/even).
3. Sends your **final SQL query** to the returned webhook URL using the `accessToken` (JWT).

---

## 🧱 Tech Stack
- Java 17+
- Spring Boot 3.2
- Maven
- WebClient for HTTP calls

---

## 🚀 Run Locally

### 1️⃣ Clone this repo
```bash
git clone https://github.com/<your-username>/bajaj-java-qualifier.git
cd bajaj-java-qualifier

