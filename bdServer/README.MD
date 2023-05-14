## PASOS PARA CONFIGURAR MAESTRO ESCLAVO

* Una vez iniciados los contenedores hacer lo siguiente

 ~~~
docker-compose build

docker-compose up -d / docker-compose up
 ~~~

### CONFIGURAR MASTER DESDE CONSOLA

```

docker exec -it mysql_master mysql -u root -p

CREATE USER '{nombre}'@'%' IDENTIFIED BY '{contrasena}';

GRANT REPLICATION SLAVE ON *.* TO 'nombre'@'%';

FLUSH PRIVILEGES;

FLUSH TABLES WITH READ LOCK;


SHOW MASTER STATUS\G;

```



* Del último comando nos saldra algo parecido a lo siguiente

~~~
mysql> SHOW MASTER STATUS\G
*************************** 1. row ***************************
             File: mysql.000003
         Position: 829
     Binlog_Do_DB: congestiones
 Binlog_Ignore_DB:
Executed_Gtid_Set:
1 row in set (0.04 sec) 
~~~

* Tomamos los valores de File y Position y continuamos con la configuracion del SLAVE


### CONFIGURAR SLAVE DESDE CONSOLA


~~~

docker exec -it mysql_slave mysql -u root -p

CHANGE MASTER TO MASTER_HOST="mysql_master",
MASTER_USER="{nombre}",
MASTER_PASSWORD="{contrasena}",
MASTER_LOG_FILE="{valor de la salida File en la configuracion master} ",
MASTER_LOG_POS={valor de la salida Position en la configuracion master}

CHANGE MASTER TO MASTER_HOST="mysql_master",
MASTER_USER="{nombre}",
MASTER_PASSWORD="{contrasena}",
MASTER_LOG_FILE="mysql.000003",
MASTER_LOG_POS=829;




~~~

* Despues iniciamos la replicacion en el slave con el siguiente comando y mostramos el estado donde debe salira una salida similar.

~~~
START SLAVE;

SHOW SLAVE STATUS\G;
*************************** 1. row ***************************
               Slave_IO_State: Waiting for source to send event
                  Master_Host: mysql_master
                  Master_User: slave
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql.000003
          Read_Master_Log_Pos: 829
               Relay_Log_File: 4008c12f2798-relay-bin.000002
                Relay_Log_Pos: 322
        Relay_Master_Log_File: mysql.000003
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB:
          Replicate_Ignore_DB:
           Replicate_Do_Table:
       Replicate_Ignore_Table:
      Replicate_Wild_Do_Table:
  Replicate_Wild_Ignore_Table:
                   Last_Errno: 0
                   Last_Error:
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 829
              Relay_Log_Space: 539
              Until_Condition: None
               Until_Log_File:
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File:
           Master_SSL_CA_Path:
              Master_SSL_Cert:
            Master_SSL_Cipher:
               Master_SSL_Key:
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error:
               Last_SQL_Errno: 0
               Last_SQL_Error:
  Replicate_Ignore_Server_Ids:
             Master_Server_Id: 1
                  Master_UUID: af73d416-f20b-11ed-acf7-0242ac1d0002
             Master_Info_File: mysql.slave_master_info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Replica has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind:
      Last_IO_Error_Timestamp:
     Last_SQL_Error_Timestamp:
               Master_SSL_Crl:
           Master_SSL_Crlpath:
           Retrieved_Gtid_Set:
            Executed_Gtid_Set:
                Auto_Position: 0
         Replicate_Rewrite_DB:
                 Channel_Name:
           Master_TLS_Version:
       Master_public_key_path:
        Get_master_public_key: 0
            Network_Namespace:
1 row in set, 1 warning (0.01 sec)
~~~


### VOLVEMOS A MASTER 

* Desbloqueamos la escritura en las tablas

~~~

UNLOCK TABLES;

~~~

#### Y para finalizar probamos si realmente se estan replicando.
* CREAMOS UNA TABLA DE PRUEBA EN MASTER
~~~
CREATE TABLE congestion  ( idCongestion INTEGER PRIMARY KEY NOT NULL UNIQUE AUTO_INCREMENT,  descripcion VARCHAR(20) NOT NULL);

INSERT INTO congestion (descripcion) VALUES ("Accidente");

~~~

*  Y VERIFICAMOS EN EL SLAVE

~~~
use congestion;
SELECT * FROM congestion;
+--------------+-----------------+
| idCongestion | descripcion     |
+--------------+-----------------+
|            1 | Accidente       |
+--------------+-----------------+
1 row in set (0.01 sec)
~~~

_NOTA: Las tablas creadas son solo para probar la replicación._