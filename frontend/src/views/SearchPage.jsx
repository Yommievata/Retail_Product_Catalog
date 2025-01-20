import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { SearchBar } from '../components/SearchBar';
import { ProductCard } from '../components/ProductCard';
import { useDebounce } from '../hooks/useDebounce';
import { useProducts } from '../hooks/useProducts';

export function SearchPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearch = useDebounce(searchTerm, 300);
  const { products, loading, error } = useProducts(debouncedSearch);
  const navigate = useNavigate();

  return (
    <div className="search-page">
      <SearchBar value={searchTerm} onChange={setSearchTerm} />

      {loading && <div className="loading">Loading...</div>}
      {error && <div className="error">{error}</div>}

      <div className="products-grid">
        {products.map((product) => (
          <ProductCard
            key={product.id}
            product={product}
            onClick={() => navigate(`/product/${product.id}`)}
          />
        ))}
      </div>
    </div>
  );
}
