const client_id = 'purchase';
const client_secret = process.env.CLIENT_SECRET;
const oauth2_host = process.env.OAUTH2_HOST || "localhost";
const oauth2_port = process.env.OAUTH2_PORT || 8080;

const introspect_url = 
`http://${oauth2_host}:${oauth2_port}/realms/purchase/protocol/openid-connect/token/introspect`;

app.use(introspect(introspect_url,client_id,client_secret));

