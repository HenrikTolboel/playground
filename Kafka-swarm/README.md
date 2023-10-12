
# kafka docker


https://developer.confluent.io/quickstart/kafka-docker/


(https://bobcares.com/blog/confluent-kafka-docker-compose/)




# single instance

`docker-compose.yml`

```shell
docker exec broker \
    kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic todos --partitions 6 --replication-factor 1
```


```shell
docker exec broker kafka-topics --bootstrap-server broker:9092 --describe todos
```

```shell
docker exec --interactive --tty broker kafka-console-producer --broker-list broker:9092 --topic todos
```

```shell
docker exec --interactive --tty broker kafka-console-consumer  --bootstrap-server broker:9092 --topic todos
docker exec --interactive --tty broker kafka-console-consumer  --bootstrap-server broker:9092 --topic todos --from-beginning
```


# multi instance

`docker-compose-multi.yml`


```shell
docker exec broker-1 \
    kafka-topics --bootstrap-server broker-1:9092 \
             --create \
             --topic todos --partitions 6 --replication-factor 3
```

```shell
docker exec broker-2 kafka-topics --bootstrap-server broker-1:9092 --describe todos
```

```shell
docker exec --interactive --tty broker kafka-console-producer --broker-list broker:9092 --topic todos
```

```shell
docker exec --interactive --tty broker kafka-console-consumer  --bootstrap-server broker:9092 --topic todos
docker exec --interactive --tty broker kafka-console-consumer  --bootstrap-server broker:9092 --topic todos --from-beginning
```



