const { createTask, getTasks, updateTask, deleteTask } = require("../controllers/tasksController");

const taskRoutes=(req,res) =>{
//TODO  define


if(req.method==='GET'){

    getTasks(req,res);

}

else if (req.method==='POST'){ 
    createTask(req,res);
}
else if (req.method==='PATCH'){
    updateTask(req,res);
}
else if (req.method==='DELETE'){
    deleteTask(req,res);
}

else {
    res.writeHead(404, 'Data Not Found', { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ message: 'Unknown Method required.' }));
}


}


module.exports=taskRoutes;