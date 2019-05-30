# TCPController
TCP controller with threads 

This is a project that uses the Ttorrent library made by turn 
https://github.com/mpetazzoni/ttorrent

It is a Controller that sends the torrent clients the order of start downloading a file through TCP. The client start downloading the file 
and seeds it until the controler sends the end comand to finish the experiment. 

The controller end the experiment when all the clients have downloaded the file or reached timeout.
