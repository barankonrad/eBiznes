import React, { useState } from 'react';
import Products from './Products';
import Payment from './Payment';
import Navbar from './Navbar';

const App = () => {
  const [currentPage, setCurrentPage] = useState('products');

  return (
      <div>
        <Navbar currentPage={currentPage} setCurrentPage={setCurrentPage} />
        <div style={{ padding: '2rem' }}>
          {currentPage === 'products' && <Products />}
          {currentPage === 'payment' && <Payment />}
        </div>
      </div>
  );
};

export default App;