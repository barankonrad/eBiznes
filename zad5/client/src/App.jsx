import React, { useState } from 'react';
import Cart from "./Cart";
import Products from './Products';
import Payment from './Payment';
import Navbar from './Navbar';

const App = () => {
  const [currentPage, setCurrentPage] = useState('products');

  const renderPage = () => {
    switch (currentPage) {
      case 'products':
        return <Products />;
      case 'cart':
        return <Cart />;
      case 'payment':
        return <Payment />;
      default:
        return <Products />;
    }
  };

  return (
      <div style={{ fontFamily: 'Arial, sans-serif' }}>
        <Navbar currentPage={currentPage} setCurrentPage={setCurrentPage} />
        <main style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
          {renderPage()}
        </main>
      </div>
  );
};

export default App;