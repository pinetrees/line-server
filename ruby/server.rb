require 'sinatra'
require 'google_hash'

set :port, 8080
lines = File.readlines('file.txt')
file_length = lines.length

get '/lines/:id' do
    index = params[:id].to_i
    if index >= file_length
      status 413
      body 'The index you\'ve provided is out of range.'
      return
    end
    lines[index]
end
