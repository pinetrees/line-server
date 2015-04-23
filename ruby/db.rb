require 'rubygems'
require 'bundler/setup'
require 'active_record'

#We'll store our database connection information here. A quick and dirty version of a database.yml file.
ActiveRecord::Base.establish_connection(
  :adapter  => "mysql2",
  :host     => "localhost",
  :username => "guest",
  :password => "guest",
  :database => "line_server"
)

#An attempt at using sqlite was made - this crashed my hard disk twice. I don't want to put anyone else through this.
#ActiveRecord::Base.establish_connection(
#    :adapter => "sqlite3",
#    :database => "lines.db"
#)
