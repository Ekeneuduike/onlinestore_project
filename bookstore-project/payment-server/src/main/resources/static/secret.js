
(async () => {
  const response = await fetch('/api/payment/pay');
  const {client_secret: clientSecret} = await response.json();
  // Render the form using the clientSecret
})();