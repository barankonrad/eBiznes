import React, { useEffect, useState } from "react";

const Products = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch("http://localhost:9000/items")
    .then(res => res.json())
    .then(data => {
      console.log("Products:", data);
      setProducts(data);
    })
    .catch(err => {
      console.error("Error:", err);
      setError(err.toString());
    });
  }, []);

  return (
      <div style={{ padding: "2rem", fontFamily: "Arial, sans-serif" }}>
        <h2 style={{ marginBottom: "1rem" }}>Product List</h2>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <div style={{ display: "flex", flexWrap: "wrap", gap: "1rem" }}>
          {products.length > 0 ? (
              products.map(product => (
                  <div
                      key={product.id}
                      style={{
                        border: "1px solid #ccc",
                        borderRadius: "8px",
                        padding: "1rem",
                        width: "200px",
                        boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
                      }}
                  >
                    <h3 style={{ fontSize: "1.1rem", marginBottom: "0.5rem" }}>
                      {product.name}
                    </h3>
                    <p style={{ margin: "0.25rem 0" }}>Price: ${product.price}</p>
                    <p style={{ margin: "0.25rem 0", color: "#777" }}>
                      Category ID: {product.categoryId}
                    </p>
                  </div>
              ))
          ) : (
              <p>Loading products...</p>
          )}
        </div>
      </div>
  );
};

export default Products;