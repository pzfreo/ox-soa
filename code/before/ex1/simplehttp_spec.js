var frisby = require('frisby');

frisby.create('Test Random Number service')
  .get('http://localhost:8080/')
  .expectStatus(200)
  .expectHeaderContains('content-type', 'application/json')
  .expectJSON({
    random: function(v) { expect(v).toBeGreaterThan(0);expect(v).toBeLessThan(51);}
  })
  .expectJSONTypes( {
    random: Number
    }
  )
.toss();
