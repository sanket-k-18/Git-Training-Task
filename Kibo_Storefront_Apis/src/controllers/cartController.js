const {createCart, getCartById, addItemTocart} = require('../service/cartService');


const getMyCart = async (req, res) => {
    try{
        let cartId = req.user.cartId;
        console.log("CartId", cartId);
        if(!cartId){
            const newCart = await createCart();
            console.log("new Cart", newCart);
            cartId = newCart.id;

            req.user.cartId = cartId;
        }

        const cart = await getCartById(cartId);
        res.json(cart);
    }catch(err){
            res.status(500).json({ message: err.message || "Failed to fetch cart" });
    }
}

const addToCart = async (req, res) => {
    try{
        const {productCode, quantity} = req.body;

        let cartId = req.user.cartId;
        if(!cartId) {
            const newCart = createCart();
            cartId = newCart.id;
            req.user.cartId = cartId;
        }

        const updateCart = await addItemTocart(
            cartId,
            productCode,
            quantity
        );

        res.json(updateCart);
    }catch(err){
        res.status(500).json({ message: "Failed to add item to cart" });
    }
};


module.exports = {getMyCart, addToCart};