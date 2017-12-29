var frisby = require('frisby');
const Joi = frisby.Joi;



it('test random number service', function(done) {
  frisby.get('http://localhost:8080/')
  .expect('status',200)
  .expect('header', 'Content-Type', 'application/json; charset=utf-8')
  .expect('jsonTypes', '/',  { random: Joi.number() })
	.then(function(res) {
		expect(res.json.random).toBeGreaterThan(0);
		expect(res.json.random).toBeLessThan(51);
		}
	).done(done)

});
