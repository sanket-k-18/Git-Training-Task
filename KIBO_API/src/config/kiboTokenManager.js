const axios = require("axios");

let token = null;
let expiry = null;

const getKiboToken = async () => {
  console.log("getKiboToken called");

  if (token && expiry && Date.now() < expiry) {
    console.log("Using cached token");
    return token;
  }

  try {
    const response = await axios.post(
      `${process.env.KIBO_BASE_URL}/api/platform/applications/authtickets/oauth`,
      {
        client_id: process.env.KIBO_APP_ID,
        client_secret: process.env.KIBO_SHARED_SECRET,
        grant_type: "client"
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        }
      }
    );

    console.log("Token API response:", response.data);

    token = response.data.access_token;
    expiry = Date.now() + (response.data.expires_in - 60) * 1000;

    return token;
  } catch (err) {
    console.error("Token API error:", err.response?.data || err.message);
    throw err;
  }
};

module.exports = getKiboToken;
