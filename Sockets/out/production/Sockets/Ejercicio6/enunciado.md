Desarrolla una aplicación cliente-servidor distribuida utilizando sockets TCP, donde el
servidor pueda atender múltiples clientes simultáneamente mediante el uso de hilos.
Cada cliente se conecta al servidor y envía un número entero.
El servidor mantiene un contador global compartido (accesible por todos los hilos) que
acumula la suma total de los números enviados por todos los clientes.
Cada vez que un cliente envía un número, el servidor actualiza el contador global y devuelve al cliente el valor acumulado actual.