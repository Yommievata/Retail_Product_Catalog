export function ProductCard({ product, onClick }) {
  return (
    <div className="product-card" onClick={onClick}>
      <img src={product.imageUrl} alt={product.name} className="product-image" />
      <div className="product-info">
        <h3>{product.name}</h3>
        <p className="category">{product.category}</p>
        <p className="price">${product.price.toFixed(2)}</p>
      </div>
    </div>
  );
}
