## Usage

### Setup env vars with values as provided on these keys
- Either on the system level or as a runtime env vars in IntelliJ IDEA or other.
  - API_BASE_PATH
  - API_KEY
- Reasoning for the env var solution being that a production code application should not expose secrets or endpoints in source code.

### Make requests on localhost
- POST::http://localhost:8080/product-store/checkout
  - Use body
    - ```
        {
          "priceThreshold": 300,
          "productIds": [3,7]
        }
        ```
      