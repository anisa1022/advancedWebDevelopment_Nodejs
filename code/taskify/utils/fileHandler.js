const fs = require('fs');
const path = require('path');

const filePath = path.join(__dirname, '../data/tasks.json');

// Write tasks to file
exports.writeTasksToFile = (tasks) => {
    fs.writeFileSync(filePath, JSON.stringify(tasks,null, 2));   // modifying to json file:1 commit message
};

// Read tasks from file
exports.readTaskFromFile = () => {
    // If file does not exist, initialize it with an empty array
    if (!fs.existsSync(filePath)) {
        this.writeTasksToFile([]);  // Use `exports.writeTasksToFile` directly
    }
    const data = fs.readFileSync(filePath);

    return JSON.parse(data);
};
