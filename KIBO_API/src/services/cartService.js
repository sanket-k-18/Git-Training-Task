const kiboClient = require("../config/kiboClient.js");

const createCart = async () => {
  const response = await kiboClient.get("/api/commerce/carts/current");
  return response.data;
};

const getCart = async (cartId) => {
  const response = await kiboClient.get(`/api/commerce/carts/${cartId}`);
  return response.data;
};

const addItemToCart = async (cartId, productCode, quantity) => {
  const response = await kiboClient.post(
    `/api/commerce/carts/${cartId}/items`,
    {
      product: { productCode },
      quantity
    }
  );
  return response.data;
};

module.exports = { createCart, getCart, addItemToCart };
