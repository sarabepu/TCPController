"""
 Controlador de las conexiones del experimento, recibe la cantidad mínima y maneja la descarga 
 y eliminación del torrent local.torrent a los clientes
"""

import socket
import time
import os
import datetime 
import threading
from AceptarConexionesO import Acceptor
import DetenerServidor
import ejecutar
from servidor import Server

     
# minima es Cantidad minima de clientes para el experimento
minima=1
#Sincroniza el inicio del experimento para el multiplo más cercano a 15 minutos 
t1 = datetime.datetime.today()
hora=t1.hour
minu=0
dia=t1.day
while(t1.minute-minu>0):
    minu+=15
if minu==60:
    minu=0
    if hora<23:
        hora+=1
    else:
        hora=0
        dia+=1
#Espera hasta la hora 
ejecutar.sleep_till_future(dia, hora,minu)
# Realizar el experimento 3 veces
for i in range(3):  
    clients = []
    # Crea el servidor socket
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(("", 44444))
    server.listen(5)
    # Acepta las conexiónes entrantes por un minuto
    aceptador = Acceptor(server, clients)
    aceptador.start()
    time.sleep(60)
    print("desperto")
    # Deja de recibir conexiones despues del minuto
    DetenerServidor.send_stop()
    time.sleep(2)
    # Verifica que la cantidad de clientes recibidos supera la cantidad minima para el experimento
    if len(clients) >= minima: 
        print("empezando prueba" + str(i))
        finalizados = []
        #Envia la orden de empezar a descargar el torrent a cada cliente
        evento = threading.Event()
        for clienteActual in clients:
            try:
                nuevo= Server(clienteActual,finalizados,len(clients),evento,i)
                nuevo.start()
            except Exception as ex:
                print(str(ex))
                pass
        
        #Verifica si todos los clientes ya mandaron el tiempo de descarga
        while len(finalizados)<len(clients):
            time.sleep(1)
        print("descargo")
        evento.set()
        server.close()
    else:
        server.close()