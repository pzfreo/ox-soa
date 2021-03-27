import express from "express";
import bodyParser from "body-parser";


const app = express();


app.use(bodyParser.json())

interface Name {
    firstName: string;
    lastName: string;
}

async function get(req:express.Request, res: express.Response) : Promise<express.Response> {
    const paul:  Name =  {
        firstName: "Paul",
        lastName: "Fremantle"
    };

    return res.json(paul);
}

app.get("/", get);

const port = process.env.PORT || 8000;

app.listen(port, () =>
  console.log(`Simple app listening at http://localhost:${port}`)
);