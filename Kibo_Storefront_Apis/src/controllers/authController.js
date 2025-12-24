const bcrypt = require('bcrypt')
const jwt = require("jsonwebtoken");

const {User} = require('../models');

const register = async(req, res) => {
    const {email, password} = req.body;

    try{
    if(!email && !password){
        const err = new Error("email or password not provided");
        err.statusCode = 400;
        throw err;
    }

    const user = await User.findOne({where : {email}});
    if(user){
        const err = new Error("user exist");
        err.statusCode = 404;
        throw err;
    }

    const hashedPassword = await bcrypt.hash(password, 12);
    const newUser = await User.create({email, password :  hashedPassword});
    return res.status(201).json({message : "user registered", newUser});

}catch(err){
    res.status(err.statusCode || 500).json({message : err.message || "Internal server error"})    
}
}


const login = async (req, res) => {
    console.log("email", req.body);
    const {email, password} = req.body;
    try{
    if(!email && !password){
        const err = new Error("email or password not provided");
        err.statusCode = 400;
        throw err;
    }

    const user = await User.findOne({email});
    if(!user){
        const err = new Error("User not found");
        err.statusCode = 404;
        throw err;
    }

    if(!bcrypt.compare(password, user.password)){
        const err = new Error("Invalid credintials");
        err.statusCode = 400;
        throw err;
    }

    const token = jwt.sign({email}, process.env.JWT_SECRET, {expiresIn : '1h'});
    res.status(200).json({message : "login successfulls", token : token});
    }catch (err){
        res.status(err.statusCode || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
    
}

module.exports = {
    register,
    login
}