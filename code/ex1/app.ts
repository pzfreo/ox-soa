import express from "express";

const app = express();

// handle JSON body requests
app.use(express.json())

interface Random {
    random: number;
}

async function get(req:express.Request, res: express.Response) : 
    Promise<express.Response> {
    const r:  Random =  {
        random: Math.floor((Math.random() * 100) + 1)
    };

    return res.status(200).json(r);
}

app.get("/", get);

const port = process.env.PORT || 8080;
app.listen(port, () =>
  console.log(`Random server listening at http://localhost:${port}`)
);