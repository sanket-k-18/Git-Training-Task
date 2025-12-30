const {getAllProductsService, getProductByCodeService, createProductService, deleteProductService} = require("../service/productService");

const getAllProducts = async (req, res) => {
  try {
    const pageSize = Number(req.query.pageSize) || 20;
    const page = Number(req.query.page) || 1;


    const data = await getAllProductsService(page, pageSize);
    res.json(data);
  } catch (err) {
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
};

const getProductByCode = async(req, res) => {
    try{

        const productId = req.params.productId;
        const product = await getProductByCodeService(productId);
        res.json(product);
    }catch(error){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
};

const createProduct = async (req, res) => {
   try{ 
  const createdProduct = await createProductService(req.body);
  res.json(createdProduct);
  }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

const deleteProduct = async (req, res) => {
  try{
    const productId = req.params.productId;
    const deletedProduct = await deleteProductService(productId);
    res.json(deletedProduct);
  }catch(err){
    res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}








module.exports = {getAllProducts, getProductByCode, createProduct, deleteProduct}