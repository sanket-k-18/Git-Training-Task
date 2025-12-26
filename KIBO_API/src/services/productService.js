const kiboClient = require("../config/kiboClient.js");

const getProducts = async (page = 1, pageSize = 20) => {
    try {
        console.log("Calling Kibo Storefront Products API");

        const response = await kiboClient.get(
            "/api/commerce/catalog/storefront/products",
            {
                params: {
                    startIndex: (page - 1) * pageSize,
                    pageSize
                }
            }
        );

        return response.data;
    } catch (err) {
        console.error(
            "Kibo Products API Error:",
            err.response?.data || err.message
        );
        throw err;
    }
};


const getProductByCode = async (productCode) => {
    const response = await kiboClient.get(
        `/api/commerce/catalog/storefront/products/${productCode}`
    );
    return response.data;
};

module.exports = { getProducts, getProductByCode };
