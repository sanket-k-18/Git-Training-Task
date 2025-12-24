const { DataTypes } = require('sequelize')
const sequelize = require('../config/db.config')

const user = sequelize.define('user', {
    user_id :{
        type : DataTypes.INTEGER,
        primaryKey : true,
        autoIncrement : true
    },
    email : {
        type : DataTypes.STRING,
        allowNull : false,
        unique : true
    },
    password : {
        type:DataTypes.STRING,
        allowNull: false
    },
    cart_id : {
        type: DataTypes.STRING,
        allowNull: true
    }

});

module.exports = user;