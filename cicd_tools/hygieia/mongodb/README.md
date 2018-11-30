username : admin

admin password : mongodb

db name : parks

db password : mongodb

```
use admin
db.auth('admin','mongodb');

use test
db.createCollection("mycollection")
db.createCollection("mycollection2")


use admin
db.createUser(
			{
			  user: "testuser",
			  pwd: "testpassword",
			  roles: [
			  {role: "readWrite", db: "test"}
			  ]
			})

```