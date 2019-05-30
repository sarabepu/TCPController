import subprocess
import time,datetime


def sleep_till_future(dia,hora,minu):
    """
        The function takes the current time, and calculates for how many seconds should sleep until a user provided minute in the future.
    """

    t = datetime.datetime.today()
    future = datetime.datetime(t.year,t.month, dia,hora,minu)

    if future <= t:
        print("ERROR! Enter a valid minute in the future.")
    else:
        print(future)
        print(t)
        seconds_till_future = (future-t).seconds
        time.sleep( seconds_till_future )

#Sincroniza la ejecución del cliente para dentro del cuarto de hora más cercano
t1 = datetime.datetime.today()
hora=t1.hour
dia=t1.day
minu=0

while(t1.minute-minu>0):
    minu+=15
    
if minu==60:
    minu=0
    if hora<23:
        hora+=1
    else:
        hora=0
        dia+=1
#Espera la fecha
sleep_till_future(dia,hora,minu)
#Repite el experimento 3 veces 
for i in range(3):
    subprocess.call([r'C:/unaCloudTorrent/unaCloud_bat_jar/Cliente.bat'])
    
	
	
	
	


