# Teradata Batch Source


Description
-----------
Reads from a Teradata using a configurable SQL query.
Outputs one record for each row returned by the query.


Use Case
--------
The source is used whenever you need to read from a Teradata. For example, you may want
to create daily snapshots of a database table by using this source and writing to
a TimePartitionedFileSet.


Properties
----------
**Reference Name:** Name used to uniquely identify this source for lineage, annotating metadata, etc.

**Driver Name:** Name of the JDBC driver to use.

**Host:** Host that Teradata is running on.

**Port:** Port that Teradata is running on.

**Database:** Teradata database name.

**Import Query:** The SELECT query to use to import data from the specified table.
You can specify an arbitrary number of columns to import, or import all columns using \*. The Query should
contain the '$CONDITIONS' string. For example, 'SELECT * FROM table WHERE $CONDITIONS'.
The '$CONDITIONS' string will be replaced by 'splitBy' field limits specified by the bounding query.
The '$CONDITIONS' string is not required if numSplits is set to one.

**Bounding Query:** Bounding Query should return the min and max of the values of the 'splitBy' field.
For example, 'SELECT MIN(id),MAX(id) FROM table'. Not required if numSplits is set to one.

**Split-By Field Name:** Field Name which will be used to generate splits. Not required if numSplits is set to one.

**Number of Splits to Generate:** Number of splits to generate.

**Username:** User identity for connecting to the specified database.

**Password:** Password to use to connect to the specified database.

**Connection Arguments:** A list of arbitrary string key/value pairs as connection arguments. These arguments
will be passed to the JDBC driver as connection arguments for JDBC drivers that may need additional configurations.

**Schema:** The schema of records output by the source. This will be used in place of whatever schema comes
back from the query. However, it must match the schema that comes back from the query,
except it can mark fields as nullable and can contain a subset of the fields.

**Fetch Size:** The number of rows to fetch at a time per split. Larger fetch size can result in faster import,
with the tradeoff of higher memory usage.

Example
------
Suppose you want to read data from Teradata database named "prod" that is running on "localhost" port 1025,
as "postgres" user with "postgres" password (Ensure that the driver for Teradata is installed. You can also provide 
driver name for some specific driver, otherwise "teradata" will be used),  then configure plugin with: 


```
Reference Name: "src1"
Driver Name: "teradata"
Host: "localhost"
Port: 1025
Database: "prod"
Import Query: "select id, name, email, phone from users;"
Number of Splits to Generate: 1
Username: "dbc"
Password: "dbc"
```  

For example, if the 'id' column is a primary key of type int and the other columns are
non-nullable varchars, output records will have this schema:

    | field name     | type                |
    | -------------- | ------------------- |
    | id             | int                 |
    | name           | string              |
    | email          | string              |
    | phone          | string              |

Data Types Mapping
------
Teradata specific data types mapped to string and can have multiple input formats and one 'canonical' output form.
Please, refer to Teradata data types documentation to figure out proper formats.

| Teradata Data Type                                  | CDAP Schema Data Type | Comment                                      |
|-----------------------------------------------------|-----------------------|----------------------------------------------|
| BYTEINT                                             | INT                   |                                              |
| SMALLINT                                            | INT                   |                                              |
| INTEGER                                             | INT                   |                                              |
| BIGINT                                              | LONG                  |                                              |
| DECIMAL/NUMERIC                                     | DECIMAL               |                                              |
| FLOAT/REAL/DOUBLE PRECISION                         | DOUBLE                |                                              |
| NUMBER                                              | DECIMAL               |                                              |
| BYTE                                                | BYTES                 |                                              |
| VARBYTE                                             | BYTES                 |                                              |
| BLOB                                                | BYTES                 |                                              |
| CHAR                                                | STRING                |                                              |
| VARCHAR                                             | STRING                |                                              |
| CLOB                                                | STRING                |                                              |
| DATE                                                | DATE                  |                                              |
| TIME                                                | TIME_MICROS           |                                              |
| TIMESTAMP                                           | TIMESTAMP_MICROS      |                                              |
| TIME WITH TIME ZONE                                 | TIME_MICROS           |                                              |
| TIMESTAMP WITH TIME ZONE                            | TIMESTAMP_MICROS      |                                              |
| INTERVAL YEAR                                       | STRING                |                                              |
| INTERVAL YEAR TO MONTH                              | STRING                |                                              |
| INTERVAL MONTH                                      | STRING                |                                              |
| INTERVAL DAY                                        | STRING                |                                              |
| INTERVAL DAY TO HOUR                                | STRING                |                                              |
| INTERVAL DAY TO MINUTE                              | STRING                |                                              |
| INTERVAL DAY TO SECOND                              | STRING                |                                              |
| INTERVAL HOUR                                       | STRING                |                                              |
| INTERVAL HOUR TO MINUTE                             | STRING                |                                              |
| INTERVAL HOUR TO SECOND                             | STRING                |                                              |
| INTERVAL MINUTE                                     | STRING                |                                              |
| INTERVAL MINUTE TO SECOND                           | STRING                |                                              |
| INTERVAL SECOND                                     | STRING                |                                              |
| ST_Geometry                                         | STRING                |                                              |