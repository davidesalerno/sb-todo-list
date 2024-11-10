db = db.getSiblingDB('todo_db');

db.createCollection('log');
db.createUser(
    {
        user: "todo_user",
        pwd: "passwd",
        roles: [
            {
                role: "readWrite",
                db: "todo_db"
            }
        ]
    }
);

