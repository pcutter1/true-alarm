## Data Definition Language

```sqlite
CREATE TABLE IF NOT EXISTS `Alarm`
(
`alarm_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 `name` TEXT COLLATE NOCASE,
 `is_set` INTEGER NOT NULL,
 `arrival_time` INTEGER NOT NULL,
 `alert_buffer` INTEGER NOT NULL,
 `start_location_id` INTEGER NOT NULL,
 `end_location_id` INTEGER NOT NULL,
 FOREIGN KEY(`start_location_id`) REFERENCES `Location`(`location_id`) ON UPDATE NO ACTION ON DELETE SET NULL ,
  FOREIGN KEY(`end_location_id`) REFERENCES `Location`(`location_id`) ON UPDATE NO ACTION ON DELETE SET NULL
  );

  CREATE INDEX IF NOT EXISTS `index_Alarm_start_location_id` ON `Alarm` (`start_location_id`);

  CREATE INDEX IF NOT EXISTS `index_Alarm_end_location_id` ON `Alarm` (`end_location_id`);

  CREATE TABLE IF NOT EXISTS `Location`
   (
   `location_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
   `latitude` REAL NOT NULL, `longitude` REAL NOT NULL,
   `address` TEXT COLLATE NOCASE,
   `name` TEXT COLLATE NOCASE
   );

   CREATE UNIQUE INDEX IF NOT EXISTS `index_Location_name_address` ON `Location` (`name`, `address`);
```
[`ddl.sql`](sql/ddl.sql)