

const Product = require('./product');
const Order = require('./order');

Product.hasMany(Order, {foreignKey : 'product_id'});
Order.belongsTo(Product, {foreignKey : 'product_id'});

module.exports = {
    Product,
    Order
}