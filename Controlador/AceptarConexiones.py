"""
Clase para aceptar las conexiones entrantes en cierto seocket
"""

from threading import Thread
import os
class Acceptor(Thread):
    def __init__(self, serverSock, cons, num):
        Thread.__init__(self)
        self.server = serverSock
        self.clients = cons
        self.numero = num
    #Acepta las conexiones
    def run(self):
        while True:
            client_socket, addr = self.server.accept() #Acepta la nuieva conexion
            self.clients.append(client_socket)          #La agrega a la lista de conexiones
            print("nuevo Cliente aceptado"+ str(addr[0]))
            if addr[0]=='127.0.0.1':                    #Deja de recibir conexiones si se manda la petición desde DetenerServidor
                self.clients.remove(client_socket)
                break
            elif len(self.clients)==self.numero:                 
                break
