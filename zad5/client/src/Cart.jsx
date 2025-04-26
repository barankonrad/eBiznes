import React, {useEffect, useState} from "react";

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [productDetails, setProductDetails] = useState({});
  const [status, setStatus] = useState("");

  const fetchCart = () => {
    fetch("http://localhost:9000/cartItems")
    .then((res) => res.json())
    .then((data) => {
      setCartItems(data);
      fetchProductDetails(data.map(item => item.id));
    })
    .catch((error) => {
      console.error("Error fetching cart items:", error);
    });
  };

  const fetchProductDetails = (ids) => {
    fetch("http://localhost:9000/items")
    .then((res) => res.json())
    .then((items) => {
      const relevant = items.filter(item => ids.includes(item.id));
      const map = {};
      relevant.forEach(item => (map[item.id] = item));
      setProductDetails(map);
    })
    .catch((err) => {
      console.error("Error fetching product details:", err);
    });
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const handleDelete = (id) => {
    fetch(`http://localhost:9000/cart/${id}`, {
      method: "DELETE",
    })
    .then(() => {
      setStatus("Item removed from cart.");
      fetchCart();
    })
    .catch(() => {
      setStatus("Error while removing item.");
    });
  };

  const groupedItems = cartItems.reduce((acc, item) => {
    acc[item.id] = acc[item.id] || {id: item.id, quantity: 0};
    acc[item.id].quantity += item.quantity;
    return acc;
  }, {});

  const groupedArray = Object.values(groupedItems);

  const totalPrice = groupedArray.reduce((sum, item) => {
    const product = productDetails[item.id];
    return sum + (product ? product.price * item.quantity : 0);
  }, 0);

  return (
      <div style={{padding: "2rem", fontFamily: "Arial, sans-serif"}}>
        <h2>Shopping Cart</h2>

        {groupedArray.length === 0 ? (
            <p>Your cart is currently empty.</p>
        ) : (
            <ul style={{listStyle: "none", padding: 0}}>
              {groupedArray.map((item) => {
                const product = productDetails[item.id];

                return (
                    <li
                        key={item.id}
                        style={{
                          padding: "1rem",
                          marginBottom: "1rem",
                          border: "1px solid #ccc",
                          borderRadius: "8px",
                        }}
                    >
                      {product ? (
                          <>
                            <h3 style={{marginBottom: "0.25rem"}}>{product.name}</h3>
                            <p style={{
                              marginTop: 0,
                              marginBottom: "0.5rem",
                              color: "#777"
                            }}>
                              Category ID: {product.categoryId}
                            </p>
                            <p>Unit price: ${product.price.toFixed(2)}</p>
                            <p>Quantity: {item.quantity}</p>
                            <p>Total: ${(product.price * item.quantity).toFixed(
                                2)}</p>
                          </>
                      ) : (
                          <p>Loading product info...</p>
                      )}
                      <button
                          onClick={() => handleDelete(item.id)}
                          style={{
                            marginTop: "0.5rem",
                            backgroundColor: "#e74c3c",
                            color: "white",
                            padding: "0.5rem 1rem",
                            border: "none",
                            borderRadius: "5px",
                            cursor: "pointer",
                          }}
                      >
                        Remove
                      </button>
                    </li>
                );
              })}
            </ul>
        )}

        <hr style={{margin: "2rem 0"}}/>
        <h3>Total cart cost: ${totalPrice.toFixed(2)}</h3>

        {status && (
            <p
                style={{
                  marginTop: "1rem",
                  color: status.includes("Error") ? "red" : "green",
                }}
            >
              {status}
            </p>
        )}
      </div>
  );
};

export default Cart;