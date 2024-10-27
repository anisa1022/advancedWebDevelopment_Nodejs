const fs  = require('fs')
exports.writeTasksToFile = (data)=>{
    
    fs.writeFileSync('./../data/task.json',JSON.stringify(data) );
    return data;
}

exports.readTasksFromFile = ()=>{
    if(fs.existsSync('./../data/task.json')){
        this.writeTasksToFile([]);
    }
    const data = fs.readFileSync('./../data/task.json');

    return JSON.parse(data);
}


