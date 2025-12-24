const {Sequelize} = require("sequelize");

const sequilize = new Sequelize("ignitiv", "postgres", "root", {
    host : 'localhost',
    dialect : 'postgres'
});

sequilize.authenticate()
.then (() => {
    console.log("Database connected");
}).catch(err => {
    console.log("Error" , err);
});

module.exports = sequilize;