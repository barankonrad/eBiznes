import React, { useEffect, useState } from "react";

const Payment = () => {
  const [cardNumber, setCardNumber] = useState("");
  const [paymentStatus, setPaymentStatus] = useState("");
  const [cartItems, setCartItems] = useState([]);
  const [productDetails, setProductDetails] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCartAndItems = async () => {
      try {
        const cartRes = await fetch("http://localhost:9000/cartItems");
        const cartData = await cartRes.json();
        setCartItems(cartData);

        const itemsRes = await fetch("http://localhost:9000/items");
        const itemsData = await itemsRes.json();

        const productsMap = {};
        itemsData.forEach((item) => {
          productsMap[item.id] = item;
        });

        setProductDetails(productsMap);
        setLoading(false);
      } catch (error) {
        console.error("Error loading cart or items:", error);
      }
    };

    fetchCartAndItems();
  }, []);

  const getCartTotal = () => {
    return cartItems.reduce((total, item) => {
      const product = productDetails[item.id];
      const unitPrice = product?.price || 0;
      return total + unitPrice * item.quantity;
    }, 0);
  };

  const generateRandomId = () => Math.floor(Math.random() * 1000000);

  const clearCart = () => {
    fetch("http://localhost:9000/cart/clear", { method: "DELETE" })
    .then(() => setCartItems([]))
    .catch((err) => console.error("Error clearing cart:", err));
  };

  const handlePayment = () => {
    if (!cardNumber) {
      setPaymentStatus("Please enter a card number.");
      return;
    }

    const total = getCartTotal();

    const paymentData = {
      id: generateRandomId(),
      cardNumber,
      amount: total,
      timestamp: new Date().toISOString(),
      status: "pending",
    };

    fetch("http://localhost:9000/payment", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(paymentData),
    })
    .then((response) => response.json())
    .then(() => {
      setPaymentStatus("Payment successful!");
      clearCart();
    })
    .catch((error) => {
      console.error("Payment failed:", error);
      setPaymentStatus("Payment failed. Please try again.");
    });
  };

  const total = getCartTotal();

  return (
      <div style={{ padding: "2rem", fontFamily: "Arial, sans-serif" }}>
        <h2>Payment</h2>

        {loading ? (
            <p>Loading cart...</p>
        ) : (
            <>
              <p>Total: ${total.toFixed(2)}</p>

              <div style={{ marginBottom: "1rem" }}>
                <label>
                  Card Number:
                  <input
                      type="text"
                      value={cardNumber}
                      onChange={(e) => setCardNumber(e.target.value)}
                      placeholder="1234 5678 9876 5432"
                      style={{ padding: "0.5rem", marginLeft: "0.5rem", width: "250px" }}
                  />
                </label>
              </div>

              <button
                  onClick={handlePayment}
                  disabled={loading || cartItems.length === 0}
                  style={{
                    padding: "0.75rem 1.5rem",
                    backgroundColor: "#4CAF50",
                    color: "white",
                    border: "none",
                    borderRadius: "5px",
                    cursor: loading ? "not-allowed" : "pointer",
                    opacity: loading ? 0.6 : 1,
                  }}
              >
                Pay Now
              </button>

              {paymentStatus && (
                  <p
                      style={{
                        marginTop: "1rem",
                        color: paymentStatus === "Payment successful!" ? "green" : "red",
                      }}
                  >
                    {paymentStatus}
                  </p>
              )}
            </>
        )}
      </div>
  );
};

export default Payment;