const productService = require('../service/productService');

const addProduct = async(req, res) => {
    const {name, price, quantity} = req.body;
    try{

    const product =  await productService.addProduct(name, price, quantity);
    res.status(201).json(product);

    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}

const getProduct = async(req, res) => {
    const id = req.params.id;

    try{
    const product = await productService.getProduct(id);
   
    return res.status(200).json(product);

    }catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}


const updateProduct = async (req, res) => {
    const id = req.query.id;
    const {name, price, quantity} = req.body;
    
    try{
    let product = {};
    if(name){
        product.name = name;
    }

    if(price){
        product.price = price;
    }

    if(quantity){
        product.quantity = quantity;
    }

    let updatedProduct = null;
    if(product) updatedProduct = await productService.updateProduct(id, product);

   return  res.status(200).json(updatedProduct);

    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}


const deleteProduct = async (req, res) => {
  try {
    const { id } = req.params;
    const result = await productService.deleteProduct(id);
    return res.status(200).json(result);
  } catch (err) {
    return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
  }
};


module.exports = {
    addProduct,
    updateProduct,
    deleteProduct,
    getProduct
}