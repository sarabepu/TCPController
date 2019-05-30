from socket import *
"""
Enviar una peticion al AceptarConexiones para que deje de aceptar conexiones
"""
def send_stop():
	serverName = 'localhost'
	serverPort = 44444
	clientSocket = socket(AF_INET, SOCK_STREAM)
	clientSocket.connect((serverName,serverPort))


if __name__ == '__main__':
	send_stop()