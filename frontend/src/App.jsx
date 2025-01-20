import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { SearchPage } from './pages/SearchPage';
import { ProductDetail } from './pages/ProductDetail';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app">
        <header className="header">
          <h1>Product Catalog</h1>
        </header>

        <main className="main">
          <Routes>
            <Route path="/" element={<SearchPage />} />
            <Route path="/product/:id" element={<ProductDetail />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
