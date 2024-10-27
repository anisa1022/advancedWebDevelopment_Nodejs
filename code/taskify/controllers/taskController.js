
const { readTasksFromFile } = require("../utils/fileHandler")

exports.getTask = (req, res)=>{
    const tasks = readTasksFromFile();
    res.end(taskRoutes)
}
exports.createTask = (req, res)=>{
    
}
exports.updateTask = (req, res)=>{
    
}
exports.deleteTask = (req, res)=>{
    
}