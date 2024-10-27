const http = require("http");
const taskRoutes = require("../routes/taskRoutes");


const PORT = 9000;
const HOSTNAME = 'localhost';
const server = http.createServer((req,res)=>{
    if(req.url.startsWith('./tasks')){
        taskRoutes(req,res)
    }
    else{
        res.writeHead(404, 'NOT FOUND',{'content-typ': 'application/json'})
        res.end(JSON.stringify({
            message:'Page not found'
        }))
    }
});

server.listen(PORT, HOSTNAME ,()=>{
    console.log(`server running at ${PORT}`);

})