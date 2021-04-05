var http = require('http'),
    express = require('express'), 
    app = express();

app.get("/",function(req,res){
    obj = {random : Math.floor((Math.random() * 100) + 1)};
	res.json(obj);
});

var server = app.listen(8080, function() {
    console.log("Random server listening on port 8080");
});

