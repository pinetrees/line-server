require 'rubygems'
require 'bundler/setup'
require 'sinatra'
require 'active_record'
load 'db.rb'

#We declare our line object to communicate with the database
class Line < ActiveRecord::Base
end

set :bind, '127.0.0.1'
set :port, 8089

#We set the file to either the first argument, or a default file.txt
file = ARGV[0] || 'file.txt'

#We read the file into an array
lines = File.readlines(file)

#We cache the length of the file for reference - this way we don't have to count large files every time a request is made
file_length = lines.length

#This is our single route, which takes a single parameter, the line index, and returns the respective line of our input file.
get '/lines/:index' do
    #We convert our parameter to an integer
    index = params[:index].to_i

    #If the index is out of range of the file, we let our user know, and stop.
    if index >= file_length
      status 413
      body 'The index you\'ve provided is out of range.'
      return
    end

    #Here, since we are possibly loading a very large file into our database, we need to take over the connection. Without doing this, we'll most likyle just get a timeout if the file is still being indexed.
    ActiveRecord::Base.connection_pool.with_connection do |conn|
      line = Line.where(index: index).first
      #It is very possible that we haven't indexed the requested line yet. 
      if not line.nil?
          return line.content
      end
      #In this case, we haven't indexed this line yet. We'll have to send our user along the long path for now.
      lines[index]
    end
end

