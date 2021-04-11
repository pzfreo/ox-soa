const FINGER="63:29:60:26:0E:53:1A:F5:89:97:11:2D:9F:58:A5:B7:44:4B:11:78";
app.use(function (req:express.Request, res:express.Response, next:express.NextFunction)
{ 
  const tlsSocket = req.socket as TLSSocket;
  const incomingFinger = tlsSocket.getPeerCertificate().fingerprint;
  if (incomingFinger != FINGER) {
    res.status(401).send("Unauthorized");
  }
  next();
});