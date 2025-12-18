const { DataTypes } = require('sequelize');
const sequilize = require('../db.config');


const order = sequilize.define('order', {
    id : {
        type : DataTypes.INTEGER,
        primaryKey : true,
        autoIncrement : true
    },
    product_id : {
        type : DataTypes.INTEGER,
        allowNull : false,
        refferences : {
            model : 'products',
            key : 'id'
        }
    },
    quantity : {
        type : DataTypes.INTEGER,
        allowNull : false, 
    },
    total_price : {
        type : DataTypes.FLOAT,
        allowNull : false,
    }
});


module.exports = order;