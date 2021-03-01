CREATE TABLE Products (
  transmission_id   VARCHAR  primary key NOT NULL,
  recordcount INTEGER NOT NULL,
  qtysum INTEGER NOT NULL,
  json_col JSON
);
COMMIT;