const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  async fetchWithError(url, options) {
    try {
      const response = await fetch(url, options);
      if (!response.ok) {
        throw new Error(`API Error: ${response.statusText}`);
      }
      return await response.json();
    } catch (error) {
      throw new Error(error.message || 'Unknown error occurred');
    }
  }

  searchProducts(query) {
    return this.fetchWithError(`${API_BASE_URL}/search?query=${encodeURIComponent(query)}`);
  }

  getProduct(id) {
    return this.fetchWithError(`${API_BASE_URL}/products/${id}`);
  }

  getAllProducts() {
    return this.fetchWithError(`${API_BASE_URL}/products`);
  }
}

export const apiService = new ApiService();
