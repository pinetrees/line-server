import time

def log_time(function, *args, **kwargs):
    start = time.time()
    function(*args, **kwargs)
    end = time.time()
    print "Execution took " + str(end - start) + " seconds"


def write_lines():
    line_count = 1000
    f = open('file.txt', 'w')
    for i in range(line_count):
        f.write('line ' + str(i) + '\n')


log_time(write_lines)
