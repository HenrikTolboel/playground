# Fetch data from docker mysql database

In advance have a datasbe "db1" created with "multi-mysql"

run-mysql.sh db1

create a table "MyTable" with 3 colums and values in a couple of rows

use e.g.

run-phpMyAdmin.sh db1 8081

open localhost:8081

create table and values....


# Build php image:
docker build -t my-php .

# Run image with local script

docker run -it --rm --network my-net --name my-running-script -v "$PWD":/usr/src/myapp -w /usr/src/myapp my-php php test.php > t.html; open t.html

t should show a simple table with your values.

