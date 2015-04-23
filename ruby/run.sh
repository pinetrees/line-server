echo "Running the server...this may take some time while the file is loaded..."
ruby loader.rb $1 &
ruby server.rb $1 
