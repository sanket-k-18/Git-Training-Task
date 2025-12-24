const axios = require('axios');

let kiboToken = null;
let tokenExpiryTime = null;

const KIBO_AUTH_URL = "https://t41316.sandbox.mozu.com/api/platform/applications/authtickets/oauth"

const APP_ID = process.env.KIBO_APP_ID;
const SHARED_SECRET = process.env.KIBO_SHARED_SECRET;

const getKiboToken = async () => {

    if(kiboToken && tokenExpiryTime && Date.now() < tokenExpiryTime){
        return kiboToken;
    }

    const data = {
        client_id: APP_ID,
        client_secret: SHARED_SECRET,
        grant_type: 'client'
};

    const response = await axios.post(
        KIBO_AUTH_URL,
        data,
        {
        headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'           
             }
        }
    );

    kiboToken = response.data.access_token;
    tokenExpiryTime = Date.now() + (response.data.expires_in - 60) * 1000;

    return kiboToken;
};

module.exports = getKiboToken;

