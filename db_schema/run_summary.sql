CREATE TABLE IF NOT EXISTS run_summary (
  summary_id int NOT NULL AUTO_INCREMENT,
  summary_name varchar(45) NOT NULL,
  username varchar(45) NOT NULL,
  start_date Date,
  end_date Date,
  distance double,
  pace double,
  route VARBINARY(4096), 
  PRIMARY KEY(summary_id)
);