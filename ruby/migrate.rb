require 'rubygems'
require 'bundler/setup'
require 'active_record'
load 'db.rb'

#If the table exists, we'll just drop it and start over. If it existed, it's probably exactly how we want it, but we should just be safe. This way, we'll clear the contents, and be sure that we have exactly the right columns, with an index on lines. That index is important. Without it, there's no reason to be using a database.
if ActiveRecord::Base.connection.table_exists? :lines
    ActiveRecord::Migration.drop_table :lines
end

#We'll create a single table, lines, to store each line of text as a row. The content portion is a text block, but under the right specifications we can restrict this to a string and optimize space.
ActiveRecord::Migration.create_table :lines do |t|
      t.integer :index
      t.text :content
end

#We're going to add a very crucial database index to the index column of each row. This is the primary reason for using the database, and will allow our lookups to function like a hash table, running in virtually constant time. This will allow us to support a 100gb file and many simultaneous users (not 1,000,000).
ActiveRecord::Migration.add_index :lines, :index

#This solution is completely optimal, but there is a serious drawback: For larger files (medium sized files, even), we are going to have to wait a while to get our file indexed. It is not a necessary operation for smaller files, and the task involves passing the file in to the server at runtime, so we have no option to compile the database ahead of time. If the server went offline for any reason, we would have to recreate the entire database. We could possibly store a hash of the file in another table, and check if this hash is the same before rewriting the entire database, but in general, using a database for this system requires a serious startup cost. With that said - it works.
