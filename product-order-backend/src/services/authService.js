const jwt = require("jsonwebtoken");
const SECRET = "secret123";

exports.login = ({ username, password }) => {
  if (username === "admin" && password === "admin123") {
    return jwt.sign({ username }, SECRET, { expiresIn: "1h" });
  }
  return null;
};
