const {getOrCreateCart} = require('../service/cartService');


const getOrCreate = async (req, res) => {
    try{
        const cart = await getOrCreateCart();
        res.json(cart);
    }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}




module.exports = {getOrCreate};