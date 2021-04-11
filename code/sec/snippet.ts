const port = process.env.PORT || 8443;

const base_dir = "/home/oxsoa/sec/server/keys";

const secureServer = createServer({
  key: readFileSync(base_dir+'/private/server-nopass.key.pem'),
  cert: readFileSync(base_dir+'/server.cert.pem'),
  ca: readFileSync(base_dir+'/ca.cert.pem'),
  requestCert: true,
  rejectUnauthorized: false
}, app);

secureServer.listen(port, function() {
  console.log(`Secure Purchase app listening at https://localhost:${port}`);
});


// app.listen(port, () =>
//   console.log(`Purchase app listening at http://localhost:${port}`)
// );