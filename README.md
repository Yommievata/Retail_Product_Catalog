# IKEA Product Catalog with Fuzzy Search

A full-stack application that implements a product catalog with fuzzy search capabilities. The backend is built with Java Spring Boot and the frontend with React.

## Project Structure

```
product-catalog/
├── backend/                 # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml
│
└── frontend/               # React application
    ├── src/
    ├── public/
    └── package.json
```

## Backend

### Tech Stack
- Java 23
- Spring Boot 3.2
- Maven
- Built-in memory database

### Features
- REST API endpoints for product management
- Custom fuzzy search implementation
- In-memory product storage with mock data
- CORS enabled for frontend integration

### Setup & Running

1. Prerequisites:
  - Java 23 JDK
  - Maven

2. Build and Run:
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend server will start at `http://localhost:8080`

### API Endpoints
- `POST /api/products` - Add a new product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/search?query={searchTerm}` - Search products by name

## Frontend

### Tech Stack
- React 18.3.1
- Vite
- JavaScript (ES6+)

### Features
- Dynamic search with debouncing
- Responsive product grid
- Detailed product view
- Real-time search results

### Setup & Running

1. Prerequisites:
  - Node.js (v18+ recommended)
  - npm or yarn

2. Install Dependencies:
```bash
cd frontend
npm install
```

3. Run Development Server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

## Running the Complete Application

1. Start the backend server:
```bash
cd backend
mvn spring-boot:run
```

2. In a new terminal, start the frontend:
```bash
cd frontend
npm run dev
```

3. Access the application at `http://localhost:5173`

## Implementation Details

### Backend
- Uses Spring Boot for REST API implementation
- Implements custom fuzzy search algorithm
- Stores product data in memory using `ConcurrentHashMap`
- Includes mock data initialization
- CORS configured for local development

### Frontend
- Built with React and Vite for fast development
- Implements debounced search functionality
- Responsive design for various screen sizes
- Real-time product filtering
- Clean and intuitive user interface

## Project Requirements Met
- ✅ Fuzzy search implementation
- ✅ Product catalog display
- ✅ Dynamic search results
- ✅ Detailed product view
- ✅ Responsive design
- ✅ Mock data initialization
- ✅ Clean code structure
- ✅ Proper error handling

## Additional Notes
- The backend includes a custom fuzzy search implementation without using external libraries
- The frontend implements debouncing to prevent excessive API calls
- The application uses mock data that is initialized on startup
- CORS is configured for local development