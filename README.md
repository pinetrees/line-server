# line-servers

#Introduction
The line server is demonstrated in three languages. My reasoning for this is as follows: 

My skills are strongest in Python. I understood that Flask would handle the job very well, and was able to put this server together in very little time. From this point, Ruby was not so far of a stretch. I only needed to find a suitable, simple framework, on par with Flask, to do essentially the same thing. Between these two servers, I had most of what I needed, but I felt that the more interesting questions would not be answered well by either of these two implementations, which are both very high level.

Java was the next logical choice. I knew I would have great control over the implementation, and over the methods of storing and retrieving the data. For a file which can fit into memory, there was not so much need for this (I think), but when considering very large files, I felt that high level solutions would fall short of an optimal solution. I have very little experience with Java (certainly not enough to list it on my resume), so this job was a great challenge for me (a very big motivator) - to simply get a Java server running took me longer than both of the other servers combined. To find an implementation which responded to parameters took approximately the same duration. To learn to read a file into an array took longer than any of the previous tasks.

For files which can fit in memory, we have little to worry about. Storing the file as an array of lines gives us O(n) complexity for retrieval, with n the number of lines in the file. We can do better only by transforming this into a binary tree or a hash table. 

The active system is a database driven Ruby/Sinatra server which works with MySQL to index the lines of the file.

#Quickstart
The installation assumes you have bundler installed. You can install bundler on Ubuntu using apt-get.
./build.sh
(you'll be prompted for your mysql credentials)

./run.sh

Q&A

#How does this system work?
The basic operation of each of the implementations is to run a server with (at least) a single route. When the server is initialized, the text file is read into a global array. When users connect to the server via the route, the index they specify is converted into an integer, which is used to look up and return the associated line number from the text file from the global array.

An advanced implementation (designed well outside the expected time for this project) uses ActiveRecord and a MySQL table to index each line of the file as a row. The process takes a significant amount of time, but once complete, allows for nearly constant time lookups, which would lift the bound on the file size limitation, and restrict the users only by factors which are practically independent of data storage and retrieval. By default, ActiveRecord blocks connections while this process is happening, so my system circumvents this by taking over the connection when user requests are made. If the indexing job is not finished, the system looks up the line from the array instead. There is a serious compatibility issue between this system and the requirements, since this solution is only ideal for a situation where we can preload the file as early as we like. Loading the file at server runtime caused the necessity to clear the table each time the server is run. If we work with a large file, a server crash would put this system to waste.

#How will the system perform with larger files?
The simple solution: I'm unsure. I know that for files which fit into memory, the array can be stored in memory as well, and the lookup will be very fast. When disk space is involved, I do not know how each of the languages handle chunking the data, and am left to hope that they know what they are doing. I believed that by using a language like Java, I would be able to have a stronger control over the method of storage. I still believe this to be true, but simply do not have enough time to implement the right solution. 

The advanced solution: It's a tradeoff. While the file is being loaded, the system does not perform optimally. It still uses O(n) array lookups for line indices which have not yet been written into the database. After the file is completely index, the system performs lookups in constant time.

#How will the system perform with many users?
This is only important with respect to the frequency of requests. Without taking into account distributed systems, many users making pseudo-simultaneous requests is roughly equivalent to a single user making an equal number of consecutive requests. The question of performance requires some math, and is dependent on the size of the file. If we assume O(n) time for the lookup, and disregard other universal, roughly constant factors such as the actual request translation (not involving the lookup), then we should be able to calculate a rough estimation of the number of requests per second our system can handle:

1 MHz = 10^6 cycles / second =>

1 GHz = 1000 MHz = 1000 * 10^6 cycles / second = 10^9 cycles / second

1 average dedicated server > 4 cores * 3 GHz > 10 GHz = 10^10 cycles / second

For average sized lines, say 10 words, 50 characters, 50 bytes, a 1GB file would have about 20,000,000 lines. This would give us a worst case of about 10^10 / 20,000,000, or 500 lookups per second. This is only 5 lookups per second for a 100gb file.

If we had a million users, and each of the made a request at the same instant, one of them would have to wait 1,000,000 / 5, or 200,000 seconds, or 3350 hours, or 139 days!

If we instead stored this file in a binary tree, and were able to achieve O(log(n)) lookup, the same 100gb file could be searched (10^10 / log(2,000,000,000)), or almost a half a billion times per second.

In the advanced case, once the database import finishes, we are only throttled by the network/server, which could be tweaked to handle up to 10,000 request per second (but this would be difficult).

#References
There is a list of references I used in refs.txt. 

#Resources
The system uses Flask as a Python server, Sinatra as a Ruby server, and Jersey as a Java server, all chosen because they are extremely lightweight, and this system did not need anything complex with respect to the server. The advanced system also uses MySQL and ActiveRecord for database storage and interfacing, and Bundle to handle gems.

#Time
I initially spent half an hour to set up the Flask server, then another hour to set up the Sinatra server, then another two to set up the Jersey server, and another 1-2 hours to learn to read the file into Java. If I had more time, I would focus on implementing an appropriate data structure in Java to handle larger files, and then perhaps to handle the requests themselves. I would certainly start by implementing a binary tree to store the file, and subsequently exploring the possibility of a distributed hash table. I would have loved to know more about how to handle shuffling data between memory and disk space.

The database driven system took an additional three hours to iron out.

#Critique
My code has a tendency to ramble. I have difficulty expressing my ideas until they are mostly fleshed out. Any code I write would benefit from several refactors. Mostly, this stems from my inexperience with writing code. I have too many files, and not enough structure. My implementation methods are odd, and nonstandard. For example, I would have benefitted from using a database.yml file.

