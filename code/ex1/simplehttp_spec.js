var frisby = require('frisby');


it('test random number service', function(doneFn) {
  frisby.get('http://localhost:8080/')
  .expectStatus(201)
  .expectHeaderContains('Content-Type', 'application/json')
  .expectJSONTypes( {
    random: Number
    }
  )
  .expectJSON({
    random: function(v) { expect(v).toBeGreaterThan(0);expect(v).toBeLessThan(101);}
  })
.doneFn()
});