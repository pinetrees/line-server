echo "Running the server...this may take some time while the file is loaded..."
ruby server.rb $1 &
ruby loader.rb $1
