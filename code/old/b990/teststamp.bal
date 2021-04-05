import ballerina/io;

type payment record {
    string cardNumber;
    string postcode;
    string name;
    int month;
    int year;
    int cvc;
    string merchant;
    string reference;
    float amount;
};


public function main(string... args) {

    json js = 
    {
        cardNumber: "4544950403888999",
        postcode: "PO107XA",
        name: "P Z FREMANTLE",
        month: 6,
        year: 2017,
        cvc: 999,
        merchant: "A0001",
        reference: "test",
        amount: 11.11
    };

    payment | error p  = payment.convert(js);
    io:println(p);
    
}
