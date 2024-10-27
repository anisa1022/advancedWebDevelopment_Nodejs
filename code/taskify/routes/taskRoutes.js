const { deleteTask, getTask ,updateTask,createTask } = require("../controllers/taskController")

const taskRoutes = (req ,res)=>{
    if(req.method == 'GET'){
        getTask(req,res)
    }
    else if(req.method == 'POST'){
        createTask(req,res)

    }
    else if(req.method == 'PATCH'){
        updateTask(req,task)

    }
    else if(req.method == 'DELETE'){
        deleteTask(req,res)
    }
    else{
        res.writeHead(404, 'NOT FOUND',{'content-typ': 'application/json'})
        res.end(JSON.stringify({
            message:'Page not found'
        }))
    }
}

module.exports = taskRoutes