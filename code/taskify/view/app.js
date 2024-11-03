const http = require('http');
const taskRoutes = require('./routes/taskRoutes');

const port = 9000;
const hostname = "localhost";

const server = http.createServer((req, res) => {
    if (req.url.startsWith('/tasks')) {
        taskRoutes(req, res); 
    } else {
        res.writeHead(404, 'Not Found', { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ message: 'sorry you got lost !' }));
    }
});

server.listen(port, hostname, () => {
    console.log(`Server running on ${port}`);
});
