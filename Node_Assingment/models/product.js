const { DataTypes } = require('sequelize');
const sequilize = require('../db.config');

const product = sequilize.define('product', {
    id : {
        type : DataTypes.INTEGER,
        primaryKey : true,
        autoIncrement : true
    },
    name : {
        type : DataTypes.STRING,
        allowNull : false, 
    },
    price : {
        type : DataTypes.FLOAT,
        allowNull : false,
    },
    quantity : {
        type : DataTypes.INTEGER,
        allowNull : false,
    }
});

module.exports = product;