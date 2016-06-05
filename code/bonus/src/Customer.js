var mongoose = module.parent.exports.mongoose;
var customerSchema = mongoose.Schema({
    name: String,
    id: {type: String, unique: true},
    email: String
});
var customer = mongoose.model('Customer', customerSchema);
module.exports = customer;