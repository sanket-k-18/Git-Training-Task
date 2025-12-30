const kiboClient = require('../config/kiboClient');


const getAllProductsService = async (page = 1, pageSize = 10) => {
  try {
    const startIndex = (page - 1) * pageSize;

    const response = await kiboClient.get(
      '/api/commerce/catalog/storefront/products',
      {
        params: {
          startIndex,
          pageSize
        }
      }
    );
    return {
      items: response.data.items,
      totalCount: response.data.totalCount,
      page,
      pageSize
    };
  } catch (error) {
    throw error;
  }
};

const getProductByCodeService = async (productCode) => {
    const response = await kiboClient.get(`/api/commerce/catalog/storefront/products/${productCode}`);
    return response.data;
}

const createProductService = async (product) => {
  const response = await kiboClient.post("/api/commerce/catalog/admin/products", product);
  return response.data;
}

module.exports = {getAllProductsService, getProductByCodeService, createProductService};