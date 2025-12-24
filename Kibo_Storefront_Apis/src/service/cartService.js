const kiboClient = require("../config/kiboClient")


const createCart = async() => {
    console.log("Inside Create cart");
    try{
    const response = await kiboClient.get('/api/commerce/carts/current');

    console.log("Response", response);
    return response.data;

    }catch(err){
        throw err;
    }
}


const getCartById = async(cart_id) => {
    const response = await kiboClient.get(`/api/commerce/carts/${cart_id}`);
    return response.data;
}


const addItemToCart = async(cartId, productCode, quantity) => {
    const response = await kiboClient.post(`/api/commerce/carts/${cartId}/items`, {
            product : {
                productCode,
            },            
            quantity,
    });
    return response.data;
}


module.exports = {
    addItemToCart,
    createCart,
    getCartById
}