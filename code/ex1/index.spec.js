var frisby = require('frisby');
const Joi = frisby.Joi;

it('test random number service', function(done) {
  frisby.get('http://localhost:8080/')
  .expect('status', 201)
  .expect('header', 'Content-Type', 'application/json; charset=utf-8')
  .expect('jsonTypes', 'random', Joi.number().required() )
	.then(function(res) {
		expect(res.json.random).toBeGreaterThan(1);
		expect(res.json.random).toBeLessThan(100);
		}
	)
	.done(done)
});
