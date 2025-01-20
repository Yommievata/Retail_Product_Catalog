export function SearchBar({ value, onChange }) {
  return (
    <div className="search-bar">
      <input
        type="text"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder="Search products..."
        className="search-input"
      />
    </div>
  );
}
