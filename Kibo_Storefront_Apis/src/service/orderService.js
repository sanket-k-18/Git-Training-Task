const kiboClient = require("../config/kiboClient")



const   createOrder = async (order) => {
    const response = await kiboClient.post("api/commerce/orders", order);
    return response.data;
}



module.exports = {createOrder};