CREATE KEYSPACE IF NOT EXIST cInfo WITH REPLICATION={'class':'SimpleStrategy','replication_factor'=1};
  USE cInfo;
CREATE TABLE IF NOT EXIST customers(
  id int PRIMARY KEY,
  lastname text,
  firstname text,
  balance double
);