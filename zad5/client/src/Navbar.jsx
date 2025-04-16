import React from 'react';

const Navbar = ({ currentPage, setCurrentPage }) => {
  return (
      <div style={{ padding: '1rem', backgroundColor: '#333', color: 'white' }}>
        <button
            style={{ marginRight: '1rem', background: 'none', color: 'white', border: 'none' }}
            onClick={() => setCurrentPage('products')}
        >
          Products
        </button>
        <button
            style={{ marginRight: '1rem', background: 'none', color: 'white', border: 'none' }}
            onClick={() => setCurrentPage('payment')}
        >
          Payment
        </button>
      </div>
  );
};

export default Navbar;