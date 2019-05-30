f = open("archivos/demofile3.txt", "a")
for i in range(int(1000000*100)):
    f.write("Now the file has more content!")
f.close()