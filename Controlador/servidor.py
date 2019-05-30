
from threading import Thread, Lock


class Server (Thread):

    def __init__(self, clienteActual, finalizados,objetivo,evento,num):
        Thread.__init__(self)
        self.client=clienteActual
        self.lock = Lock()
        self.finalizados=finalizados
        self.objetivo=objetivo
        self.e=evento
        self.i=num
    def run(self):
                    
        #Envia la orden de empezar a descargar el torrent a cada cliente
        self.client.sendall((str(self.i)+"\n").encode())

        #Recibe los tiempos de descarga del torrent de cada cliente
        s=""
        try:
            #Se configura timeout para evitar bloqueos
            self.client.settimeout(30*60)
            a = self.client.recv(1024).rstrip()
            #Escribe en el archivo los resultados
            #Coloca un candado para evitar problemas de concurrencia
            self.lock.acquire()
            f = open("prueba15Maquinas5GB30.txt", "a")
            f.write("# min Maquinas "+ str(self.objetivo)+" "+str(a)+"\n")
            f.close()
            print(a)
            self.finalizados.append(1)
            #Quita el candado 
            self.lock.release()
            #Espera a que todos acaben
            self.e.wait()
            #Manda a eliminar el archivo
            print("Elimina")
            self.client.sendall("end\n".encode())
        except Exception as ex:
            #Escribe en el archivo el error y manda la orden de eliminar 
            # (normalmente esta orden no se lee porque el cliente se quedó trabado)
            s="Ocurrió un error con el cliente "+ self.client.getpeername()[0] +": "+ str(ex)
            self.lock.acquire()
            f = open("prueba15Maquinas5GB30.txt", "a")
            f.write(s+"\n")
            f.close()
            print(s)
            self.finalizados.append(1)
            self.lock.release()
            print("Elimina")
            self.client.sendall("end\n".encode())
            
            
                
                
