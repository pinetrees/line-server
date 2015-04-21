# line-servers
Preface:
The line server is demonstrated in three languages. My reasoning for this is as follows: 

My skills are strongest in Python. I understood that Flask would handle the job very well, and was able to put this server together in very little time. From this point, Ruby was not so far of a stretch. I only needed to find a suitable, simple framework, on par with Flask, to do essentially the same thing. Between these two servers, I had most of what I needed, but I felt that the more interesting questions would not be answered well by either of these two implementations, which are both very high level.

Java was the next logical choice. I knew I would have great control over the implementation, and over the methods of storing and retrieving the data. For a file which can fit into memory, there was not so much need for this (I think), but when considering very large files, I felt that high level solutions would fall short of an optimal solution. I have very little experience with Java (certainly not enough to list it on my resume), so this job was a great challenge for me (a very big motivator) - to simply get a Java server running took me longer than both of the other servers combined. To find an implementation which responded to parameters took approximately the same duration. To learn to read a file into an array took longer than any of the previous tasks.

I am writing this after everything described above has been completed. I have yet to make considerations or perform thorough tests on larger files or a large number of simultaneous requests.

For files which can fit in memory, we have little to worry about. Storing the file as an array of lines gives us O(n) complexity for retrieval, with n the number of lines in the file. We can do better only by transforming this into a binary tree or a hash table. 

Q&A

How does this system work?
The basic operation of each of the implementations is to run a server with (at least) a single route. When the server is initialized, the text file is read into a global array. When users connect to the server via the route, the index they specify is converted into an integer, which is used to look up and return the associated line number from the text file from the global array.

How will the system perform with larger files?
I'm unsure. I know that for files which fit into memory, the array can be stored in memory as well, and the lookup will be very fast. When disk space is involved, I do not know how each of the languages handle chunking the data, and am left to hope that they know what they are doing. I believed that by using a language like Java, I would be able to have a stronger control over the method of storage. I still believe this to be true, but simply do not have enough time to implement the right solution. 

How will the system perform with many users?
This is only important with respect to the frequency of requests. Without taking into account distributed systems, many users making pseudo-simultaneous requests is roughly equivalent to a single user making an equal number of consecutive requests. The question of performance requires some math, and is dependent on the size of the file. If we assume O(n) time for the lookup, and disregard other universal, roughly constant factors such as the actual request translation (not involving the lookup), then we should be able to calculate a rough estimation of the number of requests per second our system can handle:

1 MHz = 10^6 cycles / second =>
1 GHz = 1000 MHz = 1000 * 10^6 cycles / second = 10^9 cycles / second
1 average dedicated server > 4 cores * 3 GHz > 10 GHz = 10^10 cycles / second

For average sized lines, say 10 words, 50 characters, 50 bytes, a 1GB file would have about 20,000,000 lines. This would give us a worst case of about 10^10 / 20,000,000, or 500 lookups per second. This is only 5 lookups per second for a 100gb file.

If we had a million users, and each of the made a request at the same instant, one of them would have to wait 1,000,000 / 5, or 200,000 seconds, or 3350 hours, or 139 days!

If we instead stored this file in a binary tree, and were able to achieve O(log(n)) lookup, the same 100gb file could be searched (10^10 / log(2,000,000,000)), or almost a half a billion times per second.

There is a list of references I used in refs.txt. 

The system uses Flask as a Python server, Sinatra as a Ruby server, and Jersey as a Java server, all chosen because they are extremely lightweight, and this system did not need anything complex with respect to the server.

I initially spent half an hour to set up the Flask server, then another hour to set up the Sinatra server, then another two to set up the Jersey server, and another 1-2 hours to learn to read the file into Java. If I had more time, I would focus on implementing an appropriate data structure in Java to handle larger files, and then perhaps to handle the requests themselves. I would certainly start by implementing a binary tree to store the file, and subsequently exploring the possibility of a distributed hash table. I would have loved to know more about how to handle shuffling data between memory and disk space.
