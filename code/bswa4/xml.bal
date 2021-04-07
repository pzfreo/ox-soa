import ballerina/io;

public function main() {

        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<wrapper><pay:ping><pay:txt>text</pay:txt></pay:ping></wrapper>`;
        io:println("body:", body);
        var some = body/**/<pay:txt>;
        io:println("some:", some);
        io:println("some.text():", some[0].text());
            
}