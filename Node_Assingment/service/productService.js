const {Product} = require('../models/index')

const addProduct = async (name, price, quantity) => {
   if(!name || !price || !quantity) {
        const err = new Error("all fields required");
        throw err;
    }

   const product = await Product.create({
        name,
        price,
        quantity
   });
   return product;
}

const getProduct = async (id) => {
    let product = {};
    if(!id){
        product = await Product.findAll();
        return product;
    }
     product = await Product.findByPk(id);
    if(!product){
        const err = new Error("Product now found");
        err.statusCode = 404;
        throw err;
    }
    return product
}

const updateProduct = async (productId, product) => {
    const prod = await Product.findOne({where : {id : productId}});


    if(!prod) {
        const err = new Error("Product not found");
        err.statusCode = 404;
        throw err;
    }

    const updated =  await prod.update(product);
    return updated.toJSON();

}

const deleteProduct = async (productId) => {
  const prod = await Product.findByPk(productId);

  if (!prod) {
    const error = new Error("Product not found");
    error.statusCode = 404;
    throw error;
  }

  await prod.destroy();
  return { message: "Product deleted successfully" };
};


module.exports = {
    addProduct,
    getProduct,
    updateProduct,
    deleteProduct
}