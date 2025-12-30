const kiboClient = require("../config/kiboClient")


const getOrCreateCart = async() => {
    try{
    const response = await kiboClient.get('/api/commerce/carts/current');
    return response.data;
    }catch(err){
        console.log("error in getOrCreateCart:", err.message);
        throw err;
    }
}





module.exports = {
    getOrCreateCart,
}