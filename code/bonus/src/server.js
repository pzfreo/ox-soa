var express     =   require("express");
var app         =   express();
var bodyParser  =   require("body-parser");
var mongoose = require('mongoose');
mongoose.connect('mongodb://mongo-data/test');
module.exports.mongoose = mongoose;
var Customer     =   require("./Customer.js");

var router      =   express.Router();
var db = mongoose.connection;

console.log("starting");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({"extended" : false}));

router.get("/",function(req,res){
    res.json({"message" : "mongo express rest app"});
});

router.route("/customers")
    .get(function(req,res){
	    Customer.find({}, function(err, data) {
	    if (!err) {
	    	for (i in data) {
				data[i].__v = undefined;
				data[i]._id = undefined;
	    	}
	    	res.json(data);
	    }
	    });
    })
    .post(function(req,res){
		var c = new Customer(req.body);
		c.save()
		Customer.findOne({id:c.id}, function(err, data) {
			
			if (!err) {
				// remove internal fields from Mongo
				data.__v = undefined;
				data._id = undefined;
				
				res.set("Location","/customers/"+c.id);
				res.status(201).json(data);
			}
		});
    });

router.route("/customers/:id")
    .get(function(req,res){
        var response = {};
        Customer.findOne({id: req.params.id} ,function(err,data){
        // This will run Mongo Query to fetch data based on ID.
            if(!err) {
                // remove internal fields from Mongo
				data.__v = undefined;
				data._id = undefined;
				
				res.json(data);
                               
            }
            else {
            	res.sendStatus(404);
			}
        });
    })



app.use('/',router);

app.listen(8000);
console.log("Listening to PORT 8000");