const { Pool } = require('pg');

require('dotenv').config();

console.log(process.env.DB_USER);


const pool = new Pool({
    user: process.env.DB_USER,
    host: process.env.DB_HOST,
    database: process.env.DB_DATABASE,
    port: process.env.DB_PORT,
    password: process.env.DB_PASSWORD

});

module.exports = pool;