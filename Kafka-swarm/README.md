
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



```
➜  services git:(master) ✗ kcat -b localhost:9092 -t henrikTopic
% Auto-selecting Consumer mode (use -P or -C to override)
{"event_time":"2023-10-12T13:12:37.561Z","event_name":"ACCOUNT_TEST","account_id":"TESTEVENT_accountId"}
{"event_time":"2023-10-12T13:17:12.629Z","event_name":"ACCOUNT_TEST","account_id":"TESTEVENT_accountId"}
% Reached end of topic henrikTopic [2] at offset 5
% Reached end of topic henrikTopic [0] at offset 26
% Reached end of topic henrikTopic [1] at offset 3
^C%
```

