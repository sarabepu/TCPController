import subprocess
import time


def sleep_till_future(dia,f_hour,f_minute):
    """
        The function takes the current time, and calculates for how many seconds should sleep until a user provided minute in the future.
    """
    import time,datetime


    t = datetime.datetime.today()
        
    future = datetime.datetime(t.year,t.month,dia, f_hour,f_minute)
    print(future)
    print(t)
    if future <= t:
        print("ERROR! Enter a valid minute in the future.")
    else:
        seconds_till_future = (future-t).seconds
        time.sleep( seconds_till_future )
