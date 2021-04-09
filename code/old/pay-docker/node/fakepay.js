var express   =    require("express");
var app       =    express();
var uuid = require("uuid");

app.get("/pay*",function(req,res){

	urlparam = ""
	if (req.originalUrl.length > 5) urlparam = req.originalUrl.substr(5);
	
    data = {return : urlparam}
    res.json(data);
});

app.post("/pay*", function(req,res) {
	data = {authcode: "TEST0001", reference:uuid.v4(), refusalreason: "OK"}
	res.json(data);
});

app.listen(8002);