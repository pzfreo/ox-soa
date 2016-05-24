var frisby = require('frisby');

frisby.create('Test Random Number service')
  .get('http://localhost:8080/')
  .expectStatus(200)
  .expectHeaderContains('Content-Type', 'application/json')
  .expectJSONTypes( {
    random: Number
    }
  )
  .expectJSON({
    random: function(v) { expect(v).toBeGreaterThan(0);expect(v).toBeLessThan(101);}
  })
.toss();