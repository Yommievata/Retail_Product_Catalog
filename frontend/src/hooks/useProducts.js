import { useState, useEffect } from 'react';
import { apiService } from '../services/api';

export function useProducts(searchTerm) {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      try {
        const data = searchTerm
          ? await apiService.searchProducts(searchTerm)
          : await apiService.getAllProducts();
        setProducts(data);
        setError(null);
      } catch (err) {
        setError(err.message || 'Failed to fetch products');
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [searchTerm]);

  return { products, loading, error };
}
