create table user_info(
   username VARCHAR(45) NOT NULL,
   password VARCHAR(45) NOT NULL,
   photo VARBINARY(10000) ,
   PRIMARY KEY ( username )
);