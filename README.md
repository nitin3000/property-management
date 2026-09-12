# 🏡 PropApp - Real Estate Query & Relevance Scoring Engine

PropApp is a robust, production-ready real estate data filtering and ranking platform built with **Spring Boot** and backed by **PostgreSQL**. The engine ingests property streams from multiple feed sources, parses complex data constraints, applies an active timeline aging decay algorithm, and paginates responses securely.

---

## 🚀 System Architecture Layout
The application utilizes a classic layered enterprise design model configured using standard Java abstractions to ensure optimal bytecode compilation stability:

* **`com.propapp.model`** -> Houses database entities (`Listing`) and serialized composite key mappings (`ListingId`) to handle cross-feed row collisions.
* **`com.propapp.repository`** -> Custom JPQL query layer executing explicit type-casts to safely evaluate large string descriptors without crashes.
* **`com.propapp.service`** -> Houses the core mathematical scoring engine and compound weekly aging calculations.
* **`com.propapp.controller`** -> Validation ingress layer that evaluates parameters before firing transactions.

---

## 🧠 Core Relevance Ranking Engine Matrix
Instead of basic database sort queries, listings are dynamically assigned a **Relevance Match Score (0% - 100%)** at runtime using a weighted evaluation index:

$$\text{Relevance Score} = \text{Budget Variance (70% Max)} + \text{Chronological Recency (30% Max)}$$

### 1. Budget Fit Variance (70 Points Max)
* **Under-Budget Properties:** If a property list price matches or sits underneath the user's explicit target budget parameter, it instantly secures the complete **70 points**.
* **Over-Budget Properties:** If a property exceeds the target budget threshold, points degrade linearly using a relative distance variance decay equation to avoid abrupt dataset cutoffs:
$$\text{Budget Score} = \max\left(0,\; 70.0 \times \left(1.0 - \frac{\text{Property Price} - \text{Target Budget}}{\text{Target Budget}}\right)\right)$$

### 2. Timeline Market Aging Decay (30 Points Max)
* **Fresh Inventory:** Active property listings published to the market feed inside the last 7 days receive the full **30 points**.
* **Compounding Weekly Penalty:** Older listings are gradually penalized to prioritize new inventory, using a 10% compounding weekly decay formula based on their exact day offset age:
$$\text{Recency Score} = \max\left(0,\; 30.0 \times 0.90^{\left(\frac{\text{Days Old}}{7.0}\right)}\right)$$

### 🏆 Tie-Breaker Resolution Rules
If multiple properties achieve an identical relevance percentage match, the application runs a tie-breaker routine that evaluates the raw `listedDate` attribute to force the **newest inventory to display first**.

---

## 🛡️ Deliberate Input Validation Guardrails
The system protects against faulty inputs at the API ingress gate, throwing precise `HTTP 400` or `HTTP 404` error strings rather than allowing silent data failures or empty data sets:

* **Pricing Logic Contradictions:** Rejects requests with an immediate `HTTP 400 Bad Request` if `minPrice` is greater than `maxPrice`.
* **Invalid Layout Limits:** Blocks pagination page sizes (`size`) less than 1 or offset tracking pages (`page`) less than 0.
* **Explicit Missing City Alerts:** Checks if a requested city exists in the database. If it is not found, it responds with an `HTTP 404 Not Found` error string instead of returning a misleading, blank list.

---

## 🛠️ Verification & Deployment Manual

### 1. Start Your PostgreSQL Database (Docker)
```bash
docker exec -it postgres psql -U appuser -d propdb -c "CREATE DATABASE propdb;"
```

### 2. Compile and Boot the Spring Boot Hub
```bash
mvn clean spring-boot:run
```

### 3. Ingest Mock Listing Payloads (cURL)
```bash
curl -X POST http://localhost:8080/api/listings \
  -H "Content-Type: application/json" \
  -d '{"id":"prop_101","source":"MLS_A","address":"123 Ocean Blvd","city":"Miami","state":"FL","price":500000.0,"bedrooms":3,"status":"active","listedDate":"2026-09-10","description":"Luxury beach condo"}'
```

### 4. Execute Scored Evaluation Engine
```bash
curl "http://localhost:8080/api/listings/search?city=Miami&targetBudget=550000"
```
